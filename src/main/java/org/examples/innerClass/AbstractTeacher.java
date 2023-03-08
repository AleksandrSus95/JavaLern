package org.examples.innerClass;

public abstract class AbstractTeacher {
    private int id;
    public AbstractTeacher(int id){
        this.id = id;
    }
    public abstract boolean remandStudent(Student student);
}
