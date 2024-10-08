package com.cunoc.edu.gt.annotations.persistence;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, FIELD})
@Retention(RUNTIME)
public @interface Column {

    /**
     * (Optional) The name of the column. Defaults to
     * the property or field name.
     */
    String name() default "";
}