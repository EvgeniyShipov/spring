package ru.otus.spring.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
public class Author {
    private long id;
    private String name;
    private String surname;
    private String patronymic;

    public String getFullName() {
        return String.format("%s %s %s", surname, name, patronymic);
    }
}
