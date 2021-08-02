drop table if exists books;

create table books (
  id serial
  , title text
  , author text
  , primary key (id)
);

insert into books (title, author)
values
  ('Kotlin 入門', 'Kotlin 太郎')
;