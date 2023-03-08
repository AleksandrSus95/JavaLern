package org.examples.recordClass;

public record RecordClass(String name, int id){
    private static String testName;

    @Override
    public String name() {
        return name;
    }

    @Override
    public int id() {
        return id;
    }

    @Override
    public String toString() {
        return "testRecord{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
