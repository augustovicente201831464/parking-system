package com.cunoc.edu.gt.utils;

import com.cunoc.edu.gt.annotations.persistence.Param;
import com.cunoc.edu.gt.annotations.repository.Query;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Objects;

import static com.cunoc.edu.gt.utils.ReflectionUtils.toSnakeCase;

/**
 * Query utils to help with query construction and manipulation
 *
 * @Author: Augusto Vicente
 */
public class QueryUtils {

    /**
     * Construct a select query from a method
     *
     * @param entity the entity class
     * @param method the method
     * @return the query
     */
    public static String constructSelectQueryFromMethod(Class<?> entity, Method method) {
        return Objects.requireNonNullElseGet(
                getQueryFromAnnotation(method),
                () -> queryWherePartFromParams(
                        new StringBuilder("SELECT * FROM ")
                                .append(ReflectionUtils.entityName(entity)),
                        method.getParameters()
                ).append(";").toString()
        );
    }

    public static String constructExistQuery(Class<?> entity, Method method) {
        return Objects.requireNonNullElseGet(
                QueryUtils.getQueryFromAnnotation(method),
                () -> queryWherePartFromParams(
                        new StringBuilder("SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END FROM ")
                                .append(ReflectionUtils.entityName(entity)),
                        method.getParameters()
                ).append(";").toString()
        );
    }

    private static StringBuilder queryWherePartFromParams(StringBuilder queryBuilder, Parameter[] parameters) {
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

        return queryBuilder;
    }

    public static String getQueryFromAnnotation(Method method) {
        if (method.isAnnotationPresent(Query.class)) {
            Query queryAnnotation = method.getAnnotation(Query.class);
            return queryAnnotation.value();
        }

        return null;
    }
}