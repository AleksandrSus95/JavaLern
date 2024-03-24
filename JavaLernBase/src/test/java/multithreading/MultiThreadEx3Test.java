package multithreading;

public class MultiThreadEx3Test {
    // Тут тоже использовал на всякий случай мэйн
    // Посмотрим какие состояния есть у потоков
    public static void main(String[] args) throws InterruptedException {
        // Основных состояния 3 - это new, runnable, terminated (создан, запущен, завершен)
        // У состояния rannable есть два подсостояния - ready, runing (готовность, запущен)
        ExampleThread exClass = new ExampleThread();
        Thread thread = new Thread(exClass);
        System.out.println(thread.getState());
        thread.start();
        System.out.println(thread.getState());
        thread.join();
        System.out.println(thread.getState());
    }
}

class ExampleThread implements Runnable {

    @Override
    public void run() {
        System.out.println(" Thread Name: " + Thread.currentThread().getName() + " Start");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(" Thread Name: " + Thread.currentThread().getName() + " Finish");
    }
}