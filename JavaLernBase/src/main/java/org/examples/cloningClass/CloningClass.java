package org.examples.cloningClass;

import java.util.ArrayList;
import java.util.Objects;

public class CloningClass implements Cloneable {
    private long id;
    private String name;
    private ArrayList<Short> list = new ArrayList<>();

    public CloningClass(int id, String name) {
        this.id = id;
        this.name = name;
        for (short i = 0; i < 10; i++) {
            list.add(Short.valueOf(i));
        }
    }

    public long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public ArrayList<Short> getList() {
        return this.list;
    }

    @Override
    public String toString() {
        String list = new String();
        for (short i : this.list) {
            list += String.valueOf(i);
        }
        return CloningClass.class.getName() +
                "id=" + id +
                ", name='" + name + '\'' +
                ", list=[" + list + ',' +
                "]}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CloningClass testClass = (CloningClass) o;
        return this.id == testClass.id && Objects.equals(this.list, testClass.list) && Objects.equals(this.name, testClass.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name, this.list);
    }

    @Override
    public CloningClass clone() {
        CloningClass testClone = null;
        try {
            testClone = (CloningClass) super.clone();
            testClone.list = (ArrayList<Short>) this.list.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        return testClone;
    }
}
