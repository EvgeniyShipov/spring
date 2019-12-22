package ru.otus.spring.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Jenre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;
import static ru.otus.spring.domain.Jenre.*;

@JdbcTest
@Import(BookDaoJdbc.class)
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
class BookDaoJdbcTest {
    private static final String BOOK_TITLE = "The Winter of Our Discontent";
    private static final int AUTHOR_ID = 1;
    private static final int JENRE_ID = TRAGEDY.ordinal();
    private static final int BOOK_ID = 1;

    @Autowired
    private BookDaoJdbc bookDaoJdbc;

    @Test
    void getById() {
        Book book = bookDaoJdbc.getById(BOOK_ID);

        assertThat(book).isNotNull();
        assertThat(book.getTitle()).isEqualTo(BOOK_TITLE);
        assertThat(book.getIdAuthor()).isEqualTo(AUTHOR_ID);
        assertThat(book.getIdJenre()).isEqualTo(JENRE_ID);
    }

    @Test
    void getByAuthor() {
        List<Book> books = bookDaoJdbc.getByAuthor(AUTHOR_ID);

        assertThat(books).isNotNull();
        assertThat(books.get(0).getTitle()).isEqualTo(BOOK_TITLE);
        assertThat(books.get(0).getIdAuthor()).isEqualTo(AUTHOR_ID);
        assertThat(books.get(0).getIdJenre()).isEqualTo(JENRE_ID);
    }

    @Test
    void getAll() {
        List<Book> books = bookDaoJdbc.getAll();

        assertThat(books).isNotNull();
        assertThat(books.get(0).getTitle()).isEqualTo(BOOK_TITLE);
        assertThat(books.get(0).getIdAuthor()).isEqualTo(AUTHOR_ID);
        assertThat(books.get(0).getIdJenre()).isEqualTo(JENRE_ID);
    }

    @Test
    void create() {
        String title = "title";
        int idAuthor = 1;
        int jenreId = HORROR.ordinal();
        Book book = new Book()
                .setTitle(title)
                .setIdAuthor(idAuthor)
                .setIdJenre(jenreId);

        bookDaoJdbc.create(book);

        List<Book> books = bookDaoJdbc.getAll();

        assertThat(books.get(1).getTitle()).isEqualTo(title);
        assertThat(books.get(1).getIdAuthor()).isEqualTo(idAuthor);
        assertThat(books.get(1).getIdJenre()).isEqualTo(jenreId);
    }

    @Test
    void deleteById() {
        bookDaoJdbc.deleteById(BOOK_ID);
        List<Book> books = bookDaoJdbc.getAll();

        assertThat(books).isEmpty();
    }

    @Test
    void update() {
        int changedJenre = DRAMA.ordinal();

        Book book = bookDaoJdbc.getById(BOOK_ID);
        book.setIdJenre(changedJenre);

        bookDaoJdbc.update(book);

        Book resultBook = bookDaoJdbc.getById(BOOK_ID);

        assertThat(resultBook.getIdJenre()).isEqualTo(changedJenre);
    }
}