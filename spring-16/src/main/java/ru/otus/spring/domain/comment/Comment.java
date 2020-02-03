package ru.otus.spring.domain.comment;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import ru.otus.spring.domain.book.Book;

@Data
@Accessors(chain = true)
@Document(collection = "comments")
public class Comment {

    @Id
    private String id;
    private String message;
    @DBRef
    private Book book;
}
