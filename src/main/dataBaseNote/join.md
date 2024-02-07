**Запрос из нескольких таблиц**  
JOIN - используется для объединения нескольких групп данных в единый поток информации.  
Виды:  
- Left Join - все записи из левой таблицы и совпадения из правой
- Inner Join - оба совпадения (дефолтный)
- Right Join - все записи из правой таблицы и совпадения из левой
- Right Join c NULL - только совпадения с права
- Full Outer Join c NULL - все кроме совпадений
- Full Outer Join - все варианты
- Left Join c NULL - только совпадения с лева  
```
SELECT attribute-1, attribute-2...
FROM table-1
JOIN table-2 ON table-1.parameter=table-2.parameter
WHERE table-1.parameter IS 'myData'
```