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