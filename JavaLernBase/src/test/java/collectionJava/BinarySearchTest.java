package collectionJava;

import org.junit.jupiter.api.Test;

import java.util.*;

public class BinarySearchTest {
    @Test
    public void binarySearchExample() {
        /*
        Бинару серч использует метод компаратора, для корректного поиска надо чтобы был реализован в классе
        искаомых объектов компаратор, так же перед использованием бинарного поиска массив должен быть отсоритрован
        Например если мы ищем по UUID среди объектов например Users то компаратор должен быть реализован по UUID и
        массив должен быть отсориторован по UUID.

        Бинарный поиск делит ОТСОРТИРОВАННЫЙ массив попалам, берет элемент из середины и сравнивает с искомым.
        Если при сравнении (comapare) получили отрицательный результат то бере левую половину, делем ее пополам с равниваем заного
         */
        // В классе по работе с массивами Arrays - так же есть бинарныйпоиск
        // Пример с Collections
        ArrayList<Integer> integers = new ArrayList<>();
        integers.add(11);
        for(int i = 0; i < 100; i++) {
            integers.add(new Random().nextInt(100));
        }
        System.out.println(integers);
        Collections.sort(integers);
        System.out.println(integers);
        int indexFoundElements = Collections.binarySearch(integers, 11);
        System.out.println("index = " + indexFoundElements + " elementByIndex:" + integers.get(indexFoundElements));
        // Пример с Arrays
        int[] arrayInt = new int[100];
        arrayInt[0] = 5;
        for(int i = 1; i < 100; i++) {
            arrayInt[i] = new Random().nextInt(100);
        }
        System.out.println(Arrays.toString(arrayInt));
        Arrays.sort(arrayInt);
        System.out.println(Arrays.toString(arrayInt));
        int index = Arrays.binarySearch(arrayInt, 5);
        System.out.println("index: " + index + " value: " + arrayInt[index]);
    }
}
