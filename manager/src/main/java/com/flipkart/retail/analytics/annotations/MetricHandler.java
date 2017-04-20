package com.flipkart.retail.analytics.annotations;

import com.flipkart.retail.analytics.enums.MetricType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.TYPE})
public @interface MetricHandler {
    Type[] value() default {};

    @interface Type {
        MetricType metricType();
    }
}
