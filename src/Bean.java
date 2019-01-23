@MyAnnotation
public class Bean {

    @MyAnnotation.BindString(value = "蔡前程")
    private String name;

    @MyAnnotation.BingInt(value = 20)
    private int age;

    @MyAnnotation.BingFloat(value = 180f)
    private float height;

    @Override
    public String toString() {
        return "name:"+name+" age:"+age+" height:"+height;
    }
}
