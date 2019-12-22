package ru.otus.spring.dao;

import ru.otus.spring.domain.Book;

import java.util.List;

public interface BookDao {

    Book getById(int id);

    List<Book> getByAuthor(int id);

    List<Book> getAll();

    void create(Book book);

    void deleteById(int id);

    void update(Book book);
}
