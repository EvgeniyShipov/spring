package ru.otus.spring.dao;

import ru.otus.spring.domain.Author;

import java.util.List;

public interface AuthorDao {
    
    Author getById(long id);
    
    List<Author> getAll();

    void deleteById(long id);

    void create(Author author);
}
