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
import ru.otus.spring.repository.AuthorRepository;
import ru.otus.spring.repository.BookRepository;
import ru.otus.spring.repository.CommentRepository;
import ru.otus.spring.repository.JenreRepository;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class AuthorRouterTest {

    @MockBean
    private BookRepository bookRepository;
    @MockBean
    private JenreRepository jenreRepository;
    @MockBean
    private CommentRepository commentRepository;
    @MockBean
    private AuthorRepository authorRepository;
    @Autowired
    private RouterFunction authorRoute;

    private WebTestClient client;

    @BeforeEach
    void setUp() {
        client = WebTestClient
                .bindToRouterFunction(authorRoute)
                .build();
    }

    @Test
    void getAllAuthors() {
        Author author = new Author().setId("1").setName("name");
        List<Author> authors = Arrays.asList(author);
        Flux<Author> authorFlux = Flux.fromIterable(authors);
        when(authorRepository.findAll()).thenReturn(authorFlux);

        client.get()
                .uri("/authors")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(Author.class)
                .isEqualTo(authors);

        verify(authorRepository).findAll();
    }


    @Test
    void getAuthor() {
        Author author = new Author().setId("1").setName("name");
        when(authorRepository.findById(author.getId())).thenReturn(Mono.just(author));

        client.get()
                .uri("/authors/" + author.getId())
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(Author.class)
                .isEqualTo(author);

        verify(authorRepository).findById(author.getId());
    }

    @Test
    void createAuthor() {
        Author author = new Author().setId("1").setName("name").setSurname("surname").setPatronymic("patronymic");
        when(authorRepository.save(author)).thenReturn(Mono.just(author));

        client.post()
                .uri("/authors")
                .bodyValue(author)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(Author.class)
                .isEqualTo(author);

        verify(authorRepository).save(author);
    }

    @Test
    void updateAuthor() {
        Author author = new Author().setId("1").setName("name").setSurname("surname").setPatronymic("patronymic");
        when(authorRepository.save(author)).thenReturn(Mono.just(author));

        client.put()
                .uri("/authors/" + author.getId())
                .bodyValue(author)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(Author.class)
                .isEqualTo(author);

        verify(authorRepository).save(author);
    }

    @Test
    void deleteAuthor() {
        Author author = new Author().setId("1").setName("name");
        when(bookRepository.existsByAuthorId(author.getId())).thenReturn(Mono.just(false));
        when(authorRepository.deleteById(author.getId())).thenReturn(Mono.just(author));

        client.delete()
                .uri("/authors/" + author.getId())
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(Author.class)
                .isEqualTo(author);

        verify(authorRepository).deleteById(author.getId());
    }
}