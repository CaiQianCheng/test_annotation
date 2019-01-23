package com.example.administrator.test_annotation.Utils;

import android.app.Activity;

import java.lang.reflect.Field;

import com.example.my_annotation.*;

/**
 * @author: cai qian cheng
 * @date: 2018/12/11
 * @function:
 */
public class UseAnnotation {

    public static void findView(Activity object) {
        try {
            Class c1 = object.getClass();
            Field[] fields = c1.getDeclaredFields();
            //遍历字段找到注解并给对象复制
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                field.setAccessible(true);
                if (field.isAnnotationPresent(ViewSeeker.class)) {
                    ViewSeeker annotation = field.getAnnotation(ViewSeeker.class);
                    field.set(object, object.findViewById(annotation.value()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
