package org.examples.optionalClass;

import java.util.Objects;

public class Order {
    private long id;
    private String name;

    public Order(int id, String name){
        this.id = id;
        this.name = name;
    }

    public long getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return this.id == order.id && Objects.equals(this.name, order.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return this.getClass().getName() +
                "{id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
