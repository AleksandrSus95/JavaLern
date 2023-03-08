import org.examples.abstactClass.AbstractQuest;
import org.examples.abstactClass.TestAction;
import org.examples.abstactClass.TestGenerator;
import org.examples.cloningClass.TestClass;
import org.examples.innerClass.Student;
import org.examples.innerClass.TeacherLogic;
import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.UUID;

public class SimpleTests {
    @Test
    public void testAbstractClass(){
        TestGenerator generator = new TestGenerator();
        AbstractQuest[] test = generator.generateTest(60, 2);
        TestAction action = new TestAction();
        int result = action.checkTest(test);
        System.out.println(result + " correct answers," + (60-result) + " incorrect");
    }

    @Test
    public void testCloneClass(){
        TestClass testClass = new TestClass(new Random().nextInt(), UUID.randomUUID().toString());
        System.out.println(testClass.hashCode());
        System.out.println(testClass.equals(testClass));
        System.out.println(testClass.toString());
        TestClass testClone = testClass.clone();
        System.out.println(testClone.hashCode());
        System.out.println(testClone.equals(testClass));
        System.out.println(testClone.toString());
    }

    @Test
    public void testInnerClass(){
        Random random = new Random();
        TeacherLogic logic = new TeacherLogic();
        Student student = new Student(random.nextInt(),
                "This Random Name" + random.nextInt(),
                random.nextInt(),
                "This Random Faculty" + random.nextInt());
        student.createAddress("ThisCity", "ThisStreet", random.nextInt(), random.nextInt());
        logic.expelledProcess(random.nextInt(), student);
        logic.expelledProcess(6, student);
        System.out.println(student);
    }

    @Test
    public void testNestedClass(){
        org.examples.nestedClass.Student st1 = new org.examples.nestedClass.Student(2341757, "Mazaliyk", 3, 5.42f);
        org.examples.nestedClass.Student st2 = new org.examples.nestedClass.Student(2341742, "Polovinkin", 1, 5.42f);
        org.examples.nestedClass.Student.NameComparator nameComparator = new org.examples.nestedClass.Student.NameComparator();
        org.examples.nestedClass.Student.GroupComparator groupComparator = new org.examples.nestedClass.Student.GroupComparator();
        org.examples.nestedClass.Student.MarkComparator markComparator = new org.examples.nestedClass.Student.MarkComparator();
        int result1 = nameComparator.compare(st1, st2);
        int result2 = groupComparator.compare(st1, st2);
        int result3 = markComparator.compare(st1, st2);
        System.out.println(st1.getName() + " [" + result1 + "] " + st2.getName());
        System.out.println(st1.getGroup() + " [" + result2 + "] " + st2.getGroup());
        System.out.println(st1.getAverageMark() + " [" + result3 + "] " + st2.getAverageMark());
    }
}
