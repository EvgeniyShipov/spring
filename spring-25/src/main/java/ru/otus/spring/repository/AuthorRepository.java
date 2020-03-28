package ru.otus.spring.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.spring.domain.Author;

public interface AuthorRepository extends CrudRepository<Author, Long> {

}
