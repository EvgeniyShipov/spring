package ru.otus.spring.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.otus.spring.domain.Book;

import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "books", path = "books")
public interface BookRepository extends MongoRepository<Book, Long> {

    Optional<Book> findById(String id);

    boolean existsByJenreId(String id);

    boolean existsByAuthorId(String id);

    void deleteById(String id);
}
