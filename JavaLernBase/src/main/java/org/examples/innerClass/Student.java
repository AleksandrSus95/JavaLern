package org.examples.innerClass;

import java.util.Objects;

public class Student {
    private int studentId;
    private String name;
    private int group;
    private String faculty;
    private Address address;

    public Student(int studentId, String name, int group, String faculty) {
        this.studentId = studentId;
        this.name = name;
        this.group = group;
        this.faculty = faculty;
    }

    public void createAddress(String city, String street, int houseId, int flatId) {
        this.address = new Address(city, street, houseId, flatId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return this.studentId == student.studentId &&
                this.group == student.group &&
                this.name.equals(student.name) &&
                this.faculty.equals(student.faculty) &&
                this.address.equals(student.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.studentId, this.name, this.group, this.faculty, this.address);
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + this.studentId +
                ", name='" + this.name + '\'' +
                ", group=" + this.group +
                ", faculty='" + this.faculty + '\'' +
                ", address=[" + this.address.toString() +
                "]}";
    }

    private class Address {
        private String city;
        private String street;
        private int houseId;
        private int flatId;
        private String email;
        private String skype;
        private long phoneNumber;

        public Address(String city, String street, int houseId, int flatId) {
            this.city = city;
            this.street = street;
            this.houseId = houseId;
            this.flatId = flatId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Address address = (Address) o;
            return this.houseId == address.houseId &&
                    this.flatId == address.flatId &&
                    this.phoneNumber == address.phoneNumber &&
                    this.city.equals(address.city) &&
                    this.street.equals(address.street) &&
                    this.email.equals(address.email) &&
                    this.skype.equals(address.skype);
        }

        @Override
        public int hashCode() {
            return Objects.hash(city, street, houseId, flatId, email, skype, phoneNumber);
        }

        @Override
        public String toString() {
            return "Address{" +
                    "city='" + city + '\'' +
                    ", street='" + street + '\'' +
                    ", houseId=" + houseId +
                    ", flatId=" + flatId +
                    ", email='" + email + '\'' +
                    ", skype='" + skype + '\'' +
                    ", phoneNumber=" + phoneNumber +
                    '}';
        }
    }
}
