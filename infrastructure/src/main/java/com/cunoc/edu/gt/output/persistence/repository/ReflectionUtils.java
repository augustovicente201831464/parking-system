package com.cunoc.edu.gt.output.persistence.repository;

import com.cunoc.edu.gt.annotations.persistence.*;
import com.cunoc.edu.gt.exception.NotFoundException;
import lombok.NonNull;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public class ReflectionUtils {

    public static List<Field> getAllFields(Class<?> clazz) {
        List<Field> fields = new ArrayList<>();
        while (clazz != null) {
            Collections.addAll(fields, clazz.getDeclaredFields());
            clazz = clazz.getSuperclass(); // Get the superclass
        }

        return fields;
    }

    public static Field getField(Class<?> clazz, String fieldName) {
        while (clazz != null) {
            try {
                return clazz.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
            }
        }
        return null;
    }

    /**
     * Get list of objects from ResultSet
     *
     * @param resultSet the ResultSet
     * @param clazz    the class
     * @return the list of objects
     * @param <T> the type of the object
     */
    @SneakyThrows
    public static <T> List<T> getListObjectFromResultSet(ResultSet resultSet, Class<T> clazz) {
        List<T> list = new ArrayList<>();

        while (resultSet.next()) {
            list.add(getObjectFromResultSet(resultSet, clazz));
        }

        return list;
    }

    /**
     * Get object from ResultSet
     *
     * @param resultSet the ResultSet
     * @param clazz    the class
     * @return the object
     * @param <T> the type of the object
     */
    @SneakyThrows
    public static <T> T getObjectFromResultSet(ResultSet resultSet, Class<T> clazz) {

        T object = clazz.getDeclaredConstructor().newInstance();
        List<Field> fields = getAllFields(clazz);
        for (Field field : fields) {
            field.setAccessible(true);

            //Field annotation Column
            String fieldName = "";

            if (field.isAnnotationPresent(Column.class)) {
                Column column = field.getAnnotation(Column.class);
                fieldName = column.name();
            } else {
                fieldName = toSnakeCase(field.getName());
            }

            try {
                if (field.getType().equals(Integer.class) || field.getType().equals(int.class)) {
                    field.set(object, resultSet.getInt(fieldName));
                } else if (field.getType().equals(Long.class) || field.getType().equals(long.class)) {
                    field.set(object, resultSet.getLong(fieldName));
                } else if (field.getType().equals(Double.class) || field.getType().equals(double.class)) {
                    field.set(object, resultSet.getDouble(fieldName));
                } else if (field.getType().equals(Float.class) || field.getType().equals(float.class)) {
                    field.set(object, resultSet.getFloat(fieldName));
                } else if (field.getType().equals(Boolean.class) || field.getType().equals(boolean.class)) {
                    field.set(object, resultSet.getBoolean(fieldName));
                } else if (field.getType().equals(String.class)) {
                    field.set(object, resultSet.getString(fieldName));
                } else if (field.getType().equals(LocalDate.class)) {
                    field.set(object, resultSet.getDate(fieldName).toLocalDate());
                } else if (field.getType().equals(LocalDateTime.class)) {
                    field.set(object, resultSet.getTimestamp(fieldName).toLocalDateTime());
                }
            } catch (SQLException e) {
                Logger.getLogger("ReflectionUtils").warning(String.format("Field %s not found in ResultSet", fieldName));
                throw new SQLException(String.format("Field %s not found in ResultSet", fieldName));
            }
        }
        return object;
    }

    public static String toSnakeCase(@NonNull String input) {
        StringBuilder result = new StringBuilder();
        boolean isFirstChar = true;
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (Character.isUpperCase(c)) {
                if (!isFirstChar) {
                    result.append("_");
                }
                result.append(Character.toLowerCase(c));
            } else {
                result.append(c);
            }
            isFirstChar = false;
        }
        return result.toString();
    }

    public static String getMethodName() {

        // Validate if the method is called from a class
        if (Thread.currentThread().getStackTrace().length > 2) {
            return Thread.currentThread().getStackTrace()[2].getMethodName();
        } else {
            return "undefined";
        }
    }

    public static <ENTITY> StringBuilder constructSaveQuery(ENTITY entity) {
        List<Field> fields = ReflectionUtils.getAllFields(entity.getClass());


        String simpleName = entity.getClass().getSimpleName();
        if (entity.getClass().isAnnotationPresent(Entity.class)) {
            Entity entityAnnotation = entity.getClass().getAnnotation(Entity.class);
            simpleName = entityAnnotation.name();
        }

        StringBuilder query = new StringBuilder(String.format("INSERT INTO %s (", simpleName));
        StringBuilder values = new StringBuilder(") VALUES (");

        for (Field field : fields) {
            if (field.isAnnotationPresent(Id.class) &&
                    field.isAnnotationPresent(GeneratedValue.class) &&
                    field.getAnnotation(GeneratedValue.class).strategy().equals(GenerationType.IDENTITY)) {
                continue;
            }

            // Skip fields without the Column annotation
            if (!field.isAnnotationPresent(Column.class)) {
                continue;
            }

            field.setAccessible(true);

            String columnName = "";

            if (field.isAnnotationPresent(Column.class)) {
                Column column = field.getAnnotation(Column.class);
                columnName = column.name();
            }

            if (columnName.isEmpty() || columnName.isBlank()) {
                columnName = toSnakeCase(field.getName());
            }

            query.append(columnName).append(", ");
            values.append("?, ");
        }

        query.delete(query.length() - 2, query.length());
        values.delete(values.length() - 2, values.length());

        query.append(values).append(");");

        return query;
    }

    public static <ENTITY> Field getIdField(ENTITY entity) {
        List<Field> fields = ReflectionUtils.getAllFields(entity.getClass());

        for (Field field : fields) {
            if (field.isAnnotationPresent(Id.class)) {
                return field;
            }
        }

        throw new NotFoundException("No id field found");
    }
}