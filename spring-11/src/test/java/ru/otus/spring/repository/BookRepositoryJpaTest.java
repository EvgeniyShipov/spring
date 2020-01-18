package ru.otus.spring.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Jenre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class BookRepositoryJpaTest {
    private static final long AUTHOR_ID = 1;
    private static final long JENRE_ID = 1;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void getByAuthor() {
        Author author = new Author().setId(AUTHOR_ID);

        List<Book> actualBooks = bookRepository.findByAuthor(author);

        Book expectedBook = entityManager.find(Book.class, actualBooks.get(0).getId());

        assertThat(expectedBook).isEqualTo(actualBooks.get(0));
    }

    @Test
    void getByJenre() {
        Jenre jenre = new Jenre().setId(JENRE_ID);

        List<Book> actualBooks = bookRepository.findByJenre(jenre);

        Book expectedBook = entityManager.find(Book.class, actualBooks.get(0).getId());
        assertThat(expectedBook).isEqualTo(actualBooks.get(0));
    }
}