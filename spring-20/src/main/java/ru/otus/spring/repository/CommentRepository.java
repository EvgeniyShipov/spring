package ru.otus.spring.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring.domain.Comment;

public interface CommentRepository extends ReactiveMongoRepository<Comment, Long> {

    Mono<Comment> findById(String id);

    Flux<Comment> deleteAllByBookId(String idBook);

    Mono<Comment> deleteById(String id);
}
