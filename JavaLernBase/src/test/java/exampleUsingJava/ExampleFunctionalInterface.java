package exampleUsingJava;

import jdk.jfr.Description;
import org.examples.functionalInterface.DefaultAndStatic;
import org.examples.functionalInterface.MyFunctionalInterface;
import org.examples.functionalInterface.forExample.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class ExampleFunctionalInterface {
    protected List<Department> deps = List.of(
            new Department(1, 0, "Head"),
            new Department(2, 1, "West"),
            new Department(3, 1, "East"),
            new Department(4, 2, "Germany"),
            new Department(5, 2, "France"),
            new Department(6, 3, "China"),
            new Department(7, 3, "Japan")
    );

    protected List<Employee> emps = List.of(
            new Employee("Michael", "Smith", 243, 43, Position.CHEF),
            new Employee("Jane", "Smith", 523, 40, Position.MANAGER),
            new Employee("Jury", "Gagarin", 6423, 26, Position.MANAGER),
            new Employee("Jack", "London", 5543, 53, Position.WORKER),
            new Employee("Eric", "Jackson", 2534, 22, Position.WORKER),
            new Employee("Andrew", "Bosh", 3456, 44, Position.WORKER),
            new Employee("Joe", "Smith", 723, 30, Position.MANAGER),
            new Employee("Jack", "Gagarin", 7423, 35, Position.MANAGER),
            new Employee("Jane", "London", 7543, 42, Position.WORKER),
            new Employee("Mike", "Jackson", 7534, 31, Position.WORKER),
            new Employee("Jack", "Bosh", 7456, 54, Position.WORKER),
            new Employee("Mark", "Smith", 123, 41, Position.MANAGER),
            new Employee("Jane", "Gagarin", 1423, 28, Position.MANAGER),
            new Employee("Sam", "London", 1543, 52, Position.WORKER),
            new Employee("Jack", "Jackson", 1534, 27, Position.WORKER),
            new Employee("Eric", "Bosh", 1456, 32, Position.WORKER)
    );

    @Test
    @DisplayName("Пример использования статических и дефолтных методов интерфейса")
    public void staticAndDefaultMethod() {
        class InterfaceIml implements DefaultAndStatic {

            @Override
            public int define(int x1, int y1) {
                return x1 + y1;
            }

            @Override
            public void load() {
                System.out.println("Load");
            }
        }

        InterfaceIml interfaceIml = new InterfaceIml();
        System.out.println(interfaceIml.define(2, 3));
        interfaceIml.load();//Вызове переопределенного метода
        interfaceIml.anOperator();//Вызов дефолтного метода
        DefaultAndStatic.action();//вызов статического метода
    }

    @Test
    @DisplayName("Примеры использования функциональных интерфейсов")
    public void exampleFunctionalInterface() {
        // В виде классической реализации интерфейса
        class FuncInterfaceImpl implements MyFunctionalInterface {
            @Override
            public double perimeter(double a, double b) {
                return 2 * (a + b);
            }
        }

        System.out.println("Calc Perimeter rectangle 2 and 3: " + new FuncInterfaceImpl().perimeter(2, 3));

        // В виде лямбда вырважения
        MyFunctionalInterface myFunctionalInterface = (double a, double b) -> 2 * (a + b);
        System.out.println("Calc perimeter rectangle 2 and 3: " + myFunctionalInterface.perimeter(2, 3));

        // В виде параметра метода
        class UsingInterfaceInParam {
            private double x;
            private double y;

            public UsingInterfaceInParam(double x, double y) {
                this.x = x;
                this.y = y;
            }

            public double action(MyFunctionalInterface service) {
                return 10 * service.perimeter(this.x, this.y);
            }
        }
        // Тогда обращение к интерфейсу будет следующим
        double result = new UsingInterfaceInParam(3, 5).action((a, b) -> (a + b) * 4);
        System.out.println(result);
    }

    @Test
    @DisplayName("Пример использования Predicate")
    @Description("Основная область применения: выбор\\поиск\\фильтрация элементов из stream\n" +
            "или коллекции по условию.")
    public void predicateUsing() {
        Predicate<Employee> predicate = s -> s.getPosition().equals(Position.MANAGER);
        System.out.println(emps.stream().filter(predicate).collect(Collectors.toList()));
        System.out.println(emps.stream().filter(f -> f.getFirstName().startsWith("Ja") && f.getPosition().equals(Position.MANAGER)).collect(Collectors.toList()));
        System.out.println(emps.stream().filter((predicate.and(f -> f.getAge() >= 30))).collect(Collectors.toList()));
        System.out.println(emps.stream().filter(((Predicate<Employee>) employee -> employee.getAge() >= 20).and(f -> f.getAge() <= 30)).collect(Collectors.toList()));
    }

    @Test
    @DisplayName("Пример использования интерфейса Functional")
    @Description("Его задача: выполнить действие над объектом одного\n" +
            "типа и возвратить объект другого типа.")
    public void exampleFunctional() {
        System.out.println(deps.stream().map((Department f) -> {
            return f.getId();
        }).collect(Collectors.toList()));
        System.out.println(deps.stream().map(f -> f.getName()).collect(Collectors.toList()));
        System.out.println(deps.stream().map(((Function<String, Integer>) f -> f.length()).compose(f -> f.getName())).collect(Collectors.toList()));
        BinaryOperator<String> testBinaryOperator = (s1, s2) -> s1 + " " + s2.length();
        System.out.println(testBinaryOperator.apply("test", "qwerty"));
    }

    @Test
    @DisplayName("Пример использования интерфейса Consumer")
    @Description("Интерфейс Consumer<T> представляет абстрактный метод void accept(T t),\n" +
            "функция, принимающая объект типа T и выполняющая над ним некоторое дей-\n" +
            "ствие.")
    public void consumerExample() {
        deps.forEach(System.out::println);
        deps.stream().sorted((f, d) -> d.getId() - f.getId()).forEach(System.out::println);
        deps.forEach(f -> f.setName(f.getName() + " Test"));
        deps.forEach(System.out::println);
    }

    @Test
    @DisplayName("Пример использования интерфейса Supplier")
    @Description("Интерфейс Supplier<T> возвращает новый объект типа T методом T get().\n" +
            "Предназначен для создания новых объектов. Метод get() единственный в ин-\n" +
            "терфейсе.")
    public void supplierExampleOne() {
        Supplier<Department> supplier = () -> new Department(1, 2, "testName");
        System.out.println(supplier.get());
        Supplier<int[]> supplier1 = () -> new int[10];
        int[] test = supplier1.get();
        for (int i = 0; i < test.length; i++) {
            test[i] = 1;
        }
        Arrays.stream(test).forEach(System.out::print);
        // Пример Supplier с фабрикой массивов
        interface ExampleSupplier {
            Supplier<int[]> buildArray(int size);
        }
        ExampleSupplier exampleSupplier = (size) -> {
            final int length = size > 0 ? size : 1;
            return () -> new int[length];
        };
        int[] array = exampleSupplier.buildArray(10).get();
        System.out.println("Array int size =" + array.length);

        // Пример игры в рулетку
        interface RouletteExample {
            void game(int yourRandomNumber);
        }

        RouletteExample rouletteExample = (yourNumber) -> {
            if (yourNumber < 0 || yourNumber > 7) {
                System.out.println("Number should be 0< and <7");
                return;
            }
            Supplier<Integer> numberSupplier = () -> new Random().nextInt(7);
            int getNumber = numberSupplier.get();
            System.out.println("Roulette : " + getNumber + ", your bet: " + yourNumber);
            String result = getNumber == yourNumber ? "You won" : "You Lose";
            System.out.println(result);
        };
        rouletteExample.game(new Random().nextInt(7));
        rouletteExample.game(new Random().nextInt(7));
        rouletteExample.game(new Random().nextInt(7));
        rouletteExample.game(new Random().nextInt(7));
        rouletteExample.game(new Random().nextInt(7));
        rouletteExample.game(new Random().nextInt(7));
    }

    @Test
    @DisplayName("Пример использования интерфейса Supplier")
    @Description("Интерфейс Supplier<T> возвращает новый объект типа T методом T get().\n" +
            "Предназначен для создания новых объектов. Метод get() единственный в ин-\n" +
            "терфейсе.")
    public void supplierExampleTwo() {
        interface ExampleTime {
            static Supplier<String> buildTime(String timePattern) {
                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(timePattern);
                return () -> timeFormatter.format(LocalDateTime.now());
            }

            static Supplier<String> plus(float a, float b) {
                BigDecimal decimal = new BigDecimal(a);
                BigDecimal decimal1 = new BigDecimal(b);
                BigDecimal res = decimal.add(decimal1, MathContext.DECIMAL32);
                return () -> res.toEngineeringString();
            }
        }

        Supplier<String> supplierNumber = ExampleTime.plus(1.123450989f, 2.000001f);
        System.out.println("res= " + supplierNumber.get());
        Supplier<String> supplierTime = ExampleTime.buildTime("yyyy-MM-dd HH:mm:ss");
        System.out.println("time= " + supplierTime.get());
    }

    @Test
    @DisplayName("Пример использования интерфейса Comparator")
    @Description("Реализация интерфейса Comparator<T>\n" +
            "представляет возможность его использования для сортировки наборов объек-\n" +
            "тов конкретного типа по правилам, определенным для этого типа. Контракт\n" +
            "интерфейса подразумевает реализацию метода int compare(T ob1, T ob2), при-\n" +
            "нимающего в качестве параметров два объекта, для которых должно быть\n" +
            "определено возвращаемое целое значение, знак которого и определяет правило\n" +
            "сортировки.")
    public void exampleComparator() {
        Comparator<String> comparator = (s1, s2) -> s2.length() - s1.length();
        String str = "and java course epam the rose lion wolf hero green white red white";
        Arrays.stream(str.split("\\s")).sorted(comparator).forEach(s -> System.out.printf("%s ", s));
        System.out.println();
        Arrays.stream(str.split("\\s")).sorted((s1, s2) -> s1.length() - s2.length())
                .forEach(s -> System.out.printf("%s ", s));

        // Пример использования компаротора в перечислении
        enum EmployeeComparator {
            ID(Comparator.comparingInt(Employee::getId)),
            AGE(Comparator.comparingInt(Employee::getAge));
            private Comparator<Employee> comparator;

            EmployeeComparator(Comparator<Employee> comparator) {
                this.comparator = comparator;
            }

            public Comparator<Employee> getComparator() {
                return comparator;
            }
        }

        emps.stream().sorted(EmployeeComparator.ID.getComparator()).forEach(System.out::println);
        System.out.println();
        emps.stream().sorted(EmployeeComparator.AGE.getComparator()).forEach(System.out::println);
        System.out.println();

        // Пример с полной ассоциацией перечисления как компаротора
        enum EmployeeComparatorEx implements Comparator<Employee> {
            ID {
                @Override
                public int compare(Employee e1, Employee e2) {
                    return e2.getId() - e1.getId();
                }
            },
            AGE {
                @Override
                public int compare(Employee e1, Employee e2) {
                    return e2.getAge() - e1.getAge();
                }
            }
        }
        emps.stream().sorted(EmployeeComparatorEx.ID).forEach(System.out::println);
        System.out.println();
        emps.stream().sorted(EmployeeComparatorEx.AGE).forEach(System.out::println);
        System.out.println();
    }

    @Test
    @DisplayName("Замыкания")
    @Description("Блок кода, представляющий собой лямбда-выражение вместе со значения-\n" +
            "ми локальных переменных и параметров метода, в котором он объявлен, назы-\n" +
            "вается замыканием")
    public void exampleClosure() {
        // Пример
        /*
        Замыкания не запрещают использования полей класса как статических, так
        и нестатических.
         */
        class FunctionalBuilderEx1<T> {
            public static Function<String, Integer> build(String strNum) {
                int count = 1;// не явно в замыкании становится как final
                return t -> Integer.valueOf(strNum + t) + count;
            }
        }
        Function<String, Integer> function = FunctionalBuilderEx1.build("100");//Создаем функцию
        System.out.println(function.apply("123"));
        class FunctionalBuilderEx2<T> {
            public static Function<String, Integer> build(String strNum) {
                int[] count = {1};// не явно в замыкании становится как final, но т.к массив это объект его значение можно менять
                return t -> Integer.valueOf(strNum + t) + ++count[0];
            }
        }
        function = FunctionalBuilderEx2.build("100");//создаем функцию
        System.out.println(function.apply("123"));
        System.out.println(function.apply("123"));
        System.out.println(function.apply("123"));
    }
}
