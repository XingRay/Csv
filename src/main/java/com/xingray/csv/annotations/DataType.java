package com.xingray.csv.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface DataType {
    int TIME_MILLS = 1;
    int TIME_SECONDS = 2;

    int value();
}
