package ru.otus.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Jenre;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("select b from Book b join fetch b.author join fetch b.jenre")
    List<Book> findAll();

    @Query("select b from Book b join fetch b.author join fetch b.jenre where b.author = :author")
    List<Book> findByAuthor(Author author);

    @Query("select b from Book b join fetch b.author join fetch b.jenre where b.jenre = :jenre")
    List<Book> findByJenre(Jenre jenre);
}
