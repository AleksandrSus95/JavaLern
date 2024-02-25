package org.examples.genericClass;

public class ExampleGeneric<T> {
    private T value;

    public ExampleGeneric() {
    }

    public ExampleGeneric(T value) {
        this.value = value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public static <T> T[] testStaticMethod() {
        return null;
    }

    static <T> void takeKey(T k) {
    }

    @Override
    public String toString() {
        if (value == null) return null;
        return value.getClass().getName() + ":" + value;
    }
}
