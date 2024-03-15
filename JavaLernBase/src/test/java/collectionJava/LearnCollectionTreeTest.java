package collectionJava;

import collectionModels.Students;
import collectionModels.User;
import org.junit.jupiter.api.Test;

import java.util.*;

import static java.time.LocalDate.now;
import static java.util.UUID.randomUUID;

public class LearnCollectionTreeTest {
    /*
     * Классы которые мы собираемся использовать в дереьях ОБЯЗАТЕЛЬНО должны реализовывать интерфейс Comparable
     * так как деревья работают на базе этого интерфейса при посмтроении дерева сравнивают значения с помощью него
     * ЛИБО указать его при создании дерева в конструкторе
     */
    @Test
    public void collectionTreeMapTest() {
        /*
        Элементами TreeMap являются пары ключ значение. В TreeMap элементы хранятся в отсортированном по возрастанию порядке
        (Сортировка происходит по ключу)

        В основе TreeMap лежит красное-черное дерево. Это позволяет методам работать быстро но не быстрее чем HashMap

        Ключи должны быть уникальными, если пололожить по одному и тому же ключу разные элементы то старое значение затрется.

        ВАЖНО!!! Чтобы использовать элемент в качестве ключа то надо реализовать интерфейс Comparable

        Суть сбалансированного красно черного дерева в том что оно перегруппирует свои элементы для ускорения поиска.
        TreeMap НЕ ЯВЛЯЕТСЯ СИНХРОНИЗИРОВАННОЙ коллекцие по этому синхронизировать его надо самостоятельно если это не обходимо
         */
        TreeMap<Double, User> treeMap = new TreeMap<>();
        User user1 = new User("User1", now(), randomUUID());
        User user2 = new User("User2", now(), randomUUID());
        User user3 = new User("User3", now(), randomUUID());
        User user4 = new User("User4", now(), randomUUID());
        User user5 = new User("User5", now(), randomUUID());
        User user6 = new User("User6", now(), randomUUID());
        User user7 = new User("User7", now(), randomUUID());

        treeMap.put(1.0, user1);
        treeMap.put(1.1, user2);
        treeMap.put(1.2, user3);
        treeMap.put(0.1, user4);
        treeMap.put(0.05, user5);
        treeMap.put(0.25, user6);
        treeMap.put(2.5, user7);

        System.out.println(treeMap);
        System.out.println(treeMap.get(0.1)); // Получить элемент по ключу 0.1
        System.out.println(treeMap.remove(0.1)); // Удалить элемент по ключу 0.1
        System.out.println(treeMap.keySet()); // Получить множество ключей
        System.out.println(treeMap.descendingMap()); // Реверсировать карту (т.е отсоритровать в обратном порядке)
        System.out.println(treeMap.tailMap(1.1)); // Выводит все значения выше указанного
        System.out.println(treeMap.headMap(1.5)); // Выводит все значения ниже указанного
        System.out.println(treeMap.lastEntry()); // Выводит элемент из конца (в самом конце)
        System.out.println(treeMap.firstEntry()); // Выводит элемент из начала коллекции

        // Тут я предварительно реализовал интерфейс Comparable в классе Users
        // Либо можно реализовать кампоратор при создании TreeMap, new TreeMap<>(тут реализуем компаратор)
        TreeMap<User, Double> userTreeMap = new TreeMap<>() {{
            put(new User("User1", now(), randomUUID()), 0.035);
            put(new User("User2", now(), randomUUID()), 0.035);
            put(new User("User3", now(), randomUUID()), 25.0);
            put(new User("User4", now(), randomUUID()), 0.25);
            put(new User("User5", now(), randomUUID()), 5.0);
            put(new User("User6", now(), randomUUID()), 6.234);
        }};
        System.out.println(userTreeMap);
    }

    @Test
    public void collectionTreeSet(){
        /*
        TreeSet хранит элементы в отсортированном по возрастанию порядке.
        В основе TreeSet лежит коллекция TreeMap. У элементов данного TreeMap ключи - это элементы TreeSet,
        а значения - это константа-заглушка.
        Как и все множества не допускает дубликаты и хранит значения в отсортированном по возрастанию порядке.

        По сути все то же самое что и в TreeMap только получается что мы у TreeMap используем только ключи.
         */
        Set<Integer> treeSetInt = new TreeSet<>();
        treeSetInt.add(1);
        treeSetInt.add(9);
        treeSetInt.add(192);
        treeSetInt.add(-11);
        treeSetInt.add(0);

        System.out.println(treeSetInt);
        /*
        Можно использовать те же методы что и у TreeMap
         */
        TreeSet<Students> treeSetStudents = new TreeSet<>((o1, o2) -> o1.getCourse() - o2.getCourse());
        treeSetStudents.add(new Students("Irina", 1));
        treeSetStudents.add(new Students("Aleksandr", 5));
        treeSetStudents.add(new Students("Michail", 3));
        treeSetStudents.add(new Students("Oleg", 2));
        treeSetStudents.add(new Students("Semen", 7));

        System.out.println(treeSetStudents);
        System.out.println(treeSetStudents.first()); // Получит первый элемент множества
        System.out.println(treeSetStudents.last()); // Получит последний элемент множества
        System.out.println(treeSetStudents.headSet(new Students("Nikolay", 4))); // Выведет элементы до указанного
        System.out.println(treeSetStudents.tailSet(new Students("Pasha", 6))); // Вывежеь элементы полсе указанного
        System.out.println(treeSetStudents.subSet(new Students("Nikolay", 2), new Students("Pasha", 5))); // Выведет элементы между указанным ключами
        /*
        Есть еще одно негласное правило, если у нас в классе реализован интерфейс компаратор то в хэшкоде и экуалсе можно сравнивать
        и считать хэш код только используемого поля при сравнении (в компораторе)
         */
    }
}

