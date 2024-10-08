package com.cunoc.edu.gt.annotations.persistence;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({})
@Retention(RUNTIME)
public @interface UniqueConstraint {
    String name() default "";
    String[] columnNames();
}