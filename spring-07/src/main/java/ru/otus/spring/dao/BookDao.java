package ru.otus.spring.dao;

import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;

import java.util.List;

public interface BookDao {

    Book getById(long id);

    List<Book> getByAuthor(Author author);

    List<Book> getAll();

    void create(Book book);

    void deleteById(long id);

    void update(Book book);
}
