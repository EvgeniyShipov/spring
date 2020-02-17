package ru.otus.spring.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.repository.AuthorRepository;
import ru.otus.spring.repository.BookRepository;
import ru.otus.spring.repository.CommentRepository;
import ru.otus.spring.repository.JenreRepository;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CommentController.class)
class CommentControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private BookRepository bookRepository;
    @MockBean
    private JenreRepository jenreRepository;
    @MockBean
    private CommentRepository commentRepository;
    @MockBean
    private AuthorRepository authorRepository;

    @Test
    void getAllComments() throws Exception {
        Book book = new Book().setId("1").setTitle("title");
        Comment comment = new Comment().setId("1").setMessage("message").setBook(book);
        when(commentRepository.findAll()).thenReturn(Collections.singletonList(comment));

        mvc.perform(get("/comments"))
                .andExpect(status().isOk());

        verify(commentRepository).findAll();
    }

    @Test
    void getComments() throws Exception {
        Comment comment = new Comment().setId("1").setMessage("message");
        when(commentRepository.findById(comment.getId())).thenReturn(Optional.of(comment));

        mvc.perform(get("/comments/" + comment.getId()))
                .andExpect(status().isOk());

        verify(commentRepository).findById(comment.getId());
    }

    @Test
    void createComment() throws Exception {
        String bookId = "1";
        Comment comment = new Comment().setId("1").setMessage("message");
        when(commentRepository.save(comment)).thenReturn(comment);

        mvc.perform(post("/comments")
                .param("message", comment.getMessage())
                .param("book", bookId))
                .andExpect(status().isOk());

        verify(commentRepository).save(comment);
    }

    @Test
    void updateComment() throws Exception {
        Comment comment = new Comment().setId("1").setMessage("message");
        when(commentRepository.save(comment)).thenReturn(comment);

        mvc.perform(patch("/comments/" + comment.getId()))
                .andExpect(status().isOk());

        verify(commentRepository).save(comment);
    }

    @Test
    void deleteComment() throws Exception {
        Comment comment = new Comment().setId("1").setMessage("message");

        mvc.perform(delete("/comments/" + comment.getId()))
                .andExpect(status().isOk());

        verify(commentRepository).deleteById(Long.valueOf(comment.getId()));
    }
}