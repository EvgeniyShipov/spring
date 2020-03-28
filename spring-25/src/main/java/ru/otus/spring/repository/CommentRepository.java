package ru.otus.spring.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.spring.domain.Comment;

public interface CommentRepository extends CrudRepository<Comment, Long> {

}
