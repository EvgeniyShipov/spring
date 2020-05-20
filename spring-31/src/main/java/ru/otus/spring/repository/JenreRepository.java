package ru.otus.spring.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.spring.domain.Jenre;

import java.util.Optional;

public interface JenreRepository extends CrudRepository<Jenre, Long> {

    Optional<Jenre> findById(String id);
}
