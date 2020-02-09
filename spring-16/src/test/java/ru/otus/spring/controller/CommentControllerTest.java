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
import ru.otus.spring.service.LibraryService;

import java.util.Collections;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CommentController.class)
class CommentControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private LibraryService service;
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
        when(service.getAllComments()).thenReturn(Collections.singletonList(comment));

        this.mvc.perform(get("/comments"))
                .andExpect(status().isOk());

        verify(service).getAllComments();
    }

    @Test
    void getComments() throws Exception {
        Comment comment = new Comment().setId("1").setMessage("message");
        when(service.getComment(comment.getId())).thenReturn(comment);

        this.mvc.perform(get("/comments/" + comment.getId()))
                .andExpect(status().isOk());

        verify(service).getComment(comment.getId());
    }

    @Test
    void createComment() throws Exception {
        String bookId = "1";
        Comment comment = new Comment().setId("1").setMessage("message");
        when(service.createComment(comment.getMessage(), bookId)).thenReturn(comment);

        this.mvc.perform(post("/comments/create")
                .param("message", comment.getMessage())
                .param("book", bookId))
                .andExpect(status().isOk());

        verify(service).createComment(comment.getMessage(), bookId);
    }

    @Test
    void deleteComment() throws Exception {
        Comment comment = new Comment().setId("1").setMessage("message");
        when(service.deleteComment(comment.getId())).thenReturn(comment);

        this.mvc.perform(get("/comments/delete/" + comment.getId()))
                .andExpect(status().isOk());

        verify(service).deleteComment(comment.getId());
    }
}