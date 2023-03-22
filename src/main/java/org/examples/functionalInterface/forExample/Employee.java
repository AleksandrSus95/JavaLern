package org.examples.functionalInterface.forExample;

public class Employee {
    public Employee(String firstName, String lastName, int id, int age, Position position) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
        this.age = age;
        this.position = position;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "FullName = " + firstName +
                " " + lastName + '\'' +
                ", age=" + age +
                ", position=" + position +
                ", id=" + id +
                '}';
    }

    public int getId() {
        return id;
    }

    public int getAge() {
        return age;
    }

    public Position getPosition() {
        return position;
    }

    private String firstName;
    private String lastName;
    private int id;
    private int age;
    private Position position;
}
