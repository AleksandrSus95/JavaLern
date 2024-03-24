package multithreading;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ThreadPoolExecutor;

public class MultiThreadEx1Test extends Thread {
    // Можно так же просто имплементировать Runnable
    @Override
    public void run() {
        String threadName = currentThread().getName();
        for(int i = 0; i < 10; i++){
            System.out.println(threadName + " " + i);
        }
        System.out.println("Thread " + threadName + " finish");
    }

    @Test
    public void multiThreadExampleCreateWithThreadTest() {
        System.out.println("Thread " + Thread.currentThread().getName() + " Start");
        MyThread1 myThread1 = new MyThread1();
        MyThread1 myThread2 = new MyThread1();
        myThread1.start(); // Обязательно надо использовать метод старт, т.к иначе мы просто вызовем метод run() созданного класса
        myThread2.start();

        System.out.println("Thread " + Thread.currentThread().getName() + " Finish");
    }

    @Test
    public void multiThreadExampleCreateWithRunnableTest() {
        System.out.println("Thread " + Thread.currentThread().getName() + " Start");
        Thread myThread1 = new Thread(new MyThread2());
        Thread myThread2 = new Thread(new MyThread2());
        myThread1.start(); // Обязательно надо использовать метод старт, т.к иначе мы просто вызовем метод run() созданного класса
        myThread2.start();

        System.out.println("Thread " + Thread.currentThread().getName() + " Finish");
    }

    @Test
    public void failedRunThreadUsingMethodRunTest() {
        System.out.println("Thread " + Thread.currentThread().getName() + " Start");
        Thread myThread1 = new Thread(new MyThread2());
        myThread1.run(); // Обязательно надо использовать метод старт, т.к иначе мы просто вызовем метод run() созданного класса
        // Т.к мы вызвали Метод ран, а не запустили отдельный поток то все будет происходить в 1ом и том же потоке а не в разных
        // по этому обязательно надо использовать метод старт

        System.out.println("Thread " + Thread.currentThread().getName() + " Finish");
    }

    @Test
    public void exampleUsingOverrideRunInMainClass() throws InterruptedException {
        System.out.println("Thread " + Thread.currentThread().getName() + " Start");
        Thread myThread1 = new Thread(new MultiThreadEx1Test());
        myThread1.start(); // Обязательно надо использовать метод старт, т.к иначе мы просто вызовем метод run() созданного класса
        System.out.println("Thread " + Thread.currentThread().getName() + " Finish");
    }

    @Test
    public void exampleThreadUsingAnonimClassTest() {
        System.out.println("Thread " + Thread.currentThread().getName() + " Start");
        Thread myThread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                String threadName = Thread.currentThread().getName();
                for(int i = 0; i < 10; i++) {
                    System.out.println(threadName + " " + i);
                }
                System.out.println("Thread " +  threadName + " finish");
            }
        });
        myThread1.start(); // Обязательно надо использовать метод старт, т.к иначе мы просто вызовем метод run() созданного класса
        System.out.println("Thread " + Thread.currentThread().getName() + " Finish");
    }

    @Test
    public void nameThreadExampleAndPriority() {
        MyThreadExampleMethods myThread = new MyThreadExampleMethods();
        System.out.println("Thread name: " + myThread.getName() + " Priority Thread: " + myThread.getPriority());
        myThread.setName("Test thread");
        myThread.setPriority(MAX_PRIORITY);
        System.out.println("Use method run");
        myThread.run(); // При использование метода ран мы просто вызовем метод класс а не запустим отдельный поток.
        // Обрати внимание на имя потока, оно такое же как у основного потока
        // Запустим основной поток чтобы показать, что у него такое же имя как и у вызванного метода ран
        System.out.println("Main Thread name: " + Thread.currentThread().getName());
        // Теперь запустим поток методом старт и обратим внимание на имя потока, оно будет отличаться от имени основного потока
        myThread.start();
        MyThreadExampleMethods myThread1 = new MyThreadExampleMethods();
        System.out.println("Thread name: " + myThread1.getName() + " Priority Thread: " + myThread1.getPriority());
    }
}

// Первый вариант создания потока
// Унаследовать класс Thread
class MyThread1 extends Thread {

    @Override
    public void run() {
        String threadName = currentThread().getName();
        for(int i = 0; i < 10; i++){
            System.out.println(threadName + " " + i);
        }
        System.out.println("Thread " + threadName + " finish");
    }
}

// Второй вариант создания
// Имплементировать интерфейс Runnable
// Используется, в случае если класс будет наследовать уже какой-то класс
class MyThread2 implements Runnable {

    @Override
    public void run() {
        String threadName = Thread.currentThread().getName();
        for(int i = 0; i < 10; i++) {
            System.out.println(threadName + " " + i);
        }
        System.out.println("Thread " +  threadName + " finish");
    }
}

class MyThreadExampleMethods extends Thread {

    @Override
    public void run() {
        System.out.println("Thread name: " + currentThread().getName() + " priority thread: " + currentThread().getPriority());
    }
}

