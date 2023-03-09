package org.examples.optionalClass;

import java.util.Objects;

public class Pair<T,K> {
    private final T valueT;
    private final K valueK;

    private static final Pair<?,?> EMPTY = new Pair<>(null, null);

    private Pair(T o1, K o2) {
        this.valueT = o1;
        this.valueK = o2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return Objects.equals(this.valueT, pair.valueT) && Objects.equals(this.valueK, pair.valueK);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valueT, valueK);
    }

    public static <T,K> Pair<T,K> of(T valueT, K valueK){
        return new Pair<>(valueT, valueK);
    }

    public T getFirst(){
        return this.valueT;
    }

    public K getSecond(){
        return this.valueK;
    }
}
