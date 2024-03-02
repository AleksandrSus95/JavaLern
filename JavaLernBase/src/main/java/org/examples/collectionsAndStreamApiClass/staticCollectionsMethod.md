**Алгоритмы класса Collections**  
- `<T> void copy(List<? super T> dest, List<? extends T> src)` - копирует все элементы из одного списка в другой;
- `boolean disjoint(Collection<?> c1, Collection<?> c2)` - возвращает true, если коллекции не содержат одинаковых элементов;
- `<T> List <T> emptyList()`, `<K, V> Map <K, V> emptyMap()`, `<T> Set <T> emptySet()` - возвращают пустой список, 
  карту отображения и множество соответственно;
- `<T> void fill(List<? super T> list, T obj)` - заполняет список заданным элементом;
- `int frequency(Collection<?> c, Object o)` - возвращает количество вхождений в коллекцию заданного элемента;
- `<T> boolean replaceAll(List<T> list, T oldVal, T newVal)` - заменяет все заданные элементы новыми;
- `void reverse(List<?> list)` - «переворачивает» список;
- `void rotate(List<?> list, int distance)` - сдвигает список циклически на заданное число элементов;
- `void shuffle(List<?> list)` - перетасовывает элементы списка;
- `singleton(T o)`, `singletonList(T o)`, `singletonMap(K key, V value)` — создают множество, 
  список и карту отображения, позволяющие добавлять только один элемент;
- `<T extends Comparable<? super T>> void sort(List<T> list)`, 
  `<T> void sort(List<T> list, Comparator<? super T> c)` - сортировка списка естественным порядком и 
  с использованием Comparable или Comparator соответственно;
- `void swap(List<?> list, int i, int j)` - меняет местами элементы списка, стоящие на заданных позициях;
- `<T> List<T> unmodifiableList(List<? extends T> list)` — возвращает ссылку на список с запрещением его модификации. 
  Аналогичные методы есть для всех коллекций.