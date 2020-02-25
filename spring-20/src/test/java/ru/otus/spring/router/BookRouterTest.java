package ru.otus.spring.router;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Jenre;
import ru.otus.spring.repository.AuthorRepository;
import ru.otus.spring.repository.BookRepository;
import ru.otus.spring.repository.CommentRepository;
import ru.otus.spring.repository.JenreRepository;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class BookRouterTest {

    @MockBean
    private BookRepository bookRepository;
    @MockBean
    private JenreRepository jenreRepository;
    @MockBean
    private CommentRepository commentRepository;
    @MockBean
    private AuthorRepository authorRepository;
    @Autowired
    private RouterFunction bookRoute;

    private WebTestClient client;

    @BeforeEach
    void setUp() {
        client = WebTestClient
                .bindToRouterFunction(bookRoute)
                .build();
    }

    @Test
    void getAllBooks() {
        Author author = new Author().setId("1").setName("name").setSurname("surname").setPatronymic("patronymic");
        Jenre jenre = new Jenre().setId("1").setType("jenre");
        Book book = new Book().setId("1").setTitle("title").setAuthor(author).setJenre(jenre);
        when(bookRepository.findAll()).thenReturn(Flux.just(book));

        client.get()
                .uri("/books")
                .exchange()
                .expectStatus()
                .isOk();

        verify(bookRepository).findAll();
    }

    @Test
    void getBook() {
        Book book = new Book().setId("1").setTitle("title");
        when(bookRepository.findById(book.getId())).thenReturn(Mono.just(book));

        client.get()
                .uri("/books/" + book.getId())
                .exchange()
                .expectStatus()
                .isOk();

        verify(bookRepository).findById(book.getId());
    }

    @Test
    void createBook() {
        Jenre jenre = new Jenre().setId("1");
        Author author = new Author().setId("1").setName("name");
        Book book = new Book().setId("1").setTitle("title").setAuthor(author).setJenre(jenre);
        when(bookRepository.save(book)).thenReturn(Mono.just(book));

        client.post()
                .uri("/books")
                .bodyValue(book)
                .exchange()
                .expectStatus()
                .isOk();

        verify(bookRepository).save(book);
    }

    @Test
    void updateBook() {
        Jenre jenre = new Jenre().setId("1");
        Author author = new Author().setId("1").setName("name");
        Book book = new Book().setId("1").setTitle("title").setAuthor(author).setJenre(jenre);
        when(bookRepository.save(book)).thenReturn(Mono.just(book));

        client.put()
                .uri("/books/" + author.getId())
                .bodyValue(book)
                .exchange()
                .expectStatus()
                .isOk();

        verify(bookRepository).save(book);
    }

    @Test
    void deleteBook() {
        Author author = new Author().setId("1").setName("name");
        Book book = new Book().setId("1").setTitle("title").setAuthor(author);

        client.delete()
                .uri("/books/" + book.getId())
                .exchange()
                .expectStatus()
                .isOk();

        verify(bookRepository).deleteById(book.getId());
    }
}