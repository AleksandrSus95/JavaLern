import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ExampleMap {
    @Test
    @DisplayName("Пример создания hashMap")
    public void exampleHashMap(){
        Map<String, Integer> map = new HashMap<>();
        map.put("Jeans", 40);
        map.put("T-Shirt", 35);
        map.put("Gloves", 42);
        map.compute("Shoes", (k,v) -> 77);//помещает ключ key и вычисляет значение value при добавлении в вызывающую карту;
        System.out.println(map);
        map.computeIfPresent("Shoes", (k,v) -> v + k.length());//заменяет значение value в вызывающей карте, если ключ с таким значением существует,
        // если же пары с таким ключом не существует, то вставка пары не производится;
        System.out.println(map);
        map.computeIfAbsent("Shoes", v -> 11);//помещает ключ key и значение value в вызывающую карту,
        // если пары с таким ключом не существует, если ключ существует, то замена не производится;
        map.computeIfAbsent("Shoes_2", v -> 11);
        System.out.println(map);
    }

    @Test
    @DisplayName("Создание хэш карты и замена элемента по ключу")
    public void createHashMapAndReplaceObjectByKey(){
        HashMap<String, Integer> hashMap = new HashMap<>();
        hashMap.put("Пряник", 5);
        hashMap.put("Кейфир",1);
        hashMap.put("Хлеб",1);
        hashMap.putIfAbsent("Хлеб", 2);// замена не произойдет
        hashMap.putIfAbsent("Молоко", 5);
        hashMap.computeIfAbsent("Сырок", v -> 3);// добавит пару
        hashMap.computeIfPresent("Сырок", (k,v) -> 4); // заменит значение
        hashMap.computeIfAbsent("Сырок", v -> 3); // замены не произойдет
        System.out.println(hashMap);
        hashMap.put("Пряник",4); // Заменит или добавит новую пару
        System.out.println(hashMap + " Карта После замены элементы");
        System.out.println(hashMap.get("Хлеб") + " - Значение по ключу Хлеб");
        Set<Map.Entry<String,Integer>> entrySet = hashMap.entrySet();
        System.out.println(entrySet);
        entrySet.stream().forEach(e -> System.out.println(e.getKey() + ":" + e.getValue()));
        Set<Integer> values = new HashSet<Integer>(hashMap.values());
        System.out.println(values);
    }
}
