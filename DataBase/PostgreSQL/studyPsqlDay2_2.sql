--Хранимые процедуры
--
--Постгресс предоставляет обширный функционал по управлению данными, практически это язык программированя,
--предназначение которого - управлять данными (Язык PL/psSQL - Procedural Language/PostgreSQL).
--Одна из мощьнейшних возможностей - создание новх процедур.
--
--Пример процедуры упрощающей вставку в таблицу venues

CREATE OR REPLACE FUNCTION add_event(title text, starts timestamp, ends timestamp, venue text, postal varchar(9), country char(2))
RETURNS boolean AS $$
DECLARE
    did_insert boolean := false;
    found_count integer;
    the_venue_id integer;
BEGIN
    SELECT venue_id INTO the_venue_id
    FROM venues v
    WHERE v.postal_code = postal AND
          v.country_code = country AND
          v.name ILIKE venue
    LIMIT 1;
    
    IF the_venue_id IS NULL THEN
        INSERT INTO venues (name, postal_code, country_code)
        VALUES (venue_id, postal, country)
        RETURNING venue_id INTO the_venue_id;

        did_insert := true;
    END IF;

    -- Примечание это не равносильно error как в некоторых языках программирования
    RAISE NOTICE 'Venues found %', the_venue_id;

    INSERT INTO events (title, starts, ends, venue_id)
    VALUES (title, starts, ends, the_venue_id);
    RETURN did_insert;
    END;
$$ LANGUAGE plpgsql;

--Эту функцию можно набрать руками в терминал или же записать в файл с расширением .sql например add_event.sql
--затем импортировать в текущую схему book=# \i add_event.sql
--Перед выполнение этой процедуры посмотрим что у нас лежит в таблицах venues и events
SELECT * FROM events;
SELECT * FROM venues;

--Добавим событие с помощью новой функции
SELECT add_event('House Party', '2012-05-03 23:00', '2012-05-04 02:00', 'My Place', '426000', 'ru');

--Еще пару созданных процедур монжо посмотреть в фалу за первый день - studyPsqlDay1.sql


-- Триггеры
-- Триггеры - автоматически запуускают хранимую процедуру, когда происходит определенное событие, например вставка или обновление.

--Создадим таблицу для хранения логов
CREATE TABLE logs(
    event_id integer,
    old_title varchar(255),
    old_starts timestamp,
    old_ends timestamp,
    logged_at timestamp DEFAULT current_timestamp
);

--Теперь напишем процедуру которая будет срабатывать на триггер обновления таблицы UPDATE
CREATE OR REPLACE FUNCTION log_event() RETURNS trigger AS $$
DECLARE
BEGIN
    INSERT INTO logs(event_id, old_title, old_starts, old_ends)
    VALUES (OLD.event_id, OLD.title, OLD.starts, OLD.ends);
    RAISE NOTICE 'Someone just changed event #%', OLD.event_id;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

--Создадим триггер протокалирующий обновление таблицы (запускает процедуру log_event()
CREATE TRIGGER log_events
    AFTER UPDATE ON events
        FOR EACH ROW EXECUTE PROCEDURE log_event();
--Проверим работу триггера обновим таблицу events
UPDATE events
SET ends = '2012-05-04 01:00'
WHERE title = 'House Party';
--Проверим что сработал триггер в таблицу logs добавилась строка после обновления таблицы events
SELECT * FROM logs;
--Триггеры можно настравить чтобы они срабатывали либо до либо после обновления/вставки в таблицу
--
--Представления VIEW
--Представления являются псевдонимами запросов, позволяют использовать результаты сложного запроса
--как еще одну таблицу.
--Определение из инета. Представление - объект базы данных, являющийся результатом выполнения запроса к ней,
--определенного с помощью оператора SELECT.
--
--Синтаксис создания представления:
--CREATE VIEW имя_представления AS запрос_к_БД_типа_SELECT;
--Пример
CREATE VIEW holidays AS
SELECT event_id AS holiday_id, title AS name, starts AS date
FROM events
WHERE title LIKE '%Day%' AND venue_id IS NULL;
--Теперь представление holidays можно опрашивать как таблицу, но под капотом это все равно таблица events.
--Представления очень удобны, когда нужно простым спобом получить доступ к результатам сложного запроса.
--Несколько правил о представлениях
--1. Любой добавляемый в представление столбец должен присутствовать в базовой таблице
--2. Обновить представление непосредственно не возможно, т.е использовать функцию UPDATE к представлению не получится
--   т.к это всего лишь представление (псевдоним запроса) по сути не являющийся таблицей как таковой.
--   (в этом случае не обойтись без правила) - ЭТО ПОКА НЕ ОЧЕНЬ ПОНЯТНО т.к обновление с помощью представления получилось.

--Изменим таблицу events, добавим колонку цветов ассоциирующихся с праздничным днем
ALTER TABLE events
ADD colors text ARRAY;
--Сразу изменим определяющий представление запрос, включим в него массив colors
CREATE OR REPLACE VIEW holidays AS
SELECT event_id AS holiday_id, title AS name, starts AS date, colors
FROM events
WHERE title LIKE '%Day%' AND venue_id IS NULL;
--ОБНОВИТЬ таблицу с помощью представления не выйдет (А вот тут странно так как на самом деле вышло,
--видимо появилась какаято фича)
UPDATE holidays SET color = '{"red","green"}' WHERE name = 'Christmas Day';
--Должен был выдать
--ERROR: cannot update a view
--HINT: You need an unconditional ON UPDATE DO INSTEAD rule.

--Правила RULE
--Правила - это описание способа изменения разобранного дерева запроса. каждый раз когда выполняется команда SQL
--порождается дерево запроса (или его еще называются абстрактным синтаксическим деревом).
--
--Чтобы посмотреть план выполнения запроса используют команду EXPLAIN (фильтр - это фраза WHERE, а OUTPUT - это список столбцов).
--
--Пример команды EXPLAIN
EXPLAIN VERBOSE
SELECT * FROM holidays;
--После выполнения это команды SQL выдаст план выполнения запроса.
--По идее чтобы разрешить обновление представления см. выше holidays мы должны объяснить постгрес что делать с
--командой UPDATE и представлением. Правило будет "запоминать" все обновления holidays и применять их к таблице
--events, получая значения из псевдоотношений NEW и OLD. Функционально NEW - интерпритируется как отношение, содержащие
--новые значения, а OLD - как отношение, содержащее значение старое (до обновления).
--
--Пример создания правила для представления (VIEW) - holidays.
CREATE RULE update_holidays AS ON UPDATE TO holidays DO INSTEAD
UPDATE events
SET title = NEW.name,
    starts = NEW.date,
    colors = NEW.colors
    WHERE title = OLD.name;
--Создав такое правило мы можем обновить таблицу events с помощью представления holidays
UPDATE holidays SET colors = '{"red","green"}' where name = 'Christmas Day';

--Пример не работающией вставки в представление
INSERT INTO holidays (title, starts, ends) VALUES ('New Year','2012-12-31 00:00','2013-01-31 23:59');

--Обновим представление
CREATE OR REPLACE VIEW holidays AS
SELECT event_id AS holiday_id, title AS name, starts AS date, colors, ends AS dateEnd
FROM events
WHERE title LIKE '%Day%' AND venue_id IS NULL;

--Создание правила для вставки INSERT
CREATE RULE insert_holidays AS ON INSERT TO holidays DO INSTEAD
INSERT INTO events (title, starts, colors, ends)
VALUES (NEW.name, NEW.date, NEW.colors, NEW.dateEnd);

--Создание сводных таблиц с помщью crosstab()
--Создадим календарь мероприятий где для каждого месяца в году будет подсчитано количество мероприятий в этом месяце
--Сводная таблица - сводит сгруппированные данные относительно результатов какой-то другой операции (в этом случае списка месяцев).
--Строится в постгрес с помощью функции crosstab().
--
--Еще одна функция - extract() выделяет компоненты даты или времени.

SELECT extract(year from starts) as year, extract(month from starts) as month, count(*)
FROM events
GROUP BY year, month;

--Чтобы воспользоваться функцией crosstab(), запрос должен возвращать 3 столбца
--1. Идентификатор строки - у нас будет год
--2. Категория - в качестве категории у нас будет месяц
--3. Значение - в качестве значения счетчик count()
--Создадим временную таблицу
CREATE TEMPORARY TABLE month_count (month INT);
INSERT INTO month_count VALUES (1), (2), (3), (4), (5), (6), (7), (8), (9), (10), (11), (12);

-- Если он выдаст какието проблемы с функцией crosstab() то создать расширение
CREATE EXTENSION IF NOT EXISTS tablefunc;

--Вызовем функцию crosstab()
SELECT * FROM crosstab(
'SELECT extract(year from starts) as year,
        extract(month from starts) as month,
        count(*)
 FROM events
 GROUP BY year, month',
 'SELECT * FROM month_count') AS (
 year INT,
 jan INT, feb INT, mar INT, apr INT, may INT, jun INT, jul INT, aug INT, sep INT, oct INT, nov INT, dec int)
 ORDER BY YEAR;