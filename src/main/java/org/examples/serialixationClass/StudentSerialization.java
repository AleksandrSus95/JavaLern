package org.examples.serialixationClass;

import org.examples.nestedClass.Student;

import java.io.Serializable;
import java.util.StringJoiner;

public class StudentSerialization implements Serializable {
    public static String faculty = "MMF";
    private String name;
    private int id;
    private transient String password;
    private static final long serialVersion = 2L;

    public StudentSerialization(String name, int id, String password) {
        this.name = name;
        this.id = id;
        this.password = password;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Student.class.getSimpleName() + "[", "]")
                .add("name = '" + this.name + "'")
                .add("password = '" + this.password + "'")
                .add("id = '" + this.id).toString();
    }
}
