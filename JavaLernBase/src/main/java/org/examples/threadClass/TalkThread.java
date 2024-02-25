package org.examples.threadClass;

public class TalkThread implements Runnable{
    @Override
    public void run() {
        try {
            for (int i = 0; i < 10; i++){
                System.out.println("Talk --> " + i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        } finally {
            System.out.println("Thread Name: " + Thread.currentThread().getName());
        }

    }
}
