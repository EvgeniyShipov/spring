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
import ru.otus.spring.domain.Jenre;
import ru.otus.spring.repository.AuthorRepository;
import ru.otus.spring.repository.BookRepository;
import ru.otus.spring.repository.CommentRepository;
import ru.otus.spring.repository.JenreRepository;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class JenreRouterTest {

    @MockBean
    private BookRepository bookRepository;
    @MockBean
    private JenreRepository jenreRepository;
    @MockBean
    private CommentRepository commentRepository;
    @MockBean
    private AuthorRepository authorRepository;
    @Autowired
    private RouterFunction jenreRoute;
    private WebTestClient client;

    @BeforeEach
    void setUp() {
        client = WebTestClient
                .bindToRouterFunction(jenreRoute)
                .build();
    }

    @Test
    void getAllJenre() {
        Jenre jenre = new Jenre().setId("1").setType("jenre");
        List<Jenre> jenres = Arrays.asList(jenre);
        Flux<Jenre> jenreFlux = Flux.fromIterable(jenres);

        when(jenreRepository.findAll()).thenReturn(jenreFlux);

        client.get()
                .uri("/jenres")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(Jenre.class)
                .isEqualTo(jenres);

        verify(jenreRepository).findAll();
    }

    @Test
    void getJenre() {
        Jenre jenre = new Jenre().setId("1").setType("jenre");
        when(jenreRepository.findById(jenre.getId())).thenReturn(Mono.just(jenre));

        client.get()
                .uri("/jenres/" + jenre.getId())
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(Jenre.class)
                .isEqualTo(jenre);

        verify(jenreRepository).findById(jenre.getId());
    }

    @Test
    void createJenre() {
        Jenre jenre = new Jenre().setId("1").setType("jenre");
        when(jenreRepository.save(jenre)).thenReturn(Mono.just(jenre));

        client.post()
                .uri("/jenres")
                .bodyValue(jenre)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(Jenre.class)
                .isEqualTo(jenre);

        verify(jenreRepository).save(jenre);
    }

    @Test
    void updateJenre() {
        Jenre jenre = new Jenre().setId("1").setType("jenre");
        when(jenreRepository.save(jenre)).thenReturn(Mono.just(jenre));

        client.put()
                .uri("/jenres/" + jenre.getId())
                .bodyValue(jenre)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(Jenre.class)
                .isEqualTo(jenre);

        verify(jenreRepository).save(jenre);
    }

    @Test
    void deleteJenre() {
        Jenre jenre = new Jenre().setId("1").setType("jenre");
        when(bookRepository.existsByJenreId(jenre.getId())).thenReturn(Mono.just(false));
        when(jenreRepository.deleteById(jenre.getId())).thenReturn(Mono.just(jenre));

        client.delete()
                .uri("/jenres/" + jenre.getId())
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(Jenre.class)
                .isEqualTo(jenre);

        verify(jenreRepository).deleteById(jenre.getId());
    }
}