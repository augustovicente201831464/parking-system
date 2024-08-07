package com.cunoc.edu.gt.utils;

import com.cunoc.edu.gt.annotations.validation.DataRequired;
import com.cunoc.edu.gt.exception.ValidationException;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.util.logging.Logger;

/**
 * Validator class to validate objects
 *
 * @Author: Augusto Vicente
 */
public class ValidatorManager {

    @SneakyThrows
    public static void validate(Object obj) {
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(DataRequired.class)) {
                field.setAccessible(true);
                Object value = field.get(obj);
                if (value == null || value.toString().trim().isEmpty()) {
                    DataRequired annotation = field.getAnnotation(DataRequired.class);
                    Logger.getLogger("ValidatorManager").info("Validation failed: " + annotation.message());
                    throw new ValidationException(annotation.message());
                }
            }
        }
    }
}