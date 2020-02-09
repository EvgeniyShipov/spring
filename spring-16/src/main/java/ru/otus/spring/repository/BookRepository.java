package ru.otus.spring.repository;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Jenre;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends CrudRepository<Book, Long> {

    Optional<Book> findById(String id);

    @Query("select b from Book b join fetch b.author join fetch b.jenre where b.author = :author")
    List<Book> findByAuthor(Author author);

    @Query("select b from Book b join fetch b.author join fetch b.jenre where b.jenre = :jenre")
    List<Book> findByJenre(Jenre jenre);
}
