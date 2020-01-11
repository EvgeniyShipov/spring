package ru.otus.spring.repository;

import ru.otus.spring.domain.Author;

import java.util.List;

public interface AuthorRepository {
    
    Author getById(long id);
    
    List<Author> getAll();

    void delete(Author author);

    Author create(Author author);
}
