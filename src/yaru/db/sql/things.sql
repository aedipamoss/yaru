-- src/yaru/db/sql/things.sql
-- HugSQL file for Things

-- :name create-things-table
-- :command :execute
-- :result :raw
-- :doc Create things table
create table things (
  id integer primary key not null,
  title varchar(40),
  color varchar(40),
  priority varchar(40),
  created_at timestamp not null default current_timestamp
)

-- :name drop-things-table :!
-- :doc Drop things table if it exists
drop table things

-- A :result value of :n below will return affected rows:
-- :name insert-thing :! :n
-- :doc Insert a single thing and return affected row count
insert into things (title, color, priority)
values (:title, :color, :priority)

-- A :result value of generated keys
-- :name insert-thing-return-keys :insert :raw
-- :doc Insert a single thing with an sql returning clause
insert into things (title, color, priority)
values (:title, :color, :priority)

-- A ":result" value of ":1" specifies a single record
-- (as a hashmap) will be returned
-- :name thing-by-id :? :1
-- :doc Get thing by id
select * from things
where id = :id

-- A ":result" value of ":*" specifies a vector of records
-- (as hashmaps) will be returned
-- :name all-things :? :*
-- :doc Get all things
select * from things
order by id

-- Updates thing by id
-- :name update-thing-by-id :! :n
-- :doc Updates thing by id
update things
set title = :title,
    color = :color,
    priority = :priority
where id = :id

-- Deletes thing by id
-- :name delete-thing-by-id :! :n
delete from things where id = :id
