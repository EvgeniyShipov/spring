package ru.otus.spring.repository;

import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Jenre;

import java.util.List;

public interface BookRepository {

    Book getById(long id);

    List<Book> getByAuthor(Author author);

    List<Book> getByJenre(Jenre jenre);

    List<Book> getAll();

    Book create(Book book);

    void delete(Book book);

    void update(Book book);
}
