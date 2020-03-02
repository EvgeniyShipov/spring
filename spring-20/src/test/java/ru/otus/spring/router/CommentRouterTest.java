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
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.repository.AuthorRepository;
import ru.otus.spring.repository.BookRepository;
import ru.otus.spring.repository.CommentRepository;
import ru.otus.spring.repository.JenreRepository;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class CommentRouterTest {

    @MockBean
    private BookRepository bookRepository;
    @MockBean
    private JenreRepository jenreRepository;
    @MockBean
    private CommentRepository commentRepository;
    @MockBean
    private AuthorRepository authorRepository;
    @Autowired
    private RouterFunction commentRoute;
    private WebTestClient client;

    @BeforeEach
    void setUp() {
        client = WebTestClient
                .bindToRouterFunction(commentRoute)
                .build();
    }

    @Test
    void getAllComments() {
        Book book = new Book().setId("1").setTitle("title");
        Comment comment = new Comment().setId("1").setMessage("message").setBook(book);
        List<Comment> comments = Arrays.asList(comment);
        Flux<Comment> commentFlux = Flux.fromIterable(comments);
        when(commentRepository.findAll()).thenReturn(commentFlux);

        client.get()
                .uri("/comments")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(Comment.class)
                .isEqualTo(comments);

        verify(commentRepository).findAll();
    }

    @Test
    void getComments() {
        Comment comment = new Comment().setId("1").setMessage("message");
        when(commentRepository.findById(comment.getId())).thenReturn(Mono.just(comment));

        client.get()
                .uri("/comments/" + comment.getId())
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(Comment.class)
                .isEqualTo(comment);

        verify(commentRepository).findById(comment.getId());
    }

    @Test
    void createComment() {
        Comment comment = new Comment().setId("1").setMessage("message");
        when(commentRepository.save(comment)).thenReturn(Mono.just(comment));

        client.post()
                .uri("/comments")
                .bodyValue(comment)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(Comment.class)
                .isEqualTo(comment);

        verify(commentRepository).save(comment);
    }

    @Test
    void updateComment() {
        Comment comment = new Comment().setId("1").setMessage("message");
        when(commentRepository.save(comment)).thenReturn(Mono.just(comment));

        client.put()
                .uri("/comments/" + comment.getId())
                .bodyValue(comment)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(Comment.class)
                .isEqualTo(comment);

        verify(commentRepository).save(comment);
    }

    @Test
    void deleteComment() {
        Comment comment = new Comment().setId("1").setMessage("message");
        when(commentRepository.deleteById(comment.getId())).thenReturn(Mono.just(comment));

        client.delete()
                .uri("/comments/" + comment.getId())
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(Comment.class)
                .isEqualTo(comment);

        verify(commentRepository).deleteById(comment.getId());
    }
}