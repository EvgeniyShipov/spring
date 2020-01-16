package ru.otus.spring.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.spring.domain.Comment;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CommentRepositoryJpaTest {
    private static final long BOOK_ID = 1;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void getByBookId() {
        List<Comment> actualComments = commentRepository.findByBookId(BOOK_ID);
        Comment expectedComment = entityManager.find(Comment.class, actualComments.get(0).getId());

        assertThat(actualComments).isNotNull();
        assertThat(expectedComment).isEqualTo(actualComments.get(0));
    }
}