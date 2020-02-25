package ru.otus.spring.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import ru.otus.spring.domain.Author;
import ru.otus.spring.repository.AuthorRepository;
import ru.otus.spring.repository.BookRepository;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.*;

@Log
@Component
@RequiredArgsConstructor
public class AuthorHandler {

    private final AuthorRepository repository;
    private final BookRepository books;

    public Mono<ServerResponse> getAllAuthors(ServerRequest request) {
        return ok().contentType(APPLICATION_JSON)
                .body(repository.findAll(), Author.class);
    }

    public Mono<ServerResponse> getAuthor(ServerRequest request) {
        return repository.findById(request.pathVariable("id"))
                .flatMap(author -> ok().contentType(APPLICATION_JSON)
                        .body(author, Author.class))
                .switchIfEmpty(notFound().build());
    }

    public Mono<ServerResponse> createAuthor(ServerRequest request) {
        return ok().contentType(APPLICATION_JSON)
                .body(request.bodyToMono(Author.class)
                        .flatMap(repository::save), Author.class);
    }

    public Mono<ServerResponse> updateAuthor(ServerRequest request) {
        String id = request.pathVariable("id");
        return ok().contentType(APPLICATION_JSON)
                .body(request.bodyToMono(Author.class)
                        .flatMap(repository::save), Author.class);
    }

    public Mono<ServerResponse> deleteAuthor(ServerRequest request) {
        String id = request.pathVariable("id");
        return ok().contentType(APPLICATION_JSON)
                .body(books.existsByAuthorId(id)
                        .filter(isExists -> !isExists)
                        .flatMap(isExists -> repository.deleteById(id)), Void.class)
                .switchIfEmpty(badRequest().build());
    }
}
