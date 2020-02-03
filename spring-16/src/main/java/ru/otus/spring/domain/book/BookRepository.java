package ru.otus.spring.domain.book;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.otus.spring.domain.author.Author;
import ru.otus.spring.domain.jenre.Jenre;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends CrudRepository<Book, Long> {

    Optional<Book> findById(String id);

    @Query("select b from Book b join fetch b.author join fetch b.jenre where b.author = :author")
    List<Book> findByAuthor(Author author);

    @Query("select b from Book b join fetch b.author join fetch b.jenre where b.jenre = :jenre")
    List<Book> findByJenre(Jenre jenre);
}
