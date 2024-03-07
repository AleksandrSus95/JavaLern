package collectionJava;

import collectionModels.User;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;

import static java.time.LocalDate.now;
import static java.util.UUID.randomUUID;

public class LearnCollectionArrayTest {

    @Test
    public void collectionArrayList() {
        /*
        АррэйЛист или массив переменной длины, используется тогда, когда нужна структура похожая на массив,
        другими словами АррэйЛист - массив изменяющий свою длину.

        в ArrayList есть понятие capasity см. в исходниках, с помощью него инициализируется изначальный массив объектов
        */
        // Способы создания АррэйЛист
        ArrayList<String> list1 = new ArrayList<String>();
        list1.add("Aleksandr");
        list1.add("Ivan");
        list1.add("Irina");
        list1.add("Ibragim");
        System.out.println(list1);
        // Создания без второго указания типа данных ArrayList<String> list = new ArrayList<>();
        // Можно указать размер массива АррэйЛиста указав размер в конструкторе: ArrayList<String> list = new ArrayList<>(200);
        // Можно создать на базе другого АррэйЛиста:  ArrayList<String> list = new ArrayList<>(arrayList);
        // Если создать список без указания типа в дженериках, то в лист можно будет добавлять любые данные,
        // т.к АррэйЛист в своей основе содержит тип данных Object
        list1.add(1, "Nikolay"); // Добавит в позицию под индексом один новое значение, но не затрет предыдущие, а сдвинет их вперед
        System.out.println(list1); // НО таким способом нельзя добавить в новую позицию т.к если у нас размер равен 3 то 4ой ячейки еще не существует
        list1.set(1, "New Name"); // set заменит элемент по указанному индексу
        System.out.println(list1);
        list1.remove("Ivan"); // Удалит элемент если найдет совпадение по указанному объекту, будет удален первый попавшийся элемент совпадающий с указанным, вернет тип булеан в слуаче успеха труе
        // Для корректной работы метода выше надо чтобы был перепорпеделен метод equals (базовый equals в своей сути использует == и будет труе если ссылки совпадают, значение при этом проверяться не будет)
        System.out.println(list1);
        list1.remove(3); // Удалит элемент по указанному индексу и вернет удаленный элемент )
        System.out.println(list1);

        ArrayList<String> list2 = new ArrayList<>(){{
           add("Misha");
           add("Viktor");
           add("Marina");
        }};

        list1.addAll(list2); // Добавляет все элементы в конец списка, так же можно добавить новые элементы с определенного индекса
        System.out.println(list1);
        System.out.println(list1.indexOf("Misha")); // Поиск индекса элемента, он так же покажет первый найденный элемент
        System.out.println(list1.contains("Marina")); // Проверит содержит ли список указанный элемент, будет использовать для сравнения equals
        System.out.println(list1.containsAll(list2)); // Проверит содержит ли список все элементы переданной в метод коллекции
        list1.lastIndexOf("Object"); // Поиск индекса элемента, найден элемент по последнему совпадению
        list1.clear(); // Опустошит список
        list1.isEmpty(); // Покажет пуст ли наш список (фолсе если не пуст, труе если пустой)
    }


    @Test
    public void collectionLinkedListTest() {
        /*
        Связанный список LinkedList - это звенья одной цепочки. Эти элементы хранят определенные данные, а так же
        ссылки на предыдущий и следующий элементы.
        Каждый элемент знает своих соседей.

        При добавлении и удалении просто меняются ссылки на предыдущий и последующий элементы, в аррэй листе приходилось бы двигать все элементы при
        добавлении и удалении элемента.

        Недостатки:
        1. Получение элемента по указанному индексу get(index) будет работать куда медленнее чем в аррэйЛист, т.к придется пройтись по всем звеньям перед тем
           как найти нужный нам элемент, в АррейЛисте это происходит гораздо быстрее

        Преймущество:
        1. Быстрая вставка и удаление элемента.
           Особенно стоит использовать Связанный список когда надо будет часто удалять и добавлять новые элементы, и особенно если речь идет о элементах в начале коллеции
           (в начале вообще надо будет обновить только 1 ссылку)

        Но в большинстве случаев АррэйЛист будет удовлетворять всем требованиям и стоит использовать его.
         */
        LinkedList<User> users = new LinkedList<>();
        users.add(new User().setName("Name1")
                .setUuid(randomUUID())
                .setBirthDay(now()));
        users.add(new User().setName("Name2")
                .setUuid(randomUUID())
                .setBirthDay(now()));
        users.add(new User().setName("Name3")
                .setUuid(randomUUID())
                .setBirthDay(now()));
        System.out.println(users);
    }
}
