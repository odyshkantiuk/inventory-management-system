package com.inventory_management_system.dao.annotation;

import com.inventory_management_system.dao.annotation.utils.converter.StringConverter;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Stored {
    public String name();

    public Class converter() default StringConverter.class;
}
