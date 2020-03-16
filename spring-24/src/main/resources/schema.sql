CREATE TABLE IF NOT EXISTS JENRE
(
    ID   BIGINT AUTO_INCREMENT PRIMARY KEY,
    TYPE VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS AUTHORS
(
    ID         BIGINT AUTO_INCREMENT PRIMARY KEY,
    NAME       VARCHAR(255) NOT NULL,
    SURNAME    VARCHAR(255) NOT NULL,
    PATRONYMIC VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS BOOKS
(
    ID        BIGINT AUTO_INCREMENT PRIMARY KEY,
    TITLE     VARCHAR(255) NOT NULL,
    ID_AUTHOR INT          NOT NULL,
    ID_JENRE  INT          NOT NULL,
    FOREIGN KEY (ID_AUTHOR) references AUTHORS (ID),
    FOREIGN KEY (ID_JENRE) references JENRE (ID)
);

CREATE TABLE IF NOT EXISTS COMMENTS
(
    ID      BIGINT AUTO_INCREMENT PRIMARY KEY,
    MESSAGE VARCHAR(255) NOT NULL,
    ID_BOOK INT,
    FOREIGN KEY (ID_BOOK) references BOOKS (ID)
);

CREATE TABLE IF NOT EXISTS acl_sid
(
    id        bigint(20)   NOT NULL AUTO_INCREMENT,
    principal tinyint(1)   NOT NULL,
    sid       varchar(100) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY unique_uk_1 (sid,principal)
);

CREATE TABLE IF NOT EXISTS acl_class
(
    id    bigint(20)   NOT NULL AUTO_INCREMENT,
    class varchar(255) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY unique_uk_2 (class)
);

CREATE TABLE IF NOT EXISTS acl_object_identity
(
    id                 bigint(20) NOT NULL AUTO_INCREMENT,
    object_id_class    bigint(20) NOT NULL,
    object_id_identity bigint(20) NOT NULL,
    parent_object      bigint(20) DEFAULT NULL,
    owner_sid          bigint(20) DEFAULT NULL,
    entries_inheriting tinyint(1) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY unique_uk_3 (object_id_class,object_id_identity),
    FOREIGN KEY (object_id_class) REFERENCES acl_class (id),
    FOREIGN KEY (parent_object) REFERENCES acl_object_identity (id),
    FOREIGN KEY (owner_sid) REFERENCES acl_sid (id)
);

CREATE TABLE IF NOT EXISTS acl_entry
(
    id                  bigint(20) NOT NULL AUTO_INCREMENT,
    acl_object_identity bigint(20) NOT NULL,
    ace_order           int(11)    NOT NULL,
    sid                 bigint(20) NOT NULL,
    mask                int(11)    NOT NULL,
    granting            tinyint(1) NOT NULL,
    audit_success       tinyint(1) NOT NULL,
    audit_failure       tinyint(1) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY unique_uk_4 (acl_object_identity,ace_order),
    FOREIGN KEY (acl_object_identity) REFERENCES acl_object_identity (id),
    FOREIGN KEY (sid) REFERENCES acl_sid (id)
);