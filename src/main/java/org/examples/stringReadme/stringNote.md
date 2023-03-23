**Некоторые методы класса String**
- `String concat(String s)` или оператор «+»—слияние строк;
- `boolean equals(Object ob) и equalsIgnoreCase(String s)` —сравнение строк с учетом и без учета нижнего и верхнего регистра символов соответственно;
- `int compareTo(String s) и compareToIgnoreCase(String s)` —лексикографическое сравнение строк с учетом и без учета их регистра. Метод осуществляет
вычитание кодов первых различных символов вызывающей и передаваемой строки в метод строк и возвращает целое значение. Метод возвращает значе-
ние 0 в случае, когда equals() возвращает значение true;
- `boolean contentEquals(CharSequence ob)` — сравнение строки и содержимого объекта типа StringBuffer, StringBuilder и пр.;
- `booleanmatches(String regex)` — проверка строки на соответствие регулярному выражению;
- `String substring(int n, int m)` — извлечение из строки подстроки длины m-n, начиная с позиции n. Нумерация символов в строке начинается с нуля;
- `String substring(int n)` — извлечение из строки подстроки, начиная с позиции n;
- `int length()` — определение длины строки;
- `int indexOf(char ch)` — определение позиции символа в строке;
- `static String valueOf(type v)` — преобразование переменной базового типа к строке;
- `String toUpperCase()/toLowerCase()` — преобразование всех символов вызывающей строки в верхний/нижний регистр;
- `String replace(char с1, char с2)` — замена в строке всех вхождений первого символа вторым символом;
- `String replaceAll(String regex, String replacement)` — замена в строке всех подстрок, соответствующих регулярному выражению, новой строкой, см. так-же replaceFirst();
- `String intern()` — заносит строку в «пул» литералов и возвращает ее объектную ссылку;
- `String strip()` — удаление всех пробелов в начале и конце строки, более совершенный аналог метода trim(), см. также методы stripLeading() и stripTrailing();
- `char charAt(int position)` — возвращение символа из указанной позиции (нумерация с нуля);
- `boolean isEmpty()` —возвращает true, если длина строки равна 0;
- `boolean isBlank()` — возвращает true, если строка пуста или содержит только пробельные символы;
- `static String join(CharSequence delimiter, CharSequence... elements)` — объединение произвольного набора строк (коллекции строк) в одну строку с заданной строкойразделителем;
- `char[] getChars(int srcBegin, int srcEnd, char[] dst, int dstBegin)` — извлечение символов строки в массив символов;
- `static String format(String format, Object… args), format(Locale l, Stringformat, Object… args)` — создание форматированной строки, полученной с использованием формата, локализации и др.;
- `String[] split(String regex), String[] split(String regex, int limit)` — поиск вхождения в строку заданного регулярного выраженияшаблона
в качестве разделителя и деление исходной строки в соответствии с этим разделителем на массив строк;
- `IntStream codePoints()` — извлечение символов строки в поток (stream) их кодов;
- `IntStream chars()` — преобразование строки в stream ее символов;
- `Stream<String> lines()` — извлечение строк, разделенных символом перехода на другую строку, в поток (stream) строк.
