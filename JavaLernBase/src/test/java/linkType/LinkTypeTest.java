package linkType;

import collectionModels.User;
import org.junit.jupiter.api.Test;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.time.LocalDate;
import java.util.UUID;

public class LinkTypeTest {

    @Test
    public void linkTypesTest() {
        User user = new User("User Name 1", LocalDate.now(), UUID.randomUUID()); // Strong ссылка (прямая ссылка на объект)
        // Пока объект не = налл (существует), то эта ссылка препядствует удалению объекта сборщика мусора, т.к есть прямая связь с объектом

        SoftReference<User> softUser = new SoftReference<User>(user); // Мягкая ссылка, такой объект будет существовать до тех пор пока есть память
        // для выполнения программы, удаляется только при нехватке памяти.

        WeakReference<User> weakUser = new WeakReference<>(user); // Такая сылка очищается при первом запуске гарбич коллектора.
        // WeakReferense (слабя ссылка) удобная для кэширования и красткосрочного хранения данных доп информации, метаданных и т.п

        ReferenceQueue<User> refQueueUser = new ReferenceQueue<>();
        PhantomReference<User> phantomReferenceUser = new PhantomReference<>(user, refQueueUser); // Фантомная ссылка всегда возвращает налл, не зависимо существует объект
        // или нет. Используется в совместке с очередью ссылок, с помощью очереди можем извлеч удаленный объект чтобы убедиться что он действительно удален. И выполнить
        // какую либо логику по высвобождению ресурсов

        System.out.println("Strong ref: " + user);
        System.out.println("Soft ref: " + softUser.get());
        System.out.println("Weak ref: " + weakUser.get());
        System.out.println("Phantom ref: " + phantomReferenceUser.get());

        // Занулим объект
        user = null;
        System.out.println();
        System.out.println("Strong ref: " + user); // Налл так как объект занулен и строгая ссылка не хранит больще ссылку на объект
        System.out.println("Soft ref: " + softUser.get()); // Данные есть
        System.out.println("Weak ref: " + weakUser.get()); // Данные есть но могут и не быть, зависит от того когда сработает гарбич коллектор
        System.out.println("Phantom ref: " + phantomReferenceUser.get()); // Пока что тоже возвращает налл

        // Попробуем запустить гарбич коллектор (у нас нет гарантий что он будет вызван именно в этот момент)
        System.out.println();
        System.out.println("Try to call GC");
        System.gc();
        System.out.println("After call GC");
//        System.out.println("Strong ref: " + user);
//        System.out.println("Soft ref: " + softUser.get()); // Убираем вызов софт, чтобы не было ссылок на наш объект, чтобы увеличить шан вызова гарбич коллектора
        System.out.println("Weak ref: " + weakUser.get()); // Должна стать налл, но грабич коллектор м.б и не вызван
        System.out.println("Phantom ref: " + phantomReferenceUser.get());

        // Рассмотрим работы фантомной ссылки
        // Вытащим из очереди удаленны объект и извлекаем его
        softUser.clear(); // Очистим мягкие ссылки до кучи (т.к сборщик мусора не в какую не хочет работать)
        System.out.println();
        System.out.println("Try to call GC");
        System.gc();
        System.out.println("After call GC");
//        System.out.println("Strong ref: " + user);
//        System.out.println("Soft ref: " + softUser.get()); // Убираем вызов софт, чтобы не было ссылок на наш объект, чтобы увеличить шан вызова гарбич коллектора
        System.out.println("Weak ref: " + weakUser.get()); // Должна стать налл, но грабич коллектор м.б и не вызван
        System.out.println("Ref queue ref: " + refQueueUser.poll()); // Если ссылка была удалено то мы вытащим ее из пула
    }
}
