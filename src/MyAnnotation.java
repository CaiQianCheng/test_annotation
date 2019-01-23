import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解类
 * Target注解决定MyAnnotation注解可以加在哪些成分上，如加在类身上，或者属性身上，或者方法身上等成分
 * Retention 注解周期
 * 在注解类上使用另一个注解类，那么被使用的注解类就称为元注解,这里的Retention称为元注解。
 */
@Retention(RetentionPolicy.RUNTIME)//运行时注解 源代码注解 todo 编译注解 todo 这两种需要示例
@Target({ElementType.TYPE})
public @interface MyAnnotation {

    //字段上注解 可以用view实例化举例以及默认值 TODO
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.FIELD})
    @interface BindString {
        String value() default "张三"; //定义基本属性
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.FIELD})
    @interface BingInt {
        int value() default 18;
        int[] arrayAttr() default {1,2,4};//数组类型的属性
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.FIELD})
    @interface BingFloat {
        float value() default 175f;
        //EumTrafficLamp lamp() default EumTrafficLamp.RED;// 枚举类型的属性
    }

    //在方法上的注解待学习 TODO 注解点击事件




}

