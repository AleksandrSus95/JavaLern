package exampleUsingJava;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class ExampleObjectAndLink {
    /*
   Java всегда передает параметры по значению
   Т.е при передаче переменной в метод создается новый ее локальный экземпляр внутри метода
   Но с объектами все чуть посложнее, при передаче объект в качестве парамтера, передается его ссылка, и получается что
   внутри метода по этой ссылке мы можем изменять объект.
   Стоит отметить что массивы а так же массивы примитивов находятся в области памяти хеап (куче) там же где и лежат
   все наши объекты.

   Так же в данном примере стоит взять во внимание что стринг это неизменяемый класс (иммутабл)

   В этот метод мы передаем ссылку на строковый объект
   и пытаемся измениьт его значение но тут ничего не получится, как только мы присвили ему новое значение создался новый
   локальный объект
    */
    public static void testMethod1(String someString) {
        someString = "new element";
    }

    /*
    В этот метод мы передаем ссылку на массив объектов (строк)
    ячейки массивы мы можем редактировать и менять в них значение и эти значения изменятся так же и за пределами
    этого метода, но стоит помнить что в сам метод передается все таки копия ссылки на объект а не исходная ссылка,
    копия ссылки внутри метода указывает на тот же участок памяти что и исходная ссылка
     */
    public static void testMethod2(String[] someStringArr) {
        for (int i = 0; i < someStringArr.length; i++) {
            someStringArr[i] = "new element " + i;
        }
    }

    /*
    Стоит акуратно работать с передачей в метод массива, если нам надо чтобы исходный элемент
    вне метода остался неизменным, то в самом методе стоит создать новый идентичный объект,
    созданный объект будет ссылатся уже на другой участок памяти.
     */
    public static void testMethod3(List<String> someStringArr) {
        someStringArr = new ArrayList<>(someStringArr);
        for (int i = 0; i < someStringArr.size(); i++) {
            someStringArr.set(i, "new element in List string" + i);
        }
    }

    @Test
    public void usingString() {
        String testStr = "testStr = " + UUID.randomUUID();
        testMethod1(testStr);
        System.out.println(testStr);
        String[] testArrString = new String[10];
        for (int i = 0; i < testArrString.length; i++) {
            testArrString[i] = "testArrString" + i + " = " + UUID.randomUUID();
        }
        Arrays.stream(testArrString).forEach(System.out::println);
        testMethod2(testArrString);
        Arrays.stream(testArrString).forEach(System.out::println);
        List<String> listString = new ArrayList<>(List.of(testArrString));
        testMethod3(listString);
        System.out.println("Это вывод после вызова метода как видим в методе создали новый экземпляр а исходный не поменялся");
        listString.stream().forEach(System.out::println);
    }
}
