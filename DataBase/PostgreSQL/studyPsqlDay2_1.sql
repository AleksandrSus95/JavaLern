--Агрегатные функции
--count() - получить количество строк
--min() - минимальное значение
--max() - максимальное значение

--Дополним таблицы данными
INSERT INTO venues (name, street_address, postal_code, country_code)
VALUES ('Bolivar','Borodina street','426000','ru'),
('My Place','School street', '426000', 'ru');

INSERT INTO events (title, starts, ends, venue_id)
VALUES ('Moby','2012-02-06 21:00','2012-02-06 23:00',(
    SELECT venue_id
    FROM venues
    WHERE name = 'Crystal Ballroom'
)), ('Irishka Day','2012-08-23 00:00', '2012-08-23 23:59',(
    SELECT venue_id
    FROM venues
    WHERE name = 'Ronni Burger'
)), ('Grishin Day', '2012-02-06 19:00', '2012-02-06 22:00', (
    SELECT venue_id
    FROM venues
    WHERE name = 'Bolivar'
)),('Wedding', '2012-02-26 21:00', '2012-02-26 23:00', (
    SELECT venue_id
    FROM venues
    WHERE name = 'Voodoo Donuts'
)), ('Dinner with Mom', '2012-02-26 18:00', '2012-02-26 20:30', (
    SELECT venue_id
    FROM venues
    WHERE name = 'My Place'
)), ('Valentine"s Day', '2012-02-14 00:00', '2012-02-14 23:59', NULL) RETURNING event_id;

--Пример Агрегитвных запросов
SELECT count(title)
FROM events
WHERE title LIKE '%Day%';

SELECT min(starts), max(ends)
FROM events JOIN venues
ON events.venue_id = venues.venue_id
WHERE venues.name = 'Crystal Ballroom';

--Группировка
--GROUP BY группироует данные по зданным полям, с помощью ее можно сгруппировать строка а заетам применить к отдельным
--группам ту или иную агрегатную функцию (например, count())

--Посчтиаем количество мероприятий в каждом месте проведения
SELECT venue_id, count(*)
FROM events
GROUP BY venue_id;

SELECT v.name, groupv.venue_id, groupv.manyEvents
FROM (SELECT venue_id, count(*) as manyEvents
FROM events
GROUP BY venue_id) as groupv, venues v
WHERE v.venue_id = groupv.venue_id;

--В паре с фразой GROUP BY идет
--ключевое слово HAVING , которое работает, как WHERE, но применяется
--для фильтрации результатов агрегатных функций (WHERE для этой
--цели не годится).

--Пример
SELECT venue_id
FROM events
GROUP BY venue_id
HAVING count(*) >= 2 AND venue_id IS NOT NULL;

--Можно использовать GROUP BY вообще без агрегатных функций.
--Если написать SELECT...FROM...GROUP BY имя одного столбца, то
--будут возвращены все уникальные значения в этом столбце.

SELECT venue_id FROM events GROUP BY venue_id;

--Подобная группировка встречается настолько часто, что в SQL
--для нее предусмотрено специальное ключевое слово DISTINCT.

SELECT DISTINCT venue_id FROM events;

--Результаты этих запросов будут идентичны.
--Еще примеры
SELECT e.event_id, e.title, e.starts, e.ends, v.venue_id, v.name FROM (
SELECT venue_id
FROM events
GROUP BY venue_id
HAVING count(*) >= 2 AND venue_id IS NOT NULL) AS venueID
JOIN venues v ON venueID.venue_id = v.venue_id
JOIN events e ON venueID.venue_id = e.venue_id;

--Оконные функции
--Оконные функции похожи на запросы с GROUP BY в том смысле,
--что позволяют применять агрегатные функции к нескольким стро-
--кам. Различие же в том, что они позволяют использовать встроенные
--агрегатные функции, не требуя, чтобы каждое поле включалось толь-
--ко в одну строку сгруппированного результата.
--
--Пример запроса с группировкой который возовет ошибку
SELECT title, venue_id, count(*)
FROM events
GROUP BY venue_id;

--Проблема которая возникает при таком вызове:
--Мы подсчитываем строки с одинаковым значением venue_id, но
--у мероприятий с названиями (title) LARP Club и Wedding значение
--venue_id одно и то же. Postgres не знает, какое из них отображать.
--
--Если GROUP BY возвращает по одной записи на каждое группируе-
--мое значение, то оконная функция может вернуть несколько записей
--для каждой строки.
--
--Оконная функция возвращает все удовлетваряющие запросу строки, реплецируя результаты агрегатных функций.
--
--Пример использования оконноый функции

SELECT title, venue_id, count(*) OVER ( PARTITION BY venue_id )
FROM events;

--Грубо говоря оконаая функция разбивает данные на группы но при ее использовании строки не группируются в одну.
--А группировка получается разбивает данные на группы и выводит одно значение - значение группы, с помощью нее мы можем найти
--например сколько мироприятий запланировано в месте под айдишником таким-то, еще раз пример такой группировки
SELECT e.venue_id, count(*)
FROM events e
GROUP BY venue_id;
--и если понадобится то потом к группировке можно применить фильтрацию, но не с помощью WHERE а с помощью оператора HAVING,
--он специально предназначен для фильтрации групп.

--Еще один пример
SELECT title, venue_id, starts, count(*)
OVER ( PARTITION BY venue_id ORDER BY starts DESC )
FROM events;

--Транзакции
--Транзакция гарантирет выполнение команд входящих в ее группу, транзакции являются атамарными. Если хотябы одна операция
--внутри транзакции завершается с ошибкой, то откатываются все команды, как будто они вообще не выполнялись.
--
--Любую транзакцию можно заключить в блок BEGIN TRANSACTION.
--Синтаксис:
--BEGIN TRANSACTION;
--тут выполняем команды, делаем селекты, в общем работаем с базой.
--Выполнив код в этом блоке и убедившись что ошибок нет мы можем
--сделать COMMIT и тогда данные будут изменены в БД. Если что то пошло не так и
--и нам надо все откатить то можно сделать ROLLBACK тогда все измениня будут откачены и база вернется
--в состояние как будто ничего и не было.
--Или мы можем использовать ключевое слово END - после него транзакция будет закрыта
--и как Я понял если ошибок никаких не было то к базе будут применены изменения которые мы сделали в рамках этой транзакции.
--
--END фиксирует текущую транзакцию. Все изменения, произведённые этой транзакцией, становятся видимыми для других и
--гарантированно сохранятся в случае сбоя. Эта команда является расширением PostgreSQL и равнозначна команде COMMIT.
--
--После выполнения комманд COMMIT | ROLLBACK | END транзакция закрывается и изменения становятся видимыми для других пользователей
--или в случае ошибки или ROLLBACK транзакция не менят базу;

--Примеры
BEGIN TRANSACTION;
DELETE FROM events;
SELECT * FROM events; -- тут покажет что все данные из таблицы удалены
ROLLBACK; -- откатываем изменения и закрываем транзакцию
SELECT* FROM events; -- показываем что в таблице events ничего не изменилось.

--Не явные транзакции
--Все команды которые мы выполняли(ем) в psql, бьыли частью не явной транзакции. Если например было начато выполнение
--команды DELETE FROM account WHERE total < 20; и база не завершив удаление до конца грохнулась, то после перезапуска сервера
--команда будет отменена
--Транзакции полезны когда модифицируются 2 и более таблицы, которые должны быть согласованы между собой.
