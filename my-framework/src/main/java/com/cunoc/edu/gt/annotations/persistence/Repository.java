package com.cunoc.edu.gt.annotations.persistence;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Repository {

    String value() default "";
}