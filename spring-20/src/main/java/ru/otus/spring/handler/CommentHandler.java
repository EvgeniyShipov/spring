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
        return ok().contentType(APPLICATION_JSON)
                .body(repository.findById(request.pathVariable("id")), Comment.class);
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
        return ok().contentType(APPLICATION_JSON)
                .body(repository.deleteById(request.pathVariable("id")), Comment.class);
    }
}
