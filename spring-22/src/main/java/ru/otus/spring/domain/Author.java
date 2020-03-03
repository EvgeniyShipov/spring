package ru.otus.spring.domain;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Accessors(chain = true)
@Document(collection = "authors")
public class Author {

    @Id
    private String id;
    private String name;
    private String surname;
    private String patronymic;

    public String getFullName() {
        return String.format("%s %s %s", surname, name, patronymic);
    }
}
