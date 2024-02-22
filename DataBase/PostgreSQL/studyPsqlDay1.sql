SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

DROP DATABASE book;

CREATE DATABASE book;

\connect book;

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

CREATE SCHEMA studysql;
COMMENT ON SCHEMA studysql IS 'Example schema to study sql';

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;
COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';

DROP EXTENSION IF EXISTS "uuid-ossp";
CREATE EXTENSION IF NOT EXISTS "uuid-ossp" WITH SCHEMA pg_catalog;

SET search_path = studysql, pg_catalog; -- Говорит что подефолту искать в схеме studysql

-- Создадим процедуру lang()
CREATE FUNCTION lang() RETURNS text
    LANGUAGE plpgsql STABLE
    AS $$
BEGIN
  RETURN current_setting('TMS.studysql');
EXCEPTION
  WHEN undefined_object THEN
    RETURN NULL;
END;
$$;

-- Создадим процедуру now()
CREATE FUNCTION now() RETURNS timestamp with time zone
    LANGUAGE sql IMMUTABLE
    AS $$SELECT '2024-01-01 19:00:00'::TIMESTAMP AT TIME ZONE 'Europe/Samara';
$$;

COMMENT ON FUNCTION now() IS 'Point in time according to which the data are generated';

SET default_tablespace = '';
SET default_with_oids = false;

DROP TABLE IF EXISTS countries;
CREATE TABLE countries (
country_code char(2) PRIMARY KEY,
country_name text UNIQUE
);

INSERT INTO countries (country_code, country_name)
VALUES ('us','United States'), ('mx','Mexico'), ('au','Australia'),
('gb','United Kingdom'), ('de','Germany'), ('ru','Russia');

DROP TABLE IF EXISTS cities;
CREATE TABLE cities (
name text NOT NULL,
postal_code varchar(9) CHECK (postal_code <> ''),
country_code char(2) REFERENCES countries,
PRIMARY KEY (country_code, postal_code)
);

INSERT INTO cities
VALUES ('Portland','97205','us'),
('Izhevs','426000','ru');

DROP TABLE IF EXISTS venues;
CREATE TABLE venues (
venue_id SERIAL PRIMARY KEY,
name varchar(255),
street_address text,
type char(7) CHECK ( type in ('public','private') ) DEFAULT 'public',
postal_code varchar(9),
country_code char(2),
FOREIGN KEY (country_code, postal_code)
REFERENCES cities (country_code, postal_code) MATCH FULL
);

INSERT INTO venues (name, postal_code, country_code)
VALUES ('Crystal Ballroom', '97205', 'us'),
('Voodoo Donuts', '97205', 'us'), ('Ronni Burger', '426000','ru') RETURNING venue_id;

DROP TABLE IF EXISTS events;
CREATE TABLE events (
    event_id SERIAL PRIMARY KEY,
    title varchar(255) CHECK (title != '') NOT NULL,
    starts TIMESTAMP NOT NULL,
    ends TIMESTAMP NOT NULL,
    venue_id INTEGER,
    FOREIGN KEY (venue_id)
    REFERENCES venues (venue_id)
);

INSERT INTO events (title, starts, ends, venue_id)
VALUES ('LARP Club', '2012-02-15 17:30', '2012-02-15 19:30', 2),
('April Fools Day', '2012-04-01 00:00', '2012-04-01 23:59', NULL),
('Christmas Day', '2012-12-25 00:00', '2012-12-25 23:59', NULL),
('Beer party Day', '2012-08-15 00:00', '2012-08-15 23:59', 3) RETURNING event_id;

--Заметки про JOIN
--1. Внутреннее соединение INNER JOIN - операция внутреннего соединения. В простом случае с помощью ключевого слова ON
--   задаются 2 столбца (по одному из каждой таблицы), значения которых должны совпадать. Соединение вернет одну таблицу,
--   составленную из 2х с значениями которые совпадают в обоих по условию после ON.
--2. Внешнее соединение LEFT, RIGHT, FULL JOINы - при таком способе должны быть возвращены все строки одной таблицы, даже если
--   им нет соответсвия в другой.
--   LEFT - вернет все строки таблицы слева
--   RIGHT - вернет все сроки таблицы справа
--   FULL - вернет все строки из обоих таблиц.
--
--Ключевое слово OUTER можно опустить оно используется по умолчанию. Так же как и INNER.
--Примеры:
--SELECT e.title, v.name
--FROM events e JOIN venues v
--ON e.venue_id = v.venue_id;
--
--В результате получим
--     title      |     name
------------------+---------------
-- LARP Club      | Voodoo Donuts
-- Beer party Day | Ronni Burger
--
--SELECT e.title, v.name
--FROM events e LEFT OUTER JOIN venues v
--ON e.venue_id = v.venue_id;
--
--В результате получим
--      title      |     name
-------------------+---------------
-- LARP Club       | Voodoo Donuts
-- April Fools Day |
-- Christmas Day   |
-- Beer party Day  | Ronni Burger
--
--SELECT e.title, v.name
--FROM events e RIGHT OUTER JOIN venues v
--ON e.venue_id = v.venue_id;
--
--В результате получим
--     title      |       name
------------------+------------------
-- LARP Club      | Voodoo Donuts
-- Beer party Day | Ronni Burger
--                | Crystal Ballroom
--
--SELECT e.title, v.name
--FROM events e FULL JOIN venues v
--ON e.venue_id = v.venue_id;
--
--В результате получим
--      title      |       name
-------------------+------------------
-- LARP Club       | Voodoo Donuts
-- April Fools Day |
-- Christmas Day   |
-- Beer party Day  | Ronni Burger
--                 | Crystal Ballroom

-- Индексы
--Индекс – это специальная структура данных, которая позволяет
--избежать полного сканирования таблицы при выполнении запроса.
--
--PostgresQL автоматически создает индексы по первичному ключу, его ключем является значение первичного ключа, а значение
--первичного ключа - указатель на строку на диске. (по дефолту это хэш индесы)
--
--Если требуется сравнить столбцы с помощью операторов меньше/больше/равно, то необходимо использовать индесы типа B-дерево

DROP INDEX IF EXISTS;
CREATE INDEX events_starts
ON events USING btree (starts);

--Разница будет заметна если в таблице миллионы строк (разница в скорости выполнения)

--Про MATCH FULL
--Значения, вставляемые в ссылающиеся столбцы, сверяются со значениями во внешних столбцах внешней таблицы с учётом
--заданного типа совпадения. Возможны три типа совпадения: MATCH FULL (полное совпадение),
--MATCH PARTIAL (частичное совпадение) и тип по умолчанию, MATCH SIMPLE (простое совпадение).
--С MATCH FULL ни один из столбцов составного внешнего ключа не может содержать NULL, кроме случая, когда все внешние столбцы NULL;
--в этом случае строка может не иметь соответствия во внешней таблице. С MATCH SIMPLE любой из столбцов внешнего ключа может содержать NULL;
--при этом строка с NULL в одном из таких столбцов может не иметь соответствия во внешней таблице.
--Тип MATCH PARTIAL ещё не реализован. (Разумеется, чтобы вопросы со сравнением NULL не возникали,
--к столбцам, ссылающимся на внешние, можно применить ограничения NOT NULL.)

--Изменения таблицы см. ALTER TABLE

--Добавим в таблицу venues столбец active с значением по умолчанию true
ALTER TABLE venues
ADD active BOOLEAN DEFAULT true;