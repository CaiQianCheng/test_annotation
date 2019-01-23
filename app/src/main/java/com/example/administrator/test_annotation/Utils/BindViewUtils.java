package com.example.administrator.test_annotation.Utils;

import android.app.Activity;
import android.util.Log;


import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.Key;
import java.util.HashMap;
import java.util.Map;


/**
 * @author: cai qian cheng
 * @date: 2018/12/26
 * @function:
 */
public class BindViewUtils {


    public static void register(Activity activity) {
        try {
            //找到当前的绑定类
            Class bindClass = Class.forName("com.example.administrator.test_annotation." + "Bind_" + activity.getLocalClassName());
            //用反射执行绑定方法
            Method method = bindClass.getMethod("bindView", activity.getClass());
            method.setAccessible(true);
            method.invoke(bindClass.newInstance(),activity);
        } catch (Exception e) {
            Log.e("bind_view",e.getMessage());
        }
    }
}
