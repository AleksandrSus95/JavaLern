**Методы оболочки Optional**

- `<T> Optional<T> empty()`—создает «пустой» объект;
- `<T> Optional<T> of(T value)`—создает объект с ненулевым содержимым;
- `<T> Optional<T> ofNullable(T value)` —создает объект, содержимое кото-
  рого может быть нулевым.
- `boolean isPresent(), boolean isEmpty()` — проверяют пуста оболочка или
  нет;
- `void ifPresent(Consumer<? super T> action)` — если объект присутствует
  в оболочке, то выполняет над ним действие;
- `void ifPresentOrElse(Consumer<? super T> action, Runnable emptyAction)`—
  если объект присутствует в оболочке, то выполняет над ним действие action,
  если отсутствует, то действие emptyAction.
- `T orElse(T other)` —возвращает объект other;
- `T orElseThrow(), <T extends Throwable> T orElseThrow(Supplier<?
  extends X> exception)` — генерирует исключение NoSuchElementException
  или заданное исключение.