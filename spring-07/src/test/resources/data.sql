insert into AUTHORS ("NAME", "SURNAME", "PATRONYMIC")
    values ('John', 'Steinbeck', 'Ernst');

insert into JENRE ("NAME") values ('COMEDY');
insert into JENRE ("NAME") values ('TRAGEDY');
insert into JENRE ("NAME") values ('DRAMA');
insert into JENRE ("NAME") values ('HORROR');

insert into BOOKS ("TITLE", "ID_AUTHOR", "ID_JENRE")
    values ('The Winter of Our Discontent', 1, 1);