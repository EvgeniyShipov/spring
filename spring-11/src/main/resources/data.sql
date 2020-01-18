insert into AUTHORS ("NAME", "SURNAME", "PATRONYMIC")
values ('John', 'Steinbeck', 'Ernst');

insert into JENRE ("TYPE") values ('COMEDY');
insert into JENRE ("TYPE") values ('TRAGEDY');
insert into JENRE ("TYPE") values ('DRAMA');
insert into JENRE ("TYPE") values ('HORROR');

insert into BOOKS ("TITLE", "ID_AUTHOR", "ID_JENRE")
values ('The Winter of Our Discontent', 1, 1);

insert into COMMENTS ("MESSAGE", "ID_BOOK")
values ('Just a message', 1);