package exampleUsingJava;

import jdk.jfr.Description;
import org.examples.abstactClass.AbstractQuest;
import org.examples.abstactClass.TestAction;
import org.examples.abstactClass.TestGenerator;
import org.examples.anonimusClass.Shape;
import org.examples.anonimusClass.StudentAction;
import org.examples.cloningClass.CloningClass;
import org.examples.genericClass.ExampleGeneric;
import org.examples.innerClass.Student;
import org.examples.innerClass.TeacherLogic;
import org.examples.optionalClass.Order;
import org.examples.optionalClass.OrderAction;
import org.examples.optionalClass.Pair;
import org.examples.recordClass.RecordClass;
import org.examples.staticExample.SomeCLass;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.System.in;
import static java.lang.System.out;

public class SimpleTests {

    @Test
    @DisplayName("Пример работы статических полей")
    public void staticFieldExample() {
        SomeCLass test1 = new SomeCLass();
        SomeCLass test2 = new SomeCLass();
        SomeCLass test3 = new SomeCLass();
        SomeCLass test4 = new SomeCLass();
        SomeCLass test5 = new SomeCLass();
    }

    @Test
    @DisplayName("Абстрактный класс")
    public void testAbstractClass() {
        TestGenerator generator = new TestGenerator();
        AbstractQuest[] test = generator.generateTest(60, 2);
        TestAction action = new TestAction();
        int result = action.checkTest(test);
        out.println(result + " correct answers," + (60 - result) + " incorrect");
    }

    @Test
    @DisplayName("Клонирование классов")
    public void testCloneClass() {
        CloningClass testClass = new CloningClass(new Random().nextInt(), UUID.randomUUID().toString());
        out.println(testClass.hashCode());
        out.println(testClass.equals(testClass));
        out.println(testClass.toString());
        CloningClass testClone = testClass.clone();
        out.println(testClone.hashCode());
        out.println(testClone.equals(testClass));
        out.println(testClone.toString());
    }

    @Test
    @DisplayName("Внутренние классы")
    public void testInnerClass() {
        Random random = new Random();
        TeacherLogic logic = new TeacherLogic();
        Student student = new Student(random.nextInt(),
                "This Random Name" + random.nextInt(),
                random.nextInt(),
                "This Random Faculty" + random.nextInt());
        student.createAddress("ThisCity", "ThisStreet", random.nextInt(), random.nextInt());
        logic.expelledProcess(random.nextInt(), student);
        logic.expelledProcess(6, student);
        out.println(student);
    }

    @Test
    @DisplayName("Вложенные static классы")
    public void testNestedClass() {
        org.examples.nestedClass.Student st1 = new org.examples.nestedClass.Student(2341757, "Mazaliyk", 3, 5.42f);
        org.examples.nestedClass.Student st2 = new org.examples.nestedClass.Student(2341742, "Polovinkin", 1, 5.42f);
        org.examples.nestedClass.Student.NameComparator nameComparator = new org.examples.nestedClass.Student.NameComparator();
        org.examples.nestedClass.Student.GroupComparator groupComparator = new org.examples.nestedClass.Student.GroupComparator();
        org.examples.nestedClass.Student.MarkComparator markComparator = new org.examples.nestedClass.Student.MarkComparator();
        int result1 = nameComparator.compare(st1, st2);
        int result2 = groupComparator.compare(st1, st2);
        int result3 = markComparator.compare(st1, st2);
        out.println(st1.getName() + " [" + result1 + "] " + st2.getName());
        out.println(st1.getGroup() + " [" + result2 + "] " + st2.getGroup());
        out.println(st1.getAverageMark() + " [" + result3 + "] " + st2.getAverageMark());
    }

    @Test
    @DisplayName("Анонимный класс простой пример")
    public void anonymousClass() {
        StudentAction action = new StudentAction(); //обычный объект
        StudentAction actionAnon = new StudentAction() { //анонимный класс переопределение метода
            int base = 9;

            @Override
            public double defineScholarship(float averageMark) {
                double value = 100;
                if (averageMark > base) {
                    value *= 1 + (base / 10.0);
                }
                return value;
            }
        };
        out.println(action.defineScholarship(9.05f));
        out.println(actionAnon.defineScholarship(9.05f));
    }

    @Test
    @DisplayName("Анонимный класс в перечислении")
    public void testAnonEnum() {
        Arrays.stream(Shape.values()).forEach(shape -> out.println(shape.computeSquare()));
        out.println(Shape.RECTANGLE);
        out.println(Shape.RECTANGLE);
    }

    @Test
    @DisplayName("Пример работы с логгером JUL")
    public void testWordLoggerExample() {
        Logger LOGGER1 = Logger.getLogger(SimpleTests.class.getName() + "LOGGER1");
        LOGGER1.setLevel(Level.ALL);
        LOGGER1.fine(LOGGER1.getName() + " This fine Message");
        LOGGER1.info(LOGGER1.getName() + " This info Message");
        LOGGER1.warning(LOGGER1.getName() + " This warning Message");
        LOGGER1.severe(LOGGER1.getName() + " This severe Message");

        Logger LOGGER2 = Logger.getLogger(SimpleTests.class.getName() + "LOGGER2");
        LOGGER2.setLevel(Level.WARNING);
        LOGGER2.fine(LOGGER2.getName() + " This fine Message");
        LOGGER2.info(LOGGER2.getName() + " This info Message");
        LOGGER2.warning(LOGGER2.getName() + " This warning Message");
        LOGGER2.severe(LOGGER2.getName() + " This severe Message");

        Logger LOGGER3 = Logger.getLogger(SimpleTests.class.getName() + "LOGGER3");
        LOGGER3.setLevel(Level.SEVERE);
        LOGGER3.fine(LOGGER3.getName() + " This fine Message");
        LOGGER3.info(LOGGER3.getName() + " This info Message");
        LOGGER3.warning(LOGGER3.getName() + " This warning Message");
        LOGGER3.severe(LOGGER3.getName() + " This severe Message");
    }

    @Test
    @DisplayName("Пример работы с Generic классом")
    public void exampleGeneric() {
        ExampleGeneric<Integer> testInt = new ExampleGeneric<>();
        testInt.setValue(1);
        int v1 = testInt.getValue();
        out.println(v1);
        ExampleGeneric<String> testString = new ExampleGeneric<>("Test Java Generic");
        String v2 = testString.getValue();
        out.println(v2);
        ExampleGeneric defaultObj = new ExampleGeneric();
        defaultObj = testInt;
        out.println(defaultObj);
        defaultObj.setValue(1);
        out.println(defaultObj.getValue());
        defaultObj.setValue(Short.valueOf((short) 14));
        out.println(defaultObj.getValue());
        defaultObj.setValue("This Default Constructor Object");
        out.println(defaultObj);
        out.println(defaultObj.getValue());
        defaultObj.setValue(null);
        try {
            out.println(defaultObj);
        } catch (NullPointerException e) {
            //ignored
        }
        out.println(defaultObj.getValue());
    }

    @Test
    @DisplayName("Пример использования record класса")
    public void exampleUsingRecordClass() {
        RecordClass record = new RecordClass("Test name", new Random().nextInt());
        System.out.println(record.name());
        System.out.println(record.id());
        System.out.println(record);
    }

    @Test
    @DisplayName("Задание со Stepic Pair - дженерик класс")
    public void taskPairGeneric() {
        Pair<Integer, String> pair = Pair.of(1, "hello");
        Integer i = pair.getFirst();
        String s = pair.getSecond();

        Pair<Integer, String> pair2 = Pair.of(1, "hello");
        boolean mustBeTrue = pair.equals(pair2);
        boolean mustAlsoBeforeBeTrue = pair.hashCode() == pair2.hashCode();

        out.println(mustBeTrue);
        out.println(mustAlsoBeforeBeTrue);
    }

    @Test
    @DisplayName("Пример работы с оболочкой Optional")
    public void optionalUseExample() {
        List<Order> list = new ArrayList<>();
        list.add(new Order(123, "ExampleOrderName - 00"));
        for (int i = 0; i < 10; i++) {
            list.add(new Order(new Random().nextInt(), "ExampleOrderName - " + i));
        }
        out.println(OrderAction.findById(list, 123));
        Optional<Order> orderOptional = OrderAction.findByIdOptional(list, 123);
        orderOptional.ifPresent(out::println);
        out.println(orderOptional.get());
        out.println(orderOptional.isEmpty());
        out.println(orderOptional.isPresent());
    }

    @Test
    @DisplayName("Тест переводчика из inStream в String")
    @Description("Метод, который зачитает данные из InputStream и преобразует их в строку, используя заданную кодировку.")
    public void readInStreamToString() {
        byte[] byteArr = {48, 49, 50, 51};
        interface ReaderAsString {
            String readAsString(InputStream inputStream, Charset charset) throws IOException;
        }
        ReaderAsString reader = (InputStream inputStream, Charset charset) -> {
            InputStreamReader newReader = new InputStreamReader(inputStream, charset);
            StringWriter outputStream = new StringWriter();
            int readSymbol;
            while ((readSymbol = newReader.read()) != -1) {
                outputStream.write(readSymbol);
            }
            return outputStream.toString();
        };
        InputStream stream = new ByteArrayInputStream(byteArr);
        try {
            out.println(reader.readAsString(stream, Charset.forName("ASCII")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("Считает сумму всех встреченных в тексте вещественных чисел с точностью до шестого знака после запятой")
    public void stepicTask(){
        interface CalcSum {
            void calcSum();
        }
        CalcSum calcSum = () -> {
            Scanner scanner = new Scanner(in);
            scanner.useLocale(Locale.ENGLISH);
            double doubleD;
            double sum = 0.0;
            while (scanner.hasNext()){
                if(scanner.hasNextDouble()){
                    doubleD = scanner.nextDouble();
                    sum += doubleD;
                }else {
                    scanner.next();
                }
            }
            out.printf(Locale.ENGLISH, "%.6f", sum);
        };
        calcSum.calcSum();
    }


}
