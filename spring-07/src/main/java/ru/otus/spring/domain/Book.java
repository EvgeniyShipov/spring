package ru.otus.spring.domain;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Book {
    private long id;
    private String title;
    private Author author;
    private Jenre jenre;
}
