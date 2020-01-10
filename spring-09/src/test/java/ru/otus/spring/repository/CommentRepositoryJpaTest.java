package ru.otus.spring.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.spring.domain.Comment;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(CommentRepositoryJpa.class)
class CommentRepositoryJpaTest {
    private static final long BOOK_ID = 1;
    private static final long COMMENT_ID = 1;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void getById() {
        Comment actualComment = commentRepository.getById(COMMENT_ID);
        Comment expectedComment = entityManager.find(Comment.class, actualComment.getId());

        assertThat(expectedComment).isEqualTo(actualComment);
    }

    @Test
    void getByBookId() {
        List<Comment> actualComments = commentRepository.getByBookId(BOOK_ID);
        Comment expectedComment = entityManager.find(Comment.class, actualComments.get(0).getId());

        assertThat(actualComments).isNotNull();
        assertThat(expectedComment).isEqualTo(actualComments.get(0));
    }

    @Test
    void getAll() {
        List<Comment> actualComments = commentRepository.getAll();
        Comment expectedComment = entityManager.find(Comment.class, actualComments.get(0).getId());

        assertThat(actualComments).isNotNull();
        assertThat(expectedComment).isEqualTo(actualComments.get(0));
    }

    @Test
    void delete() {
        Comment comment = entityManager.find(Comment.class, COMMENT_ID);
        commentRepository.delete(comment);
        Comment expectedComment = entityManager.find(Comment.class, comment.getId());

        assertThat(expectedComment).isNull();
    }

    @Test
    void create() {
        Comment actualComment = commentRepository.create(new Comment().setMessage("test message"));
        Comment expectedComment = entityManager.find(Comment.class, actualComment.getId());

        assertThat(expectedComment).isEqualTo(actualComment);
    }
}