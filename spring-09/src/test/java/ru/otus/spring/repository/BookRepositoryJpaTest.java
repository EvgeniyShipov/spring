package ru.otus.spring.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Jenre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(BookRepositoryJpa.class)
class BookRepositoryJpaTest {
    private static final long AUTHOR_ID = 1;
    private static final long JENRE_ID = 1;
    private static final long BOOK_ID = 1;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void getById() {
        Book actualBook = bookRepository.getById(BOOK_ID);

        Book expectedBook = entityManager.find(Book.class, actualBook.getId());

        assertThat(expectedBook).isEqualTo(actualBook);
    }

    @Test
    void getByAuthor() {
        Author author = new Author().setId(AUTHOR_ID);

        List<Book> actualBooks = bookRepository.getByAuthor(author);

        Book expectedBook = entityManager.find(Book.class, actualBooks.get(0).getId());

        assertThat(expectedBook).isEqualTo(actualBooks.get(0));
    }

    @Test
    void getByJenre() {
        Jenre jenre = new Jenre().setId(JENRE_ID);

        List<Book> actualBooks = bookRepository.getByJenre(jenre);

        Book expectedBook = entityManager.find(Book.class, actualBooks.get(0).getId());
        assertThat(expectedBook).isEqualTo(actualBooks.get(0));
    }

    @Test
    void getAll() {
        List<Book> actualBooks = bookRepository.getAll();

        Book expectedBook = entityManager.find(Book.class, actualBooks.get(0).getId());

        assertThat(expectedBook).isEqualTo(actualBooks.get(0));
    }

    @Test
    void create() {
        String title = "title";
        Author author = new Author()
                .setId(AUTHOR_ID);
        Jenre jenre = new Jenre()
                .setId(JENRE_ID)
                .setType("horror");
        Book book = new Book()
                .setId(1L)
                .setTitle(title)
                .setAuthor(author)
                .setJenre(jenre);

        Book actualBook = bookRepository.create(book);

        Book expectedBook = entityManager.find(Book.class, actualBook.getId());

        assertThat(expectedBook).isEqualTo(actualBook);
    }

    @Test
    void update() {
        Book oldBook = entityManager.find(Book.class, BOOK_ID);
        Jenre oldJenre = oldBook.getJenre();

        Jenre newJenre = new Jenre().setId(2).setType("drama");
        oldBook.setJenre(newJenre);

        bookRepository.update(oldBook);

        Book newBook = entityManager.find(Book.class, oldBook.getId());

        assertThat(newBook.getJenre()).isEqualTo(newJenre);
        assertThat(newBook.getJenre()).isNotEqualTo(oldJenre);
    }
}