package ru.otus.spring.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;
import ru.otus.spring.domain.Book;

public interface BookRepository extends ReactiveMongoRepository<Book, Long> {

    Mono<Book> findById(String id);

    Mono<Boolean> existsByJenreId(String aLong);

    Mono<Boolean> existsByAuthorId(String aLong);

    Mono<Void> deleteById(String id);
}
