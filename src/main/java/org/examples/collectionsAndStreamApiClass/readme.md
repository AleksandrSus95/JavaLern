**Коллекции Java**  
**Интерфейсы коллекций**
- Map<K, V>—карта отображения вида «ключзначение»;
- Collection<E> — основной интерфейс коллекций, вершина иерархии коллекций List, Set.
  Также наследует интерфейс Iterable<E>;
- List<E>—специализирует коллекции для обработки упорядоченного набора элементов;
- Set<E>—множество, содержащее уникальные элементы;
- Queue<E>—очередь, где элементы добавляются в один конец списка, а извлекаются из другого конца.  

**Методы работы с коллекциями наследуемых от интерфейса Collection<E>**
- `boolean add(E obj)` - добавляет obj к вызывающему объекту коллекции и возвращает true, если объект добавлен, 
  и false, если obj уже элемент коллекции
- `boolean remove(Object obj)` - удаляет obj из коллекции
- `boolean addAll(Collection<? extends E> c)` - добавляет все элементы коллекции к вызывающей коллекции
- `void clear()` - удаляет все элементы из коллекции
- `boolean contains(Object obj)` - возврящяет true, если вызывающая коллекция содержит элемент obj
- `boolean equals(Object obj)` - возвращяет true, если коллекции эквивалентны
- `boolean isEmpty()` - возвращяет true, если коллекция пуста
- `int size()` - возвращает количествно элементов в коллекции
- `Object[] toArray()` - копирует элементы коллекции в массив байт
- `<T> T[] toArray(T a[])` - копирует элементы коллекции в массив определенного типа

**Появление Stream API**  
Появление stream API обусловленно возникновением методов для создания потоков объектов и работы с функциональными 
интерфейсами:
- default Stream<E> stream() - преобразует коллекцию в поток объектов.
- default Stream<E> parallelStream() - преобразует коллекцию в параллельный поток объектов. Повышает производительность
  при работе с очень большими коллекциями на многоядерных процессорах

**Stream API**  
Интерфейс java.util.stream.Stream<T>—поток объектов для преобразования коллекций, массивов. 
В потоке не хранятся элементы операции, он не модифицирует источник, а формирует в ответ на действие новый поток. 
Операции в потоке не выполняются до момента, пока не потребуется получить конечный результат выполнения.  

Это поток создается на основе массивов/коллекций, элементы которых переходят в состояние информационного ожидания,  
переводящего поток в следующее состояние до достиженеия терминальной цели, после чего поток прекращаят свое существование.  

Не возможно на одном и том же состоянии stream вызывать метод его обработки повторно.
Пример ошибочного использования  
```
Stream<String> stream = strings.stream();
stream.findFirst();
stream.filter(String::isBlank).findAny(); // ошибка так как этот поток достиг терминальной стадии и больще не существует.
```  
Некоторые терминальные методы сведения потока к результату. Результатом может быть новая коллекция, 
объект некоторого класса, число. Промежуточные операции обязательно должны завершаться терминальными, 
иначе они не выполнятся, так как просто не имеют смысла.  

Некоторые промежуточные метода преобразования потоков объектов:
- `filter(Predicate<? super T> predicate)` - выбор элементов из потока, на основании работы предиката в новый поток.
- `map(Function<? super T, ? extends R> mapper)` - изменение всех элементов потока, применяя к каждому элементу функцию
  mapper.
- `flatMap(Function<T, Stream<R>> mapper)` - преобразовывает один объект, как правило составной, в объект более простой 
  структуры, например, массив в строку, список в объект, списое списков в один список.
- `peek(Consumer<T> consumer)` - возвращает поток, содержащий все элементы исходного потока. Используется для просмотра элементов
  в текущем состоянии потока
- `sorted(Comparator<T> comparator) и sort()` - сортировка в новый поток
- `limit(long maxSize)` - ограничивает выходящий поток заданным в параметре значением
- `skip(long n)` - не включает в выходной поток первые n элементов исходного потока
- `distinct()` - удаляет из потока все идентичные элементы

Некоторые терминальные методы сведения потока к результату. Результатом может быть новая коллекция, 
объект некоторого класса, число. Промежуточные операции обязательно должны завершаться терминальными, 
иначе они **не выполнятся**, так как просто не имеют смысла.
- `void forEach(Consumer<T> action)` - выполняет действие над каждым элементом потока. Чтобы результат действия сохранялся, 
  реализация action должна предусматривать фиксацию результата в какомлибо объекте или потоке вывода
- `Optional<T> findFirst()` - находит первый элемент в потоке
- `Optional<T> findAny()` - находит элемент в потоке
- `long count()` - возвращяет число элементов потока
- `boolean allMatch(Predicate<T> predicate)` - возвращает истину, если все элементы потока удовлетворяют условию предиката
- `boolean anyMatch(Predicate<T> predicate)` - возвращает истину, если хотябы один элемент потока удовлетворяет условию предиката
- `boolean noneMatch(Predicate<T> predicate)` - возвращает истину, если ни один элемент потка не удовлетворяет условию предиката
- `Optional<T> reduce(T identity, BinaryOperator<T> accumulator)` - сводит все элементы потока к одноме результирующему объекту
  завернутому в оболочку Optional
  Пример:
  ```
    int sumLength = strings.stream()
      .map(s -> s.length())
      .reduce(0, (n1, n2) -> n1 + n2);
  ```
- `<R, A> R collect(Collector<? super T, A, R> collector)` - собирает все элементы в коллекцию или объект другого типа.  
- `Optional<T> min(Comparator<T> comparator)` - находит минимальный элемент
- `Optional<T>max(Comparator<T> comparator)` - находит максимальный элемент
- 

**Метасимволы в коллекциях**
Метасимволы используются при параметризации коллекций для расширения возможностей самой коллекции и 
обеспечения ее типобезопасности. Например, если параметр метода предыдущегопримера изменить сList<Order>
на List<? extends Order>, то в метод можно будет передавать коллекции, параметризованные любым допустимым типом, 
а именно классом Order и любымего подклассом, что невозможно при записи без анонимного символа.
**Но в методе нельзя будет добавить к коллекции новый элемент, пусть даже
и допустимого типа, так как компилятору неизвестен заранее тип параметриза-
ции списка.**
Пример:  
```
List<Order> action(List<? extends Order> orders) {
// orders.add(new Order(231, 12.f)); // compile error
orders.remove(0); // can be deleted
}
```
**добавление к спискам, параметризованным метасимволом с применением extends, запрещено всегда.**

Параметризация List<? super Order> утверждает, что параметр метода или возвращаемое значение может получить 
список типа Order или любого из его суперклассов, в то же время разрешает добавлять туда экземпляры класса
Order и любых его подклассов.
**В этом случае к списку, возвращенному методом, можно будет добавлять экземпляры класса Order и его подклассов.**
Пример:  
```
List<? super Order> action(List<? super Order> orders) {
orders.add(new Order(231, 12.f));
return orders;
}
```

**Лекция**  
Базовый интерфейс - java.util.Collection  
метода: 
- add()
- contains()
- remove()
- clear()
- iterator()
- size()
- isEmpty()

Интерфейс - iterator - движение вперед по массиву данных
Интерфейс - list - список (определенный порядок при работе)
- ArrayList<>() - список, более производительный чем связанный список
- LinkedList<>() - связанный список (у каждого элемента есть указатель на следующий и на предыдущий)  подходит для создания 
 стэка (Queue) или очереди (Deque).  

**Множество Set**  

- неупорядоченный набор
- элементы уникальны  
Реализации:
- Set<Long> num = HashSet<>(); - Порядок не гарантирует
- Set<Long> num = TreeSet<>(); - Сохраняет порядок

**Интерфейс Map - НЕ входит в интерфейс Collection**  
Это Ассоциативный массив имет ключ и значение.
При использовании map желательно переопределять equals() и hashCode();
Реализации - HashMap, 


