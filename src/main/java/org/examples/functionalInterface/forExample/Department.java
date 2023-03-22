package org.examples.functionalInterface.forExample;

import java.util.HashSet;
import java.util.Set;

public class Department {
    public Department(int id, int parent, String name) {
        this.id = id;
        this.parent = parent;
        this.name = name;
    }

    private final int id;
    private final int parent;
    private String name;
    private Set<Department> child = new HashSet<>();

    public int getId() {
        return id;
    }

    public int getParent() {
        return parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    @Override
    public String toString() {
        return "Department:" +
                "id=" + id +
                ", name='" + name;
    }
}
