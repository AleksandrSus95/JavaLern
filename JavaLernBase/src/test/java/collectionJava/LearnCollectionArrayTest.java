package collectionJava;

import collectionModels.User;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static java.time.LocalDate.now;
import static java.util.UUID.randomUUID;

public class LearnCollectionArrayTest {

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
