package ru.otus.spring.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Jenre;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends MongoRepository<Book, Long> {

    Optional<Book> findById(String id);

    List<Book> findByAuthor(Author author);

    List<Book> findByJenre(Jenre jenre);

    boolean existsByJenreId(String id);

    boolean existsByAuthorId(String id);

    void deleteById(String id);
}
