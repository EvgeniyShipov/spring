package ru.otus.spring.domain.comment;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends CrudRepository<Comment, Long> {

    List<Comment> findByBookId(String idBook);

    Optional<Comment> findById(String id);

    void deleteAllByBookId(String idBook);
}
