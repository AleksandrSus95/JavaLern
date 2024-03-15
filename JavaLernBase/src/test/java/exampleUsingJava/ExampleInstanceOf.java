package exampleUsingJava;

import org.junit.jupiter.api.Test;

public class ExampleInstanceOf {
    @Test
    public void instanceOfUsing() {
        String string1 = new String("test String");
        if (string1 instanceof String) {
            System.out.println("this is String");
        }
        if (string1 instanceof Object) {
            System.out.println("String as Object");
        }
        Integer number1 = Integer.valueOf("123");
        if (number1 instanceof Number) {
            System.out.println("this is Number");
        }
        if (number1 instanceof Integer) {
            System.out.println("this is Integer");
        }
        if (number1 instanceof Object) {
            System.out.println("Integer as Object");
        }
        Character char1 = Character.valueOf('a');
        if (char1 instanceof Character) {
            System.out.println("this is Character");
        }
    }
}
