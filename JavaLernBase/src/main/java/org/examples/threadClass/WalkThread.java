package org.examples.threadClass;

public class WalkThread extends Thread{

    @Override
    public void run(){
        try {
            for(int i = 0; i< 10; i++){
                System.out.println("Walk " + i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } finally {
            System.out.println("Thread Name: " + Thread.currentThread().getName());
        }

    }
}
