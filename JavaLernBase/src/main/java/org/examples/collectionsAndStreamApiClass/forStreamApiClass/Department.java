package org.examples.collectionsAndStreamApiClass.forStreamApiClass;

import java.util.HashSet;
import java.util.Set;

public class Department {
    private final int id;
    private final int parent;
    private final String name;

    private Set<Department> child = new HashSet<>();

    public Department(int id, int parent, String name) {
        this.id = id;
        this.parent = parent;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public int getParent() {
        return parent;
    }

    public String getName() {
        return name;
    }

    public Set<Department> getChild() {
        return child;
    }


}
