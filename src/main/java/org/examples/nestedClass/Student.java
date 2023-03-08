package org.examples.nestedClass;

import java.util.Comparator;

public class Student {
    private int studentId;
    private String name;
    private int group;
    private float averageMark;

    public Student(int studentId, String name, int group, float averageMark) {
        this.studentId = studentId;
        this.name = name;
        this.group = group;
        this.averageMark = averageMark;
    }

    public int getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public int getGroup() {
        return group;
    }

    public float getAverageMark() {
        return averageMark;
    }

    public static class GroupComparator implements Comparator<Student> {

        @Override
        public int compare(Student o1, Student o2) {
            return o1.group - o2.group;
        }
    }

    public static class NameComparator implements Comparator<Student>{

        @Override
        public int compare(Student o1, Student o2) {
            return o1.name.compareTo(o2.name);
        }
    }

    public static class MarkComparator implements Comparator<Student>{
        @Override
        public int compare(Student o1, Student o2) {
            return Float.compare(o2.averageMark, o1.averageMark);
        }
    }
}
