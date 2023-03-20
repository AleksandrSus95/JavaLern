import jdk.jfr.Description;
import org.examples.functionalInterface.DefaultAndStatic;
import org.examples.functionalInterface.MyFunctionalInterface;
import org.examples.functionalInterface.forExample.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ExampleFunctionalInterface {

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
        List<Employee> emps = List.of(
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
        List<Department> deps = List.of(
                new Department(1, 0, "Head"),
                new Department(2, 1, "West"),
                new Department(3, 1, "East"),
                new Department(4, 2, "Germany"),
                new Department(5, 2, "France"),
                new Department(6, 3, "China"),
                new Department(7, 3, "Japan")
        );

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

    }
}
