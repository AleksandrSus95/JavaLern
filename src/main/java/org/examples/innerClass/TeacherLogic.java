package org.examples.innerClass;

public class TeacherLogic {
    public void expelledProcess(int rectorId, Student student){
        AbstractTeacher teacher = TeacherCreator.createTeacher(rectorId);
        boolean result = teacher.remandStudent(student);
        System.out.println("Student expelled: " + result);
    }
}
