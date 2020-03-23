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
VALUES (1, 0, 'ROLE_ADMIN'),
       (2, 0, 'ROLE_EDITOR'),
       (3, 0, 'ROLE_USER');

INSERT INTO acl_class (id, class)
VALUES (1, 'ru.otus.spring.domain.Book');
INSERT INTO acl_class (id, class)
VALUES (2, 'ru.otus.spring.domain.Author');
INSERT INTO acl_class (id, class)
VALUES (3, 'ru.otus.spring.domain.Comment');
INSERT INTO acl_class (id, class)
VALUES (4, 'ru.otus.spring.domain.Jenre');

INSERT INTO acl_object_identity (id, object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting)
VALUES
-- Book
(1, 1, 1, NULL, 1, 0),
(2, 1, 2, NULL, 1, 0),
-- (3, 1, 3, NULL, 1, 0),
-- Author
(4, 2, 4, NULL, 1, 0),
(5, 2, 5, NULL, 1, 0),
-- (6, 2, 6, NULL, 1, 0),
-- Comment
(7, 3, 7, NULL, 1, 0),
(8, 3, 8, NULL, 1, 0),
(9, 3, 9, NULL, 1, 0),
-- Jenre
(10, 4, 10, NULL, 1, 0),
(11, 4, 11, NULL, 1, 0);
-- (12, 4, 12, NULL, 1, 0);

INSERT INTO acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure)
VALUES
-- ROLE_ADMIN
(1, 1, 1, 1, 1, 1, 1, 1),
(2, 2, 1, 1, 2, 1, 1, 1),

(3, 4, 1, 1, 1, 1, 1, 1),
(4, 5, 1, 1, 2, 1, 1, 1),

(5, 7, 1, 1, 1, 1, 1, 1),
(6, 8, 1, 1, 2, 1, 1, 1),

(7, 10, 1, 1, 1, 1, 1, 1),
(8, 11, 1, 1, 2, 1, 1, 1),
-- ROLE_EDITOR
(9, 1, 2, 2, 1, 1, 1, 1),
(10, 2, 2, 2, 2, 1, 1, 1),

(11, 4, 2, 2, 1, 1, 1, 1),
(12, 5, 2, 2, 2, 1, 1, 1),

(13, 7, 2, 2, 1, 1, 1, 1),
(14, 8, 2, 2, 2, 1, 1, 1),
(15, 9, 2, 2, 3, 1, 1, 1),

(16, 10, 2, 2, 1, 1, 1, 1),
(17, 11, 2, 2, 2, 1, 1, 1),
-- ROLE_USER
(18, 1, 3, 3, 1, 1, 1, 1),
(19, 4, 3, 3, 1, 1, 1, 1),
(20, 7, 3, 3, 1, 1, 1, 1),
(21, 8, 3, 3, 2, 1, 1, 1),
(22, 10, 3, 3, 1, 1, 1, 1);