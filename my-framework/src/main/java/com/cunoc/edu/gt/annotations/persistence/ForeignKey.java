package com.cunoc.edu.gt.annotations.persistence;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static com.cunoc.edu.gt.annotations.persistence.ConstraintMode.CONSTRAINT;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({})
@Retention(RUNTIME)
public @interface ForeignKey {
    String name() default "";
    ConstraintMode value() default CONSTRAINT;
    String foreignKeyDefinition() default "";
}