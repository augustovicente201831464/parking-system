package com.cunoc.edu.gt.jpa.repository.repimpl;

import com.cunoc.edu.gt.annotations.persistence.*;
import com.cunoc.edu.gt.connection.ConnectionHelper;
import com.cunoc.edu.gt.jpa.repository.JpaRepository;
import com.cunoc.edu.gt.data.pagination.Page;
import com.cunoc.edu.gt.data.pagination.Pageable;
import com.cunoc.edu.gt.data.pagination.util.PageImpl;
import com.cunoc.edu.gt.exception.NotFoundException;
import com.cunoc.edu.gt.exception.SqlExceptionCustomized;
import com.cunoc.edu.gt.utils.QueryUtils;
import com.cunoc.edu.gt.utils.ReflectionUtils;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.math.BigInteger;
import java.sql.*;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;

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

            Logger.getLogger("JpaRepositoryImpl").info("SQL Query: " + query);
            if (isIdAutoGenerated) {
                preparedStatement = connection.prepareStatement(query.toString(), Statement.RETURN_GENERATED_KEYS);
            } else {
                preparedStatement = connection.prepareStatement(query.toString());
            }

            int paramIndex = 1;
            for (Field field : fields) {

                if (field.isAnnotationPresent(Id.class)
                        && field.isAnnotationPresent(GeneratedValue.class)
                        && field.getAnnotation(GeneratedValue.class).strategy().equals(GenerationType.IDENTITY)) {
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

            preparedStatement.executeUpdate();

            Field idField = ReflectionUtils.getIdField(fields);
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

    @Override
    public Optional<ENTITY> findById(ID id) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            List<Field> fields = ReflectionUtils.getAllFields(entity);
            Field entityIdField = ReflectionUtils.getIdField(fields);

            String entityName = ReflectionUtils.entityName(entity);
            String idColumnName = ReflectionUtils.idColumnName(entityIdField);

            String query = String.format("SELECT * FROM %s WHERE %s = ?;", entityName, idColumnName);

            Logger.getLogger("JpaRepositoryImpl").info("SQL Query: " + query);
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setObject(1, id);

            Logger.getLogger("JpaRepositoryImpl").info("Query with values: " + preparedStatement);

            resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                return Optional.empty();
            }

            ENTITY entitySearched = ReflectionUtils.getObjectFromResultSet(resultSet, entity);

            fields.forEach(fieldRelation -> {
                try {
                    if (ReflectionUtils.notExistsRelation(fieldRelation)) {
                        return;
                    }

                    if (ReflectionUtils.getFetchType(fieldRelation) == FetchType.EAGER) {
                        processFetchEager(fieldRelation, entitySearched, entityIdField);
                    }
                } catch (Exception e) {
                    Logger.getLogger("JpaRepositoryImpl").warning("Error processing relation: " + e.getMessage());
                    throw new RuntimeException(e);
                }
            });

            return Optional.of(entitySearched);
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
            Field idField = ReflectionUtils.getIdField(fields);

            query.delete(query.length() - 2, query.length());
            query.append(" WHERE ").append(ReflectionUtils.toSnakeCase(idField.getName())).append(" = ?");

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
     * @param pageable the page to be retrieved
     * @return a page of entities
     */
    @Override
    public Page<ENTITY> getPage(Pageable pageable) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            ENTITY entityInstance = entity.getDeclaredConstructor().newInstance();
            String entityName = ReflectionUtils.entityName(entity);
            String order = ReflectionUtils. getOrder(pageable);
            String ascOrDesc = ReflectionUtils.getAscOrDesc(pageable);

            List<Field> fields = ReflectionUtils.getAllFields(entity);

            Set<String> validColumns = ReflectionUtils.getValidColumns(fields);
            if (!validColumns.contains(order)) {
                throw new IllegalArgumentException("Columna de ordenamiento no válida: " + order);
            }

            if (!ascOrDesc.equalsIgnoreCase("ASC") && !ascOrDesc.equalsIgnoreCase("DESC")) {
                throw new IllegalArgumentException("Dirección de ordenamiento no válida: " + ascOrDesc);
            }

            long totalElements = 0;
            preparedStatement = connection.prepareStatement(String.format("SELECT COUNT(*) AS count FROM %s;", entityName));
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                totalElements = resultSet.getLong("count");
            }

            preparedStatement = connection.prepareStatement(String.format("SELECT * FROM %s ORDER BY %s %s LIMIT ? OFFSET ?;", entityName, order, ascOrDesc));
            preparedStatement.setInt(1, pageable.getPageSize());
            preparedStatement.setLong(2, pageable.getOffset());

            Logger.getLogger("JpaRepositoryImpl").info("SQL Query: " + preparedStatement);

            resultSet = preparedStatement.executeQuery();

            List<ENTITY> entities = ReflectionUtils.getEntitiesFromResultSet(resultSet, entity);

            //Sort the entities
            String orderBy = pageable.getSort().getOrder();
            String direction = pageable.getSort().getDirection().name();


            return new PageImpl<>(entities, pageable, totalElements);
        } catch (Exception e) {
            Logger.getLogger("JpaRepositoryImpl").warning("Error getting page: " + e.getMessage());
            throw new SqlExceptionCustomized(e.getMessage());
        } finally {
            ConnectionHelper.closeResultSet(resultSet);
            ConnectionHelper.closePreparedStatement(preparedStatement);
        }
    }

    /**
     * Method to get a page of entities
     *
     * @param params the value of the field to be retrieved
     * @return a page of entities
     */
    @SneakyThrows
    private Optional<ENTITY> findByGenericQuery(String query, List<Object> params) {

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            Logger.getLogger("JpaRepositoryImpl").info("SQL Query: " + query);
            preparedStatement = connection.prepareStatement(query);
            for (int i = 0; i < params.size(); i++) {
                preparedStatement.setObject(i + 1, params.get(i));
            }

            resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                throw new NotFoundException("No data found");
            }

            ENTITY entity = ReflectionUtils.getObjectFromResultSet(resultSet, this.entity);

            //Process relation @ManyToOne or @OneToOne or @OneToMany or @ManyToMany if exists
            List<Field> fields = ReflectionUtils.getAllFields(entity.getClass());
            fields.forEach(field -> {

                try {
                    if (ReflectionUtils.notExistsRelation(field)) {
                        return;
                    }

                    if ( ReflectionUtils.getFetchType(field) == FetchType.EAGER) {
                        processFetchEager(field, entity, ReflectionUtils.getIdField(fields));
                    }
                } catch (Exception e) {
                    Logger.getLogger("JpaRepositoryImpl").warning("Error processing relation: " + e.getMessage());
                    throw new RuntimeException(e);
                }

            });

            return Optional.of(entity);
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

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            Logger.getLogger("JpaRepositoryImpl").info("SQL Query: " + query);

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

    @Override
    @SneakyThrows
    public Optional<ENTITY> findByGenericMethod(String methodName, Class<?> repository, List<Object> params) {
        try {
            return findByGenericQuery(QueryUtils.constructSelectQueryFromMethod(entity, ReflectionUtils.findMethod(repository, methodName, params)), params);
        } catch (NoSuchMethodException e) {
            Logger.getLogger("JpaRepositoryImpl").warning("Method not found");
            throw new NotFoundException("Method not found");
        }
    }

    @Override
    public boolean existsByGenericMethod(String methodName, Class<?> repository, List<Object> values) {
        try {
            return existsByGenericField(QueryUtils.constructExistQuery(entity, ReflectionUtils.findMethod(repository, methodName, values)), values);
        } catch (NoSuchMethodException e) {
            Logger.getLogger("JpaRepositoryImpl").warning("Method not found");
            throw new NotFoundException("Method not found");
        }
    }

    private void processFetchEager(Field fieldRelation, ENTITY parent, Field idParent) throws Exception {
        Logger.getLogger("JpaRepositoryImpl").info("Processing eager fetch");
        processFetch(fieldRelation, parent, idParent);
    }

    /**
     * Method to process lazy fetch
     *
     * @param fieldRelation the field relation
     * @param parent the entity
     * @param idParent the id field
     */
    @Override
    @SneakyThrows
    public void processFetchLazy(Field fieldRelation, ENTITY parent, Field idParent) {
        if(ReflectionUtils.getFetchType(fieldRelation) != FetchType.LAZY) {
            Logger.getLogger("JpaRepositoryImpl").info("Field is lazy");
            return;
        }

        processFetch(fieldRelation, parent, idParent);
    }

    private void processFetch(Field fieldRelation, ENTITY entity, Field idField) throws Exception {
        if (fieldRelation.isAnnotationPresent(ManyToMany.class)) {
            processManyToMany(fieldRelation, entity, idField);
        } else if (fieldRelation.isAnnotationPresent(OneToMany.class)) {
            processOneToMany(fieldRelation, entity, idField);
        } else if (fieldRelation.isAnnotationPresent(ManyToOne.class)) {
            processManyToOne(fieldRelation, entity, idField);
        } else if (fieldRelation.isAnnotationPresent(OneToOne.class)) {
            processOneToOne(fieldRelation, entity, idField);
        }
    }

    private void processManyToMany(Field fieldRelation, ENTITY parent, Field idField) throws Exception {
        FetchType fetchType = fieldRelation.getAnnotation(ManyToMany.class).fetch();

        fieldRelation.setAccessible(true);

        ManyToMany manyToMany = fieldRelation.getAnnotation(ManyToMany.class);
        JoinTable joinTable = fieldRelation.getAnnotation(JoinTable.class);

        Class<?> relatedEntity = ReflectionUtils.getEntityClassFromFieldAsList(fieldRelation);

        assert relatedEntity != null;

        String joinTableName = joinTable.name();
        String joinColumn = joinTable.joinColumns()[0].name();
        String inverseJoinColumn = joinTable.inverseJoinColumns()[0].name();
        String relatedTableName = ReflectionUtils.entityName(relatedEntity);
        String relatedTablePrimaryKey = ReflectionUtils.idColumnName(ReflectionUtils.getIdField(ReflectionUtils.getAllFields(relatedEntity)));

        String query = String.format(
                "SELECT r.* FROM %s jt JOIN %s r ON jt.%s = r.%s WHERE jt.%s = ?;",
                joinTableName, relatedTableName, inverseJoinColumn, relatedTablePrimaryKey, joinColumn);

        Logger.getLogger("JpaRepositoryImpl").info("SQL Query: " + query);

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            idField.setAccessible(true);
            preparedStatement.setObject(1, idField.get(parent));

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<?> relatedEntities = ReflectionUtils.getEntitiesFromResultSet(resultSet, relatedEntity);

                fieldRelation.set(parent, relatedEntities);
            }
        }
    }

    private void processOneToMany(Field fieldRelation, ENTITY parent, Field idField) throws Exception{
        throw new UnsupportedOperationException("Not implemented yet");
    }

    private void processManyToOne(Field fieldRelation, ENTITY parent, Field idField) throws Exception{
        throw new UnsupportedOperationException("Not implemented yet");
    }

    private void processOneToOne(Field fieldRelation, ENTITY parent, Field idField) throws Exception{
        throw new UnsupportedOperationException("Not implemented yet");
    }
}