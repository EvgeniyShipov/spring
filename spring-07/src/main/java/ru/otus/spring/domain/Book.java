package ru.otus.spring.domain;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Book {
    private int id;
    private String title;
    private int idAuthor;
    private int idJenre;
}
