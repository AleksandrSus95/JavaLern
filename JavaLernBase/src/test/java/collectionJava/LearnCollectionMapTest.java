package collectionJava;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class LearnCollectionMapTest {

    @Test
    public void collectionHashMapTest() {
        Map<Integer, String> map = new HashMap<>(); // HashMap не сохраняет порядок добавления элементов
        map.put(1, "Aleksandr");
        map.put(2, "Irina");
        map.put(3, "Darya");
        map.put(4, "Nikolay");
        map.put(43, "Nikolay");
        map.put(54, "Aleksandr");
        System.out.println(map);
        map.put(3, "Marina"); // По ключу 3 значение будет перезаписано
        System.out.println(map);
        map.put(null, "TestNull"); // Что ключ что значение может быть налл
        map.put(123, null);
        // Некоторые методы
        map.putIfAbsent(1, "TestAbsent"); // Значение не будет перезаписано, т.к такой ключ уже сущестует
        // получается что putIfAbsent добавлет элементо только в случае если его еще нету в карте, если оно есть то перезаписано значение по существующему ключу не будет.
        System.out.println(map);
        map.remove(4); // Удаляет элемент
        System.out.println(map);
        System.out.println(map.containsValue("Irina")); // Проверит есть ли такое значение в хэшмэпе
        System.out.println(map.containsKey(500)); // проверит есть ли такой ключ уже в карте
        System.out.println(map.keySet()); // Вернет множество всех ключей карты
        System.out.println(map.values()); // Вернет все значения коллекции
    }

    /**
     * Пример того, нафига надо переопределять к классах хэшкод.
     */
    @Test
    public void exampleHashMapTest() {
        StudentWithoutHash st1 = new StudentWithoutHash("Nikolay", 1);
        StudentWithoutHash st2 = new StudentWithoutHash("Maksim", 5);
        StudentWithoutHash st3 = new StudentWithoutHash("Egor", 3);
        StudentWithoutHash st4 = new StudentWithoutHash("Marina", 4);
        StudentWithoutHash st5 = new StudentWithoutHash("Anna", 6);
        StudentWithoutHash st6 = new StudentWithoutHash("Anna", 6);
        Map<StudentWithoutHash, Double> incorrectMap = new HashMap<>();
        incorrectMap.put(st1, 2.5);
        incorrectMap.put(st2, 53.0);
        incorrectMap.put(st3, 0.1);
        incorrectMap.put(st4, 5.0);
        incorrectMap.put(st5, 4.1);
        System.out.println(incorrectMap);
        System.out.println(incorrectMap.containsKey(st6)); // Проверим содерижт ли карта ключ st6 (st5 и st6 являются эквивалентными объектами) ответ будет фолсе,
        System.out.println(st5.equals(st6)); // но при сравнении st5 и st6 будет труе
        // Это происходит как раз изза того что у нас в этом классе не переопределен метод хэшкод

        /*
         * HashMap и HashSet коллекции которые используют хэширование при поиске и сравнении
         */
        StudentWithCorrectHash st1_1 = new StudentWithCorrectHash("Nikolay", 1);
        StudentWithCorrectHash st2_2 = new StudentWithCorrectHash("Maksim", 5);
        StudentWithCorrectHash st3_3 = new StudentWithCorrectHash("Egor", 3);
        StudentWithCorrectHash st4_4 = new StudentWithCorrectHash("Marina", 4);
        StudentWithCorrectHash st5_5 = new StudentWithCorrectHash("Anna", 6);
        StudentWithCorrectHash st6_6 = new StudentWithCorrectHash("Anna", 6);
        Map<StudentWithCorrectHash, Double> correct = new HashMap<>();
        correct.put(st1_1, 2.5);
        correct.put(st2_2, 53.0);
        correct.put(st3_3, 0.1);
        correct.put(st4_4, 5.0);
        correct.put(st5_5, 4.1);
        System.out.println(correct);
        System.out.println(correct.containsKey(st6_6)); // Проверим содерижт ли карта ключ st6 (st5 и st6 являются эквивалентными объектами) ответ будет труе,
        System.out.println(st5_5.equals(st6_6)); // м при сравнении они тоже дадут труе (так как они эквивалентны)
        // Это происходит как раз изза того что у нас в этом классе переопределен метод хэшкод
        // Вообще чтобы корректно работала коллекция hashCode должны быть переопределены оба метода как equals так и hashCode
        // Внутри HashMap при поиске пользуется обоими этими методи ищет сначала по хэшкоду и потом проверят по эквивалентности
    }

    class StudentWithoutHash{
        private String name;
        private Integer course;

        public StudentWithoutHash(String name, Integer course) {
            this.name = name;
            this.course = course;
        }

        public String getName() {
            return name;
        }

        public Integer getCourse() {
            return course;
        }

        @Override
        public String toString() {
            return "StudentWithoutHash{" +
                    "name='" + name + '\'' +
                    ", course=" + course +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            StudentWithoutHash that = (StudentWithoutHash) o;
            return Objects.equals(name, that.name) && Objects.equals(course, that.course);
        }
    }

    class StudentWithCorrectHash {
        private String name;
        private Integer course;

        public StudentWithCorrectHash(String name, Integer course) {
            this.name = name;
            this.course = course;
        }

        public String getName() {
            return name;
        }

        public Integer getCourse() {
            return course;
        }

        @Override
        public String toString() {
            return "StudentWithoutHash{" +
                    "name='" + name + '\'' +
                    ", course=" + course +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            StudentWithCorrectHash that = (StudentWithCorrectHash) o;
            return Objects.equals(name, that.name) && Objects.equals(course, that.course);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, course);
        }
    }
}
