package exampleUsingJava;

import org.junit.jupiter.api.Test;

import java.util.List;

import static java.lang.Thread.sleep;
import static java.util.stream.IntStream.range;

public class ExampleSynchronizedTest {

    @Test
    public void syncMethodRun() {
        List<Thread> testList = range(0, 10)
                .mapToObj((index) -> new Thread(() -> {
                    Thread.currentThread().setName("Test name " + index);
                    testSync(Thread.currentThread().getName());
                }))
                .toList();
        testList.forEach(Thread::start);
        while (testList.stream().anyMatch(Thread::isAlive)) {
            sleepMills(500);
        }
    }

    @Test
    public void asyncThreadRun() {
        List<Thread> test = range(0, 10)
                .mapToObj((index) -> new Thread(() -> {
                    Thread.currentThread().setName("Test name " + index);
                    testAsync(Thread.currentThread().getName());
                })).toList();
        test.forEach(Thread::start);
        while (test.stream().anyMatch(Thread::isAlive)) {
            sleepMills(500);
        }
    }

    public static synchronized void testSync(String threadId) {
        System.out.println("Sleep " + threadId);
        sleepMills(2000);
        System.out.println(threadId + " FINISH");
    }

    public static void testAsync(String threadId) {
        System.out.println("Sleep " + threadId);
        sleepMills(2000);
        System.out.println(threadId + " FINISH");
    }

    public static void sleepMills(int mills) {
        try {
            sleep(mills);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
