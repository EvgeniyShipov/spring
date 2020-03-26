package ru.otus.spring.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.domain.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends MongoRepository<Comment, Long> {

    List<Comment> findByBookId(String idBook);

    Optional<Comment> findById(String id);

    void deleteAllByBookId(String idBook);
}
