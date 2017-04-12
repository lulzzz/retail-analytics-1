package com.flipkart.retail.analytics.annotations;

import com.flipkart.retail.analytics.enums.EntityType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.TYPE})
public @interface EntityHandler {
    Type[] value() default {};

    @interface Type {
        EntityType entityType();
    }
}
