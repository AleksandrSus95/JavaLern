package org.examples.innerClass;

public class TeacherCreator {
    public static AbstractTeacher createTeacher(int id){
        int value = 0;
        class Rector extends AbstractTeacher {
            Rector(int id){
                super(id);
            }
            @Override
            public boolean remandStudent(Student student) {
                boolean result = false;
                if(student != null){
                    result = true;
                }
                return result;
            }
        }
        if(isRectorId(id)){
            return new Rector(id);
        }else {
            return new Teacher(id);
        }
    }

    public static boolean isRectorId(int id){
        return (id == 6);
    }
}
