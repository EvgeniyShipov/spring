package ru.otus.spring.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;
import ru.otus.spring.domain.Jenre;

public interface JenreRepository extends ReactiveMongoRepository<Jenre, Long> {

    Mono<Jenre> findById(String id);

    Mono<Void> deleteById(String id);
}
