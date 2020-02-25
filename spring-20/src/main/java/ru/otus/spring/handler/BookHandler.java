package ru.otus.spring.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import ru.otus.spring.domain.Book;
import ru.otus.spring.repository.BookRepository;
import ru.otus.spring.repository.CommentRepository;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.*;

@Log
@Component
@RequiredArgsConstructor
public class BookHandler {

    private final BookRepository repository;
    private final CommentRepository comments;

    public Mono<ServerResponse> getAllBooks(ServerRequest request) {
        return ok().contentType(APPLICATION_JSON)
                .body(repository.findAll(), Book.class);
    }

    public Mono<ServerResponse> getBook(ServerRequest request) {
        return repository.findById(request.pathVariable("id"))
                .flatMap(comment -> ok().contentType(APPLICATION_JSON)
                        .body(comment, Book.class))
                .switchIfEmpty(notFound().build());
    }

    public Mono<ServerResponse> createBook(ServerRequest request) {
        return ok().contentType(APPLICATION_JSON)
                .body(request.bodyToMono(Book.class)
                        .flatMap(repository::save), Book.class);
    }

    public Mono<ServerResponse> updateBook(ServerRequest request) {
        return ok().contentType(APPLICATION_JSON)
                .body(request.bodyToMono(Book.class)
                        .flatMap(repository::save), Book.class);
    }

    public Mono<ServerResponse> deleteBook(ServerRequest request) {
        String id = request.pathVariable("id");

        return ok().contentType(APPLICATION_JSON)
                .body(comments.deleteAllByBookId(id)
                        .flatMap(result -> repository.deleteById(id)), Void.class)
                .switchIfEmpty(badRequest().build());
    }
}
