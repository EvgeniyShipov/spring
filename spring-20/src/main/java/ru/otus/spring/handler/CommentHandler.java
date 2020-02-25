package ru.otus.spring.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.repository.CommentRepository;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.notFound;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Log
@Component
@RequiredArgsConstructor
public class CommentHandler {

    private final CommentRepository repository;

    public Mono<ServerResponse> getAllComments(ServerRequest request) {
        return ok().contentType(APPLICATION_JSON)
                .body(repository.findAll(), Comment.class);
    }

    public Mono<ServerResponse> getComment(ServerRequest request) {
        return repository.findById(request.pathVariable("id"))
                .flatMap(comment -> ok().contentType(APPLICATION_JSON)
                        .body(comment, Comment.class))
                .switchIfEmpty(notFound().build());
    }

    public Mono<ServerResponse> createComment(ServerRequest request) {
        return ok().contentType(APPLICATION_JSON)
                .body(request.bodyToMono(Comment.class)
                        .flatMap(repository::save), Comment.class);
    }

    public Mono<ServerResponse> updateComment(ServerRequest request) {
        return ok().contentType(APPLICATION_JSON)
                .body(request.bodyToMono(Comment.class)
                        .flatMap(repository::save), Comment.class);
    }

    public Mono<ServerResponse> deleteComment(ServerRequest request) {
        String id = request.pathVariable("id");
        return ok().contentType(APPLICATION_JSON)
                .body(repository.deleteById(id), Void.class);
    }
}
