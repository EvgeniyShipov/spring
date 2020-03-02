package ru.otus.spring.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import ru.otus.spring.domain.Jenre;
import ru.otus.spring.repository.BookRepository;
import ru.otus.spring.repository.JenreRepository;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.badRequest;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Log
@Component
@RequiredArgsConstructor
public class JenreHandler {

    private final JenreRepository repository;
    private final BookRepository books;

    public Mono<ServerResponse> getAllJenre(ServerRequest request) {
        return ok().contentType(APPLICATION_JSON)
                .body(repository.findAll(), Jenre.class);
    }

    public Mono<ServerResponse> getJenre(ServerRequest request) {
        return ok().contentType(APPLICATION_JSON)
                .body(repository.findById(request.pathVariable("id")), Jenre.class);
    }

    public Mono<ServerResponse> createJenre(ServerRequest request) {
        return ok().contentType(APPLICATION_JSON)
                .body(request.bodyToMono(Jenre.class)
                        .flatMap(repository::save), Jenre.class);
    }

    public Mono<ServerResponse> updateJenre(ServerRequest request) {
        return ok().contentType(APPLICATION_JSON)
                .body(request.bodyToMono(Jenre.class)
                        .flatMap(repository::save), Jenre.class);
    }

    public Mono<ServerResponse> deleteJenre(ServerRequest request) {
        String id = request.pathVariable("id");

        return ok().contentType(APPLICATION_JSON)
                .body(books.existsByJenreId(id)
                        .filter(isExists -> !isExists)
                        .flatMap(isExists -> repository.deleteById(id)), Jenre.class)
                .switchIfEmpty(badRequest().build());
    }
}
