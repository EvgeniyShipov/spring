package ru.otus.spring.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.domain.Jenre;

import java.util.Optional;

public interface JenreRepository extends MongoRepository<Jenre, Long> {

    Optional<Jenre> findById(String id);
}
