package ru.otus.spring.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.repository.*;
import ru.otus.spring.service.LibraryService;
import ru.otus.spring.service.LibraryUserDetailsService;

import java.util.Collections;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.ResultMatcher.matchAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WithMockUser(username = "user", authorities = {"ROLE_USER"})
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
    @MockBean
    private LibraryUserDetailsService libraryUserDetailsService;
    @MockBean
    private UserRepository userRepository;

    @Test
    void getAllComments() throws Exception {
        Book book = new Book().setId("1").setTitle("title");
        Comment comment = new Comment().setId("1").setMessage("message").setBook(book);
        when(service.getAllComments()).thenReturn(Collections.singletonList(comment));

        this.mvc.perform(get("/comments"))
                .andExpect(matchAll(
                        status().isOk(),
                        model().size(1),
                        model().attributeExists("comments"),
                        view().name("comments")));

        verify(service).getAllComments();
    }

    @Test
    void getComments() throws Exception {
        Comment comment = new Comment().setId("1").setMessage("message");
        when(service.getComment(comment.getId())).thenReturn(comment);

        this.mvc.perform(get("/comments/" + comment.getId()))
                .andExpect(matchAll(
                        status().isOk(),
                        model().size(1),
                        model().attributeExists("comment"),
                        view().name("comment")));

        verify(service).getComment(comment.getId());
    }

    @Test
    void createComment() throws Exception {
        Comment comment = new Comment().setId("1").setMessage("message");

        this.mvc.perform(get("/comments/create")
                .param("message", comment.getMessage()))
                .andExpect(matchAll(
                        status().isOk(),
                        model().size(2),
                        model().attributeExists("books"),
                        model().attributeExists("comment"),
                        view().name("comment_new")));

        verify(service).getAllBooks();
    }

    @Test
    void createComment2() throws Exception {
        String bookId = "1";
        Comment comment = new Comment().setId("1").setMessage("message");
        when(service.createComment(comment.getMessage(), bookId)).thenReturn(comment);

        this.mvc.perform(post("/comments/create")
                .param("message", comment.getMessage())
                .param("book", bookId))
                .andExpect(redirectedUrl("/comments"));

        verify(service).createComment(comment.getMessage(), bookId);
    }

    @Test
    void updateComment() throws Exception {
        Comment comment = new Comment().setId("1").setMessage("message");
        when(service.getComment(comment.getId())).thenReturn(comment);

        this.mvc.perform(post("/comments/update/" + comment.getId()))
                .andExpect(redirectedUrl("/comments"));

        verify(service).getComment(comment.getId());
        verify(service).updateComment(comment);
        verify(service).getAllComments();
    }

    @Test
    void deleteComment() throws Exception {
        Comment comment = new Comment().setId("1").setMessage("message");
        when(service.deleteComment(comment.getId())).thenReturn(comment);

        this.mvc.perform(post("/comments/delete/" + comment.getId()))
                .andExpect(redirectedUrl("/comments"));

        verify(service).deleteComment(comment.getId());
    }
}