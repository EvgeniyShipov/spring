package ru.otus.spring.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.domain.Author;

import java.util.Optional;

public interface AuthorRepository extends MongoRepository<Author, Long> {

    Optional<Author> findById(String id);

    void deleteById(String id);
}
