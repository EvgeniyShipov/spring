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
    private int id;
    private String name;
    private String surname;
    private String patronymic;
}
