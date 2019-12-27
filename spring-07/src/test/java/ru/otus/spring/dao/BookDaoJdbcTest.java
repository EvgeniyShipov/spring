package ru.otus.spring.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Jenre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;
import static ru.otus.spring.domain.Jenre.*;

@JdbcTest
@Import(BookDaoJdbc.class)
class BookDaoJdbcTest {
    private static final String BOOK_TITLE = "The Winter of Our Discontent";
    private static final int AUTHOR_ID = 1;
    private static final int JENRE_ID = 1;
    private static final int BOOK_ID = 1;

    @Autowired
    private BookDaoJdbc bookDaoJdbc;

    @Test
    void getById() {
        Book book = bookDaoJdbc.getById(BOOK_ID);

        assertThat(book).isNotNull();
        assertThat(book.getTitle()).isEqualTo(BOOK_TITLE);
        assertThat(book.getAuthor().getId()).isEqualTo(AUTHOR_ID);
        assertThat(book.getJenre().getId()).isEqualTo(JENRE_ID);
    }

    @Test
    void getByAuthor() {
        Author author = new Author().setId(AUTHOR_ID);
        List<Book> books = bookDaoJdbc.getByAuthor(author);

        assertThat(books).isNotNull();
        assertThat(books.get(0).getTitle()).isEqualTo(BOOK_TITLE);
        assertThat(books.get(0).getAuthor().getId()).isEqualTo(AUTHOR_ID);
        assertThat(books.get(0).getJenre().getId()).isEqualTo(JENRE_ID);
    }

    @Test
    void getAll() {
        List<Book> books = bookDaoJdbc.getAll();

        assertThat(books).isNotNull();
        assertThat(books.get(0).getTitle()).isEqualTo(BOOK_TITLE);
        assertThat(books.get(0).getAuthor().getId()).isEqualTo(AUTHOR_ID);
        assertThat(books.get(0).getJenre().getId()).isEqualTo(JENRE_ID);
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
                .setTitle(title)
                .setAuthor(author)
                .setJenre(jenre);

        bookDaoJdbc.create(book);

        List<Book> books = bookDaoJdbc.getAll();

        assertThat(books.get(1).getTitle()).isEqualTo(title);
        assertThat(books.get(1).getAuthor().getId()).isEqualTo(author.getId());
        assertThat(books.get(1).getJenre().getId()).isEqualTo(jenre.getId());
    }

    @Test
    void deleteById() {
        bookDaoJdbc.deleteById(BOOK_ID);
        List<Book> books = bookDaoJdbc.getAll();

        assertThat(books).isEmpty();
    }

    @Test
    void update() {
        Jenre jenre = new Jenre().setId(2).setType("drama");

        Book book = bookDaoJdbc.getById(BOOK_ID);
        book.setJenre(jenre);

        bookDaoJdbc.update(book);

        Book resultBook = bookDaoJdbc.getById(BOOK_ID);

        assertThat(resultBook.getJenre().getId()).isEqualTo(jenre.getId());
    }
}