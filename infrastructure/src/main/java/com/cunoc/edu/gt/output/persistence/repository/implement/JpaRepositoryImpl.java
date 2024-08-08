package com.cunoc.edu.gt.output.persistence.repository.implement;

import com.cunoc.edu.gt.annotations.persistence.*;
import com.cunoc.edu.gt.annotations.repository.JpaRepository;
import com.cunoc.edu.gt.annotations.repository.Query;
import com.cunoc.edu.gt.exception.NotFoundException;
import com.cunoc.edu.gt.exception.SqlExceptionCustomized;
import com.cunoc.edu.gt.output.persistence.connection.ConnectionHelper;
import com.cunoc.edu.gt.output.persistence.repository.ReflectionUtils;
import lombok.SneakyThrows;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.math.BigInteger;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import static com.cunoc.edu.gt.output.persistence.repository.ReflectionUtils.toSnakeCase;

/**
 * Class to implement the repository
 *
 * @param <ENTITY> the entity
 * @param <ID>     the id
 * @Author: KojstarInnovations
 */
public class JpaRepositoryImpl<ENTITY, ID> implements JpaRepository<ENTITY, ID> {

    private final Connection connection;
    private final Class<ENTITY> entity;

    public JpaRepositoryImpl(Class<ENTITY> entity, Connection connection) {
        this.entity = entity;
        this.connection = connection;
    }

    @Override
    public ENTITY save(ENTITY entity) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            List<Field> fields = ReflectionUtils.getAllFields(entity.getClass());

            StringBuilder query = ReflectionUtils.constructSaveQuery(entity);

            boolean isIdAutoGenerated = fields.stream().anyMatch(field -> field.isAnnotationPresent(Id.class)
                    && field.isAnnotationPresent(GeneratedValue.class)
                    && field.getAnnotation(GeneratedValue.class).strategy().equals(GenerationType.IDENTITY));

            if (isIdAutoGenerated) {
                preparedStatement = connection.prepareStatement(query.toString(), Statement.RETURN_GENERATED_KEYS);
            } else {
                preparedStatement = connection.prepareStatement(query.toString());
            }

            int paramIndex = 1;
            for (Field field : fields) {

                if(field.isAnnotationPresent(Id.class)
                        && field.isAnnotationPresent(GeneratedValue.class)
                        && field.getAnnotation(GeneratedValue.class).strategy().equals(GenerationType.IDENTITY)){
                    continue;
                }

                // Check if the field is annotated with @Column
                if (!field.isAnnotationPresent(Column.class)) {
                    continue;
                }

                field.setAccessible(true);
                Object value = field.get(entity);

                if (field.getType().isEnum()) {
                    value = value != null ? value.toString() : null;
                }

                preparedStatement.setObject(paramIndex++, value);
            }

            Logger.getLogger("JpaRepositoryImpl").info("Query: " + query.toString());
            Logger.getLogger("JpaRepositoryImpl").info("Entity: " + entity);
            Logger.getLogger("JpaRepositoryImpl").info("Query with values: " + preparedStatement.toString());

            preparedStatement.executeUpdate();

            Field idField = ReflectionUtils.getIdField(entity);
            idField.setAccessible(true);

            if (isIdAutoGenerated) {
                resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    Object generatedId = resultSet.getObject(1);
                    if (generatedId instanceof BigInteger) {
                        idField.set(entity, ((BigInteger) generatedId).intValue());
                    } else {
                        idField.set(entity, generatedId);
                    }
                }
            }

            return entity;
        } catch (Exception e) {
            throw new SqlExceptionCustomized("Error saving entity: " + e.getMessage());
        } finally {
            ConnectionHelper.closeResultSet(resultSet);
            ConnectionHelper.closePreparedStatement(preparedStatement);
        }
    }

    private Field checkId(List<Field> fields) {
        Field idField = null;

        for (Field field : fields) {
            if (field.isAnnotationPresent(Id.class)) {
                idField = field;
                break;
            }
        }

        if (idField == null) {
            throw new IllegalStateException("No field annotated with @Id found in entity class");
        }

        return idField;
    }

    @Override
    public Optional<ENTITY> findById(ID id) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            ENTITY entityInstance = entity.getDeclaredConstructor().newInstance();

            String entityName = entity.getSimpleName();

            if(entity.isAnnotationPresent(Entity.class)){
                Entity entityAnnotation = entity.getAnnotation(Entity.class);
                entityName = entityAnnotation.name();
            }

            List<Field> fields = ReflectionUtils.getAllFields(entity);
            Field idField = checkId(fields);

            String idColumnName = toSnakeCase(idField.getName());

            if(idField.isAnnotationPresent(Column.class)){
                Column columnAnnotation = idField.getAnnotation(Column.class);
                idColumnName = columnAnnotation.name();
            }

            if(idColumnName.isEmpty() || idColumnName.isBlank()){
                idColumnName = toSnakeCase(idField.getName());
            }

            String query = String.format("SELECT * FROM %s WHERE %s = ?", entityName, idColumnName);
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setObject(1, id);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {


                for (Field field : fields) {

                    if(!field.isAnnotationPresent(Column.class)){
                        continue;
                    }

                    field.setAccessible(true);
                    String columnName = toSnakeCase(field.getName());

                    if(field.isAnnotationPresent(Column.class)){
                        Column columnAnnotation = field.getAnnotation(Column.class);
                        columnName = columnAnnotation.name();
                    }

                    Object value = resultSet.getObject(columnName);

                    // Handle conversion from SQL types to Java types
                    if (value instanceof Timestamp && field.getType().equals(LocalDateTime.class)) {
                        value = ((Timestamp) value).toLocalDateTime();
                    }

                    //Sql date to local date
                    if (value instanceof Date && field.getType().equals(LocalDate.class)) {
                        value = ((Date) value).toLocalDate();
                    }

                    //String to enum conversion

                    if (value instanceof String && field.getType().isEnum()) {
                        value = Enum.valueOf((Class<Enum>) field.getType(), (String) value);
                    }

                    field.set(entityInstance, value);
                }

                return Optional.of(entityInstance);
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            Logger.getLogger("JpaRepositoryImpl").warning("Error finding entity by id: " + e.getMessage());
            throw new SqlExceptionCustomized("Error finding entity by id: " + e.getMessage());
        } finally {
            ConnectionHelper.closeResultSet(resultSet);
            ConnectionHelper.closePreparedStatement(preparedStatement);
        }
    }

    @Override
    public ENTITY update(ENTITY entity) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            List<Field> fields = ReflectionUtils.getAllFields(entity.getClass());
            StringBuilder query = new StringBuilder("UPDATE " + entity.getClass().getSimpleName() + " SET ");
            Field idField = checkId(fields);

            query.delete(query.length() - 2, query.length());
            query.append(" WHERE ").append(toSnakeCase(idField.getName())).append(" = ?");

            preparedStatement = connection.prepareStatement(query.toString());

            for (int i = 0; i < fields.size(); i++) {
                fields.get(i).setAccessible(true);
                preparedStatement.setObject(i + 1, fields.get(i).get(entity));
            }

            preparedStatement.setObject(fields.size() + 1, idField.get(entity));

            preparedStatement.executeUpdate();

            return entity;
        } catch (Exception e) {
            Logger.getLogger("JpaRepositoryImpl").warning("Error updating entity: " + e.getMessage());
            throw new SqlExceptionCustomized("Error updating entity: " + e.getMessage());
        } finally {
            ConnectionHelper.closeResultSet(resultSet);
            ConnectionHelper.closePreparedStatement(preparedStatement);
        }
    }

    /**
     * Method to update an entity by id
     *
     * @param entity the entity to be updated
     * @param id     the id of the entity to be updated
     * @return the updated entity
     */
    @Override
    public ENTITY updateById(ENTITY entity, ID id) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Method to delete an entity
     *
     * @param entity the entity to be deleted
     */
    @Override
    public void delete(ENTITY entity) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Method to delete an entity by id
     *
     * @param id the id of the entity to be deleted
     */
    @Override
    public void deleteById(ID id) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Method to get all entities
     *
     * @return all entities
     */
    @Override
    public List<ENTITY> getAll() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Method to get a page of entities
     *
     * @param values the value of the field to be retrieved
     * @return a page of entities
     */
    @SneakyThrows
    private Optional<ENTITY> findByGenericQuery(String query, List<Object> values) {
        Logger.getLogger("JpaRepositoryImpl").info("Executing query: " + query);

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement(query);
            for (int i = 0; i < values.size(); i++) {
                preparedStatement.setObject(i + 1, values.get(i));
            }

            resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                throw new NotFoundException("No data found");
            }

            return Optional.of(ReflectionUtils.getObjectFromResultSet(resultSet, entity));
        } catch (Exception e) {
            Logger.getLogger("JpaRepositoryImpl").warning(e.getMessage());
            return Optional.empty();
        } finally {
            ConnectionHelper.closeResultSet(resultSet);
            ConnectionHelper.closePreparedStatement(preparedStatement);
        }
    }

    /**
     * Method to evaluate if an entity exists by id
     *
     * @param id the id of the entity to be evaluated
     * @return true if the entity exists, false otherwise
     */
    @Override
    public boolean existsById(ID id) {
        return false;
    }

    /**
     * Method to evaluate if an entity exists by generic field
     *
     * @param value the value of the field to be evaluated
     * @return true if the entity exists, false otherwise
     */
    private boolean existsByGenericField(String query, List<Object> value) {

        Logger.getLogger(JpaRepositoryImpl.class.getName()).info("Executing query: " + query);

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement(query);
            for (int i = 0; i < value.size(); i++) {
                preparedStatement.setObject(i + 1, value.get(i));
            }

            resultSet = preparedStatement.executeQuery();

            resultSet.next();

            return resultSet.getBoolean(1);
        } catch (Exception e) {
            throw new SqlExceptionCustomized("Error finding entity: " + e.getMessage());
        } finally {
            ConnectionHelper.closeResultSet(resultSet);
            ConnectionHelper.closePreparedStatement(preparedStatement);
        }
    }

    protected String getQuery(Method method) {
        if (method.isAnnotationPresent(Query.class)) {
            Query queryAnnotation = method.getAnnotation(Query.class);
            return queryAnnotation.value();
        }

        return null;
    }

    private Method findMethod(Class<?> repository, String methodName, List<Object> values) throws NoSuchMethodException {
        Class<?>[] paramTypes = values.stream().map(Object::getClass).toArray(Class[]::new);
        return repository.getMethod(methodName, paramTypes);
    }

    private String constructSelectQuery(Method method) {
        String query = getQuery(method);

        if (query != null) {
            return query;
        }

        Parameter[] parameters = method.getParameters();

        StringBuilder queryBuilder = new StringBuilder("SELECT * FROM ");
        queryBuilder.append(entity.getSimpleName());
        queryBuilder.append(" WHERE ");

        for (int i = 0; i < parameters.length; i++) {
            Annotation[] paramAnnotations = parameters[i].getAnnotations();
            for (Annotation annotation : paramAnnotations) {
                if (annotation instanceof Param param) {
                    queryBuilder.append(toSnakeCase(param.value())).append(" = ?");
                    if (i < parameters.length - 1) {
                        queryBuilder.append(" AND ");
                    }
                }
            }
        }

        queryBuilder.append(";");
        return queryBuilder.toString();
    }

    private String constructExistQuery(Method method) {
        String query = getQuery(method);

        if (query != null) {
            return query;
        }

        Parameter[] parameters = method.getParameters();

        StringBuilder queryBuilder = new StringBuilder("SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END FROM ");
        queryBuilder.append(entity.getSimpleName());
        queryBuilder.append(" WHERE ");

        for (int i = 0; i < parameters.length; i++) {
            Annotation[] paramAnnotations = parameters[i].getAnnotations();
            for (Annotation annotation : paramAnnotations) {
                if (annotation instanceof Param param) {
                    queryBuilder.append(toSnakeCase(param.value())).append(" = ?");
                    if (i < parameters.length - 1) {
                        queryBuilder.append(" AND ");
                    }
                }
            }
        }

        queryBuilder.append(";");
        return queryBuilder.toString();
    }

    @Override
    @SneakyThrows
    public Optional<ENTITY> findByGenericMethod(String methodName, Class<?> repository, List<Object> values) {
        try {
            return findByGenericQuery(constructSelectQuery(findMethod(repository, methodName, values)), values);
        } catch (NoSuchMethodException e) {
            Logger.getLogger("JpaRepositoryImpl").warning("Method not found");
            throw new NotFoundException("Method not found");
        }
    }

    @Override
    public boolean existsByGenericMethod(String methodName, Class<?> repository, List<Object> values) {
        try {
            return existsByGenericField(constructExistQuery(findMethod(repository, methodName, values)), values);
        } catch (NoSuchMethodException e) {
            Logger.getLogger("JpaRepositoryImpl").warning("Method not found");
            throw new NotFoundException("Method not found");
        }
    }
}
