package ru.otus.spring.domain.jenre;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface JenreRepository extends CrudRepository<Jenre, Long> {

    Optional<Jenre> findById(String aLong);
}
