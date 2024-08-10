package com.cunoc.edu.gt.hibernate;

import com.cunoc.edu.gt.annotations.persistence.FetchType;
import com.cunoc.edu.gt.exception.BadOperationException;
import com.cunoc.edu.gt.jpa.repository.JpaRepository;
import com.cunoc.edu.gt.utils.ReflectionUtils;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.logging.Logger;

/**
 * Hibernate utility class
 *
 * @Author: Augusto Vicente
 */
public class Hibernate {

    /**
     * Check if a proxy is not initialized
     *
     * @param proxy the proxy to check
     * @return boolean
     */
    public static boolean notInitialized(Object proxy) {
        if (proxy == null) {
            return true;
        }

        if (proxy instanceof Collection<?>) {
            Logger.getLogger("Hibernate").info("Checking if collection is initialized");
            return ((Collection<?>) proxy).isEmpty();
        } else {
            return false;
        }
    }

    /**
     * Initialize a proxy
     *
     * @param repository the repository
     * @param parentInstance the parent instance
     * @param fieldName the field name
     */
    @SneakyThrows
    public static <ENTITY> void initialize(JpaRepository repository, ENTITY parentInstance, String fieldName) {

        if(parentInstance == null){
            Logger.getLogger("Hibernate").info("Parent instance is null");
            return;
        }

        Field entityIdField = ReflectionUtils.getIdField(parentInstance);
        Field relatedField = ReflectionUtils.getField(parentInstance, fieldName);

        if(ReflectionUtils.notExistsRelation(relatedField)){
            throw new BadOperationException("Field is not a relation");
        }

        FetchType fetchType = ReflectionUtils.getFetchType(relatedField);
        repository.processFetchLazy(relatedField, parentInstance, entityIdField);
    }
}