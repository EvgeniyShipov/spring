package ru.otus.spring.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.spring.TestConfig;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.repository.CommentRepository;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser(username = "user", authorities = {"ROLE_USER"})
@WebMvcTest(CommentController.class)
@ContextConfiguration(classes = TestConfig.class)
class CommentControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private CommentRepository commentRepository;
    private ObjectMapper mapper = new ObjectMapper();

    @Test
    void getAllComments() throws Exception {
        Book book = new Book().setId(1).setTitle("title");
        Comment comment = new Comment().setId(1).setMessage("message").setBook(book);
        when(commentRepository.findAll()).thenReturn(Collections.singletonList(comment));

        mvc.perform(get("/comments"))
                .andExpect(status().isOk());

        verify(commentRepository).findAll();
    }

    @Test
    void getComments() throws Exception {
        Comment comment = new Comment().setId(1).setMessage("message");
        when(commentRepository.findById(comment.getId())).thenReturn(Optional.of(comment));

        mvc.perform(get("/comments/" + comment.getId()))
                .andExpect(status().isOk());

        verify(commentRepository).findById(comment.getId());
    }

    @Test
    void createComment() throws Exception {
        Comment comment = new Comment().setId(1).setMessage("message");
        when(commentRepository.save(comment)).thenReturn(comment);

        mvc.perform(post("/comments")
                .contentType(APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsString(comment)))
                .andExpect(status().isCreated());

        verify(commentRepository).save(comment);
    }

    @Test
    void updateComment() throws Exception {
        Comment comment = new Comment().setId(1).setMessage("message");
        when(commentRepository.save(comment)).thenReturn(comment);

        mvc.perform(put("/comments/" + comment.getId())
                .contentType(APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsString(comment)))
                .andExpect(status().isOk());

        verify(commentRepository).save(comment);
    }

    @Test
    void deleteComment() throws Exception {
        Comment comment = new Comment().setId(1).setMessage("message");

        mvc.perform(delete("/comments/" + comment.getId()))
                .andExpect(status().isOk());

        verify(commentRepository).deleteById(comment.getId());
    }
}