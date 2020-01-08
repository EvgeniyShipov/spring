package ru.otus.spring.repository;

import ru.otus.spring.domain.Comment;

import java.util.List;

public interface CommentRepository {

    Comment getById(long id);

    List<Comment> getByBookId(long idBook);

    List<Comment> getAll();

    void delete(Comment comment);

    Comment create(Comment comment);
}
