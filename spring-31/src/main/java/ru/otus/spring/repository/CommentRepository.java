package ru.otus.spring.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.spring.domain.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends CrudRepository<Comment, Long> {

    List<Comment> findByBookId(Long idBook);

    Optional<Comment> findById(Long id);

    void deleteAllByBookId(Long idBook);
}
