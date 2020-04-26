package ru.otus.spring.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.otus.spring.domain.Jenre;

import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "jenre", path = "jenre")
public interface JenreRepository extends MongoRepository<Jenre, Long> {

    Optional<Jenre> findById(String id);

    void deleteById(String id);
}
