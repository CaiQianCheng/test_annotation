package com.example.my_annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: cai qian cheng
 * @date: 2018/12/25
 * @function:
 */
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.METHOD})
public @interface ClickSeeker {
    int value();
}
