package collectionJava;

import collectionModels.User;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;

import static java.time.LocalDate.now;
import static java.util.UUID.randomUUID;

public class LearnCollectionLhmTest {
    @Test
    public void linkedHashMapTest(){
        LinkedHashMap<Double, User> lhm = new LinkedHashMap<>();
        User user1 = new User("User1", now(), randomUUID());
        User user2 = new User("User2", now(), randomUUID());
        User user3 = new User("User3", now(), randomUUID());
        User user4 = new User("User4", now(), randomUUID());
        lhm.put(1.5, user1);
        lhm.put(8.0, user2);
        lhm.put(0.25, user3);
        lhm.put(-12.3, user4);
        System.out.println(lhm); // LinkedHashMap будет хранить значения в порядке добавления

        LinkedHashMap<Double, User> linkedHashMap = new LinkedHashMap<>(16, 0.75f,
                true); // элементы будут храниться в порядке использования
        linkedHashMap.put(1.5, user1);
        linkedHashMap.put(8.0, user2);
        linkedHashMap.put(0.25, user3);
        linkedHashMap.put(-12.3, user4);
        System.out.println(linkedHashMap);
        linkedHashMap.get(0.25);
        System.out.println(linkedHashMap); // элемент с ключем 0.25 теперь будет в конце т.к его использовали последний раз
    }
}
