package ru.otus.spring.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.spring.domain.Comment;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(CommentRepositoryJpa.class)
class CommentRepositoryJpaTest {
    private static final int BOOK_ID = 1;
    private static final int COMMENT_ID = 1;

    @Autowired
    private CommentRepository commentRepository;

    @Test
    void getById() {
        Comment comment = commentRepository.getById(COMMENT_ID);

        assertThat(comment).isNotNull();
        assertThat(comment.getId()).isEqualTo(COMMENT_ID);
    }

    @Test
    void getByBookId() {
        List<Comment> comments = commentRepository.getByBookId(BOOK_ID);

        assertThat(comments).isNotNull();
        assertThat(comments.get(0).getId()).isEqualTo(COMMENT_ID);
    }

    @Test
    void getAll() {
        List<Comment> comments = commentRepository.getAll();

        assertThat(comments).isNotNull();
        assertThat(comments.get(0).getId()).isEqualTo(COMMENT_ID);
    }

    @Test
    void delete() {
        Comment comment = commentRepository.getById(COMMENT_ID);

        commentRepository.delete(comment);

        Comment result = commentRepository.getById(COMMENT_ID);

        assertThat(result).isNull();
    }

    @Test
    void create() {
        Comment comment = commentRepository.create(new Comment().setMessage("test message"));

        Comment resultComment = commentRepository.getById(comment.getId());

        assertThat(resultComment).isEqualTo(comment);
    }
}