package com.cunoc.edu.gt.annotations.persistence;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static com.cunoc.edu.gt.annotations.persistence.ConstraintMode.PROVIDER_DEFAULT;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, FIELD})
@Retention(RUNTIME)
public @interface JoinColumns {
    JoinColumn[] value();
    ForeignKey foreignKey() default @ForeignKey(PROVIDER_DEFAULT);
}