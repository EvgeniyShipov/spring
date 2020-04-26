package ru.otus.spring.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.otus.spring.domain.Comment;

import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "comments", path = "comments")
public interface CommentRepository extends MongoRepository<Comment, Long> {

    Optional<Comment> findById(String id);

    void deleteAllByBookId(String idBook);

    void deleteById(String id);
}
