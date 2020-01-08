package ru.otus.spring.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Jenre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(BookRepositoryJpa.class)
class BookRepositoryJpaTest {
    private static final String BOOK_TITLE = "The Winter of Our Discontent";
    private static final int AUTHOR_ID = 1;
    private static final int JENRE_ID = 1;
    private static final int BOOK_ID = 1;

    @Autowired
    private BookRepository bookRepository;

    @Test
    void getById() {
        Book book = bookRepository.getById(BOOK_ID);

        assertThat(book).isNotNull();
        assertThat(book.getTitle()).isEqualTo(BOOK_TITLE);
        assertThat(book.getAuthor().getId()).isEqualTo(AUTHOR_ID);
        assertThat(book.getJenre().getId()).isEqualTo(JENRE_ID);
    }

    @Test
    void getByAuthor() {
        Author author = new Author().setId(AUTHOR_ID);
        List<Book> books = bookRepository.getByAuthor(author);

        assertThat(books).isNotNull();
        assertThat(books.get(0).getTitle()).isEqualTo(BOOK_TITLE);
        assertThat(books.get(0).getAuthor().getId()).isEqualTo(AUTHOR_ID);
        assertThat(books.get(0).getJenre().getId()).isEqualTo(JENRE_ID);
    }

    @Test
    void getAll() {
        List<Book> books = bookRepository.getAll();

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

        Book result = bookRepository.create(book);

        assertThat(result.getTitle()).isEqualTo(title);
        assertThat(result.getAuthor().getId()).isEqualTo(author.getId());
        assertThat(result.getJenre().getId()).isEqualTo(jenre.getId());
    }

    @Test
    void update() {
        Jenre jenre = new Jenre().setId(2).setType("drama");

        Book book = bookRepository.getById(BOOK_ID);
        book.setJenre(jenre);

        bookRepository.update(book);

        Book resultBook = bookRepository.getById(BOOK_ID);

        assertThat(resultBook.getJenre().getId()).isEqualTo(jenre.getId());
    }
}