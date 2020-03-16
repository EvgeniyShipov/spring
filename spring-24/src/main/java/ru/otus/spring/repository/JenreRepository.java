package ru.otus.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.domain.Jenre;

import java.util.Optional;

public interface JenreRepository extends JpaRepository<Jenre, Long> {

    Optional<Jenre> findById(long id);
}
