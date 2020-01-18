package ru.otus.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.domain.Jenre;

public interface JenreRepository extends JpaRepository<Jenre, Long> {

}
