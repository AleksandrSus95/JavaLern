import jdk.jfr.Description;
import org.examples.collectionsAndStreamApiClass.forStreamApiClass.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.*;

public class ExampleStreamApi {
    private List<Employee> emps = List.of(
            new Employee("Michael", "Smith",   243,  43, Position.CHEF),
            new Employee("Jane",    "Smith",   523,  40, Position.MANAGER),
            new Employee("Jury",    "Gagarin", 6423, 26, Position.MANAGER),
            new Employee("Jack",    "London",  5543, 53, Position.WORKER),
            new Employee("Eric",    "Jackson", 2534, 22, Position.WORKER),
            new Employee("Andrew",  "Bosh",    3456, 44, Position.WORKER),
            new Employee("Joe",     "Smith",   723,  30, Position.MANAGER),
            new Employee("Jack",    "Gagarin", 7423, 35, Position.MANAGER),
            new Employee("Jane",    "London",  7543, 42, Position.WORKER),
            new Employee("Mike",    "Jackson", 7534, 31, Position.WORKER),
            new Employee("Jack",    "Bosh",    7456, 54, Position.WORKER),
            new Employee("Mark",    "Smith",   123,  41, Position.MANAGER),
            new Employee("Jane",    "Gagarin", 1423, 28, Position.MANAGER),
            new Employee("Sam",     "London",  1543, 52, Position.WORKER),
            new Employee("Jack",    "Jackson", 1534, 27, Position.WORKER),
            new Employee("Eric",    "Bosh",    1456, 32, Position.WORKER)
    );

    private List<Department> deps = List.of(
            new Department(1, 0, "Head"),
            new Department(2, 1, "West"),
            new Department(3, 1, "East"),
            new Department(4, 2, "Germany"),
            new Department(5, 2, "France"),
            new Department(6, 3, "China"),
            new Department(7, 3, "Japan")
    );

    @Test
    public void creation() throws IOException {
        Stream<String> lines = Files.lines(Paths.get("some.txt"));
        Stream<Path> list = Files.list(Paths.get("./"));
        Stream<Path> walk = Files.walk(Paths.get("./"), 3);

        IntStream intStream = IntStream.of(1, 2, 3, 4);
        DoubleStream doubleStream = DoubleStream.of(1.2, 3.4);
        IntStream range = IntStream.range(10, 100); // 10 .. 99
        IntStream intStream1 = IntStream.rangeClosed(10, 100); // 10 .. 100

        int[] ints = {1, 2, 3, 4};
        IntStream stream = Arrays.stream(ints);

        Stream<String> stringStream = Stream.of("1", "2", "3");
        Stream<? extends Serializable> stream1 = Stream.of(1, "2", "3");

        Stream<String> build = Stream.<String>builder()
                .add("Mike")
                .add("joe")
                .build();

        Stream<Employee> stream2 = emps.stream();
        Stream<Employee> employeeStream = emps.parallelStream();

        Stream<Event> generate = Stream.generate(() ->
                new Event(UUID.randomUUID(), LocalDateTime.now(), "")
        );

        Stream<Integer> iterate = Stream.iterate(1950, val -> val + 3);

        Stream<String> concat = Stream.concat(stringStream, build);
    }

    @Test
    public void terminate() {
        Stream<Employee> stream = emps.stream();
        stream.count();

        emps.stream().forEach(employee -> System.out.println(employee.getAge()));
        emps.forEach(employee -> System.out.println(employee.getAge()));

        emps.stream().forEachOrdered(employee -> System.out.println(employee.getAge()));

        emps.stream().collect(Collectors.toList());
        emps.stream().toArray();
        Map<Integer, String> collect = emps.stream().collect(Collectors.toMap(
                Employee::getId,
                emp -> String.format("%s %s", emp.getLastName(), emp.getFirstName())
        ));

        IntStream intStream = IntStream.of(100, 200, 300, 400);
        intStream.reduce((left, right) -> left + right).orElse(0);

        System.out.println(deps.stream().reduce(this::reducer));

        IntStream.of(100, 200, 300, 400).average();
        IntStream.of(100, 200, 300, 400).max();
        IntStream.of(100, 200, 300, 400).min();
        IntStream.of(100, 200, 300, 400).sum();
        IntStream.of(100, 200, 300, 400).summaryStatistics();

        emps.stream().max(Comparator.comparingInt(Employee::getAge));

        emps.stream().findAny();
        emps.stream().findFirst();

        emps.stream().noneMatch(employee -> employee.getAge() > 60); // true
        emps.stream().anyMatch(employee -> employee.getPosition() == Position.CHEF); // true
        emps.stream().allMatch(employee -> employee.getAge() > 18); // true
    }

    @Test
    public void transform() {
        LongStream longStream = IntStream.of(100, 200, 300, 400).mapToLong(Long::valueOf);
        IntStream.of(100, 200, 300, 400).mapToObj(value ->
                new Event(UUID.randomUUID(), LocalDateTime.of(value, 12, 1, 12, 0), "")
        );

        IntStream.of(100, 200, 300, 400, 100, 200).distinct(); // 100, 200, 300, 400

        Stream<Employee> employeeStream = emps.stream().filter(employee -> employee.getPosition() != Position.CHEF);

        emps.stream()
                .skip(3)
                .limit(5);

        emps.stream()
                .sorted(Comparator.comparingInt(Employee::getAge))
                .peek(emp -> emp.setAge(18))
                .map(emp -> String.format("%s %s", emp.getLastName(), emp.getFirstName()));

        emps.stream().takeWhile(employee -> employee.getAge() > 30).forEach(System.out::println);
        System.out.println();
        emps.stream().dropWhile(employee -> employee.getAge() > 30).forEach(System.out::println);

        System.out.println();

        IntStream.of(100, 200, 300, 400)
                .flatMap(value -> IntStream.of(value - 50, value))
                .forEach(System.out::println);
    }

    @Test
    public void real() {
        Stream<Employee> empl = emps.stream()
                .filter(employee ->
                        employee.getAge() <= 30 && employee.getPosition() != Position.WORKER
                )
                .sorted(Comparator.comparing(Employee::getLastName));

        print(empl);

        Stream<Employee> sorted = emps.stream()
                .filter(employee -> employee.getAge() > 40)
                .sorted((o1, o2) -> o2.getAge() - o1.getAge())
                .limit(4);

        print(sorted);

        IntSummaryStatistics statistics = emps.stream()
                .mapToInt(Employee::getAge)
                .summaryStatistics();

        System.out.println(statistics);
    }

    @Test
    @DisplayName("Использование итераторов")
    @Description("Итераторы используются для последовательно доступа кэлементам коллекции")
    public void usingIterators(){
        Iterator<Employee> employeeIterator = emps.iterator();
        while (employeeIterator.hasNext()){
            Employee employee = employeeIterator.next();
            System.out.println(employee);
        }
        //forEach using iterator
        System.out.println("Using Iterator in forEach");
        emps.forEach(System.out::println);
        //Исключительная ситуация
        emps.add(new Employee("Michael", "Smith",   243,  43, Position.CHEF));
        System.out.println("Должно выпасть исключение " +  employeeIterator.next());
    }

    @Test
    @DisplayName("Алгоритмы сведения Collectors")
    @Description("Примеры использования методов класса Collectors в StreamAPI")
    public void exampleUsingCollectors(){
        List<String> strings = List.of("as a the d on and".split("\\s+"));
        // toCollection(Supplier<C> collectionFactory), toList(), toSet() — преобразо-
        // вание в коллекцию;
        List<Integer> listLengths = strings.stream()
                .map(s -> s.length())
                .collect(Collectors.toList());
        System.out.println(listLengths);
        // Пример с сылками на конструктор
        List<Character> listFirstSymbol = strings.stream()
                .map(s -> s.charAt(0))
                .collect(Collectors.toCollection(ArrayList::new));
        System.out.println(listFirstSymbol);
        // joining(CharSequence delimiter) —обеспечивает конкатенацию строк с за-
        // данным разделителем;
        String results = strings.stream()
                .map(String::toUpperCase)
                .collect(Collectors.joining(":"));
        System.out.println(results);
        // mapping(Function<? super T,? extends U> mapper,Collector<? super U,A,R> downstream) -
        // позволяет преобразовать элементы одного типа в элементы другого типа
        List<Integer> listCode = strings.stream()
                .collect(Collectors.mapping(s -> (int) s.charAt(0), Collectors.toList()));
        System.out.println(listCode);
        // Поиск максимального значения первых символов строки
        int max = strings.stream()
                .collect(Collectors.mapping(s -> (int) s.charAt(0),
                        Collectors.maxBy(Integer::compareTo))).orElse(-1);
        System.out.println(max);
        // minBy(Comparator<? super T> c)/maxBy(Comparator<? super T> c) - коллектор для
        // нахождения минимального или максимального элемента в потоке;
        String minLex = strings.stream()
                .collect(Collectors.minBy((s1, s2) -> s1.length() - s2.length()))
                .orElse("none");
        System.out.println(minLex);
        // filtering(Predicate<? super T> predicate, Collector<? super T,A,R> downstream) -выполняет фильтрацию элементов;
        List<String> list = strings.stream()
                .collect(Collectors.filtering(s -> s.length() > 1, Collectors.toList()));
        System.out.println(list);
        // counting()—позволяет посчитать количество элементов потока;
        long counter = strings.stream()
                .collect(Collectors.counting());
        System.out.println(counter);
        // summingInt(ToIntFunction<? super T> mapper) - выполняет суммирование элементов.
        // Существуют версии для Long и Double;
        int length = strings.stream()
                .collect(Collectors.summingInt(String::length));
        System.out.println(length);
        // averagingInt(ToIntFunction<? super T> mapper) - вычисляет среднее
        // арифметическое элементов потока. Существуют версии для Long и Double;
        Double averagingLength = strings.stream()
                .collect(Collectors.averagingDouble(String::length));
        System.out.println(averagingLength);
        // reducing(T identity, BinaryOperator<T> op) — коллектор, осуществляющий редукцию (сведение) элементов
        // на основании заданного бинарного оператора;
        int sumCodeFirstChar = strings.stream()
                .map(s -> (int) s.charAt(0))
                .collect(Collectors.reducing(0, (a, b) -> a + b));
        System.out.println(sumCodeFirstChar);
        // reducing(U identity, Function<? super T,? extends U> mapper,BinaryOperator<U> op) - аналогичное предыдущему действие.
        // groupingBy(Function<? super T, ? extends K> classifier)—коллектор группировки элементов потока
        Map<Integer, List<String>> byLength = strings.stream()
                .collect(Collectors.groupingBy(String::length));
        System.out.println(byLength);
        // partitioningBy(Predicate<? super T> predicate) - коллектор разбиения элементов потока;
        Map<Boolean, List<String>> boolLength = strings.stream()
                .collect(Collectors.partitioningBy(s -> s.length() < 2));
        System.out.println(boolLength);
    }

    @Test
    @DisplayName("Пример использование stream API")
    @Description("Поиск, удаление, измененеие элемента")
    public void usingStreamApiExample(){
        /*
        Выполнение данного оператора никак не изменит исходный список Employee,
        в то время как два предыдущих способа изменяют внутреннее состояние списка
        Employee. Отсутствие влияния на исходную коллекцию является еще одной
        дополнительной возможностью, предоставляемой Stream API.
         */
        List<Employee> employeeList = emps.stream()
                .filter(f -> f.getAge() < 20)
                .map(f -> {
                    f.setAge(f.getAge() + 100);
                    return f;
                })
                .collect(Collectors.toList());
        employeeList.forEach(System.out::println);
    }

    @Test
    @DisplayName("Пример работы с метасимволом ? в коллекциях")
    public void exampleUsingMethaSymbolInCollections(){
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add( new Employee("Mark",    "Smith",   123,  41, Position.MANAGER));
        employeeList.add( new Employee("Jane",    "Gagarin", 1423, 28, Position.MANAGER));
        employeeList.add( new Employee("Sam",     "London",  1543, 52, Position.WORKER));
        employeeList.add( new Employee("Jack",    "Jackson", 1534, 27, Position.WORKER));
        employeeList.add( new Employee("Eric",    "Bosh",    1456, 32, Position.WORKER));
        EmployeeAction action = new EmployeeAction() {
            @Override
            public void employeeRemove(List<? extends Employee> employees, int index) {
                employees.remove(index);
                // Удалить можем но добавить нет
                // Compile Error
                // employees.add(new Employee("Example",    "Example", 1234, 15, Position.WORKER));
            }
            @Override
            public void employeeAdd(List<? super Employee> employees) {
                employees.add(new Employee("Example",    "Example", 1234, 15, Position.WORKER));
            }
        };
        action.employeeRemove(employeeList, 1);
        action.employeeAdd(employeeList);
    }

    private void print(Stream<Employee> stream) {
        stream
                .map(emp -> String.format(
                        "%4d | %-15s %-10s age %s %s",
                        emp.getId(),
                        emp.getLastName(),
                        emp.getFirstName(),
                        emp.getAge(),
                        emp.getPosition()
                ))
                .forEach(System.out::println);

        System.out.println();
    }

    public Department reducer(Department parent, Department child) {
        if (child.getParent() == parent.getId()) {
            parent.getChild().add(child);
        } else {
            parent.getChild().forEach(subParent -> reducer(subParent, child));
        }

        return parent;
    }
}