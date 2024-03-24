package multithreading;

import org.junit.jupiter.api.Test;

public class MultiThreadEx2Test {
    @Test
    public void exampleSleepAndJoin() {
        MyThreadExample threadExample = new MyThreadExample();
        threadExample.start();
    }

    // Тут я использовал метод мэйн так как тест воркер закрывает прогу и не ждет когда другие треды завершат работу
    public static void main(String[] args) throws InterruptedException {
        System.out.println(Thread.currentThread() + " START");
        MyThreadExample threadExample0 = new MyThreadExample();
        threadExample0.setName("Thread example 1");
        threadExample0.start();
        MyThreadExample threadExample1 = new MyThreadExample();
        threadExample1.setName("Thread example 2");
        threadExample1.start();

        threadExample0.join(); // Вызываем метод джоин в потоке мэйн, и тем самым говорим потоку мэйн,
        threadExample1.join(); // что ему необходимо подождать пока этот поток не завершит свою работу.
//        join(1000) - в джоине можно указать сколько ждать поток, если за 1 секунду работа в потоке не будет выполнена, то
//        поток в котором был вызван метод джоин, продолжит свою работу, если поток успеет отработать раньше, то поток в котором был
//        вызван метод джоин продолжит свою работу
        System.out.println(Thread.currentThread() + " FINISH");
    }
}

class MyThreadExample extends Thread{
    @Override
    public void run() {
        System.out.println("Thread: " + currentThread().getName() + " START");
        for(int i = 0; i < 10; i++){
            System.out.println(currentThread().getName() + " index: " + i);
            try {
                sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("Thread: " + currentThread().getName() + " FINISH");
    }
}
