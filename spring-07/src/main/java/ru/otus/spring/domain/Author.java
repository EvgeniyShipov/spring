package ru.otus.spring.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Setter
@Getter
@ToString
@Accessors(chain = true)
public class Author {
    private long id;
    private String name;
    private String surname;
    private String patronymic;
}
