package ru.otus.spring.domain;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Accessors(chain = true)
@Document(collection = "books")
public class Book {

    @Id
    private String id;
    private String title;
    @DBRef
    private Author author;
    @DBRef
    private Jenre jenre;
}
