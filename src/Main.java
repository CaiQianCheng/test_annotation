import java.lang.reflect.Field;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        //扫描类包 假设扫描到了 Bean
        //假设扫描字段
        try {
            Class c1 = Class.forName("Bean");
            Object obj = c1.newInstance();
            if (c1.isAnnotationPresent(MyAnnotation.class)) {
                //识别到了注解类，接着找该类的字段
                Field[] fields = c1.getDeclaredFields();
                for (int i = 0; i < fields.length; i++) {
                    Field field = fields[i];
                    field.setAccessible(true);
                    if (field.isAnnotationPresent(MyAnnotation.BindString.class)) {
                        String value = field.getAnnotation(MyAnnotation.BindString.class).value();
                        field.set(obj, value);
                    }
                    if (field.isAnnotationPresent(MyAnnotation.BingInt.class)) {
                        int value = field.getAnnotation(MyAnnotation.BingInt.class).value();
                        field.setInt(obj, value);
                    }
                    if (field.isAnnotationPresent(MyAnnotation.BingFloat.class)) {
                        float value = field.getAnnotation(MyAnnotation.BingFloat.class).value();
                        field.setFloat(obj, value);
                    }
                }
                System.out.println(obj.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
