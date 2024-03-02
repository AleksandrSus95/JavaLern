package org.examples.innerClass;

public class Teacher extends AbstractTeacher{
    public Teacher(int id){
        super(id);
    }

    @Override
    public boolean remandStudent(Student student) {
        return false;
    }
}
