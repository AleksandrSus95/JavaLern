package org.examples.functionalInterface;

public interface DefaultAndStatic {
    // default методы могут переопределяться при необходимости
    // для вызова дефолтного метода необходимо реализовать интерфейс
    default void anOperator() {
        System.out.println("Service anOperator");
        this.method();
    }

    // static методы вызываются классическим способом без реализации интерфейса
    private void method() {
        System.out.println("Private method");
    }

    static void action() {
        System.out.println("Service static action");
    }

    int define(int x1, int y1);
    void load();
}
