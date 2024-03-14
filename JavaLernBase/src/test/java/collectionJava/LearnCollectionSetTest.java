package collectionJava;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

public class LearnCollectionSetTest {
    @Test
    public void collectionSetTest() {
        Set<String> exampleSet = new HashSet<String>();
        exampleSet.add("Aleksandr"); // При вызове метода add внутри вызывается метод put который принадлежит HashMap,
        exampleSet.add("Irina");     // в качестве значения кладется константа (Object)
        exampleSet.add("Ivan");
        exampleSet.add("Nikolay");
        exampleSet.add("Irina"); // При добавлении дубликата ничего не произойдет, новое значение просто встанет на место предыдущего, добавления
        // еще одного такого же элемента не произойдет.
        exampleSet.add(null); // Можно добавить налл
        System.out.println(exampleSet + " size:" + exampleSet.size()); // HashSet не хранит элементы по порядку. Он представляет множество уникальных значений.

        // Удаление элемента
        exampleSet.remove("Nikolay");
        System.out.println(exampleSet + " size:" + exampleSet.size());
        System.out.println(exampleSet.isEmpty()); // Проверим пустое ли множество
        System.out.println(exampleSet.contains("Irina")); // Проверит содержит ли сет указанный элемент

        Set<Integer> intSet1 = new HashSet<>();
        intSet1.add(5);
        intSet1.add(2);
        intSet1.add(3);
        intSet1.add(1);
        intSet1.add(8);
        System.out.println(intSet1);

        Set<Integer> intSet2 = new HashSet<>();
        intSet2.add(7);
        intSet2.add(4);
        intSet2.add(3);
        intSet2.add(5);
        intSet2.add(8);
        System.out.println(intSet2);

        Set<Integer> unionSet = new HashSet<>(intSet1);
        unionSet.addAll(intSet2); // Объединение множеств
        System.out.println("union set: " + unionSet);

        Set<Integer> retainSet = new HashSet<>(intSet1);
        retainSet.retainAll(intSet2); // Оставит только те элементы которые есть и в первом и втором множестве
        System.out.println("intersect set: " + retainSet);

        Set<Integer> substractSet = new HashSet<>(intSet1);
        substractSet.removeAll(intSet2); // Вычтет из 1го множества второе оставит только уникальные значения из первого
        System.out.println("subtract set: " + substractSet);
    }
}
