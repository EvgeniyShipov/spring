package ru.otus.spring.dao;

import ru.otus.spring.domain.Author;

import java.util.List;

public interface AuthorDao {
    
    Author getById(int id);
    
    List<Author> getAll();

    void deleteById(int id);

    void create(Author author);
}
