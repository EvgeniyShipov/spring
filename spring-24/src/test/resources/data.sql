insert into AUTHORS ("NAME", "SURNAME", "PATRONYMIC")
values ('John', 'Steinbeck', 'Ernst');

insert into JENRE ("TYPE")
values ('COMEDY');
insert into JENRE ("TYPE")
values ('TRAGEDY');
insert into JENRE ("TYPE")
values ('DRAMA');
insert into JENRE ("TYPE")
values ('HORROR');

insert into BOOKS ("TITLE", "ID_AUTHOR", "ID_JENRE")
values ('The Winter of Our Discontent', 1, 1);

insert into COMMENTS ("MESSAGE", "ID_BOOK")
values ('Just a message', 1);

-- ACL SECURITY
INSERT INTO acl_sid (id, principal, sid)
VALUES (1, 1, 'admin'),
       (2, 1, 'editor'),
       (3, 0, 'user');

INSERT INTO acl_class (id, class)
VALUES (1, 'ru.otus.spring.domain.Book');

INSERT INTO acl_object_identity (id, object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting)
VALUES (1, 1, 1, NULL, 3, 0),
       (2, 1, 2, NULL, 3, 0),
       (3, 1, 3, NULL, 3, 0);

INSERT INTO acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure)
VALUES
(1, 1, 1, 1, 1, 1, 1, 1),
(2, 1, 2, 1, 2, 1, 1, 1),
(3, 1, 3, 3, 1, 1, 1, 1),
(4, 2, 1, 2, 1, 1, 1, 1),
(5, 2, 2, 3, 1, 1, 1, 1),
(6, 3, 1, 3, 1, 1, 1, 1),
(7, 3, 2, 3, 2, 1, 1, 1);