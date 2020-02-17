package ru.otus.spring.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.spring.domain.Author;
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

@WebMvcTest(AuthorController.class)
class AuthorControllerTest {

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
    void getAllAuthors() throws Exception {
        Author author = new Author().setId("1").setName("name");
        when(authorRepository.findAll()).thenReturn(Collections.singletonList(author));

        this.mvc.perform(get("/authors"))
                .andExpect(status().isOk());

        verify(authorRepository).findAll();
    }

    @Test
    void getAuthor() throws Exception {
        Author author = new Author().setId("1").setName("name");
        when(authorRepository.findById(author.getId())).thenReturn(Optional.of(author));

        this.mvc.perform(get("/authors/" + author.getId()))
                .andExpect(status().isOk());

        verify(authorRepository).findById(author.getId());
    }

    @Test
    void createAuthor() throws Exception {
        Author author = new Author().setId("1").setName("name").setSurname("surname").setPatronymic("patronymic");
        when(authorRepository.save(author)).thenReturn(author);

        this.mvc.perform(post("/authors")
                .param("name", author.getName())
                .param("surname", author.getSurname())
                .param("patronymic", author.getPatronymic()))
                .andExpect(status().isOk());

        verify(authorRepository).save(author);
    }

    @Test
    void updateAuthor() throws Exception {
        Author author = new Author().setId("1").setName("name").setSurname("surname").setPatronymic("patronymic");
        when(authorRepository.save(author)).thenReturn(author);

        this.mvc.perform(patch("/authors/" + author.getId())
                .param("name", author.getName())
                .param("surname", author.getSurname())
                .param("patronymic", author.getPatronymic()))
                .andExpect(status().isOk());

        verify(authorRepository).save(author);
    }

    @Test
    void deleteAuthor() throws Exception {
        Author author = new Author().setId("1").setName("name");

        this.mvc.perform(delete("/authors/" + author.getId()))
                .andExpect(status().isOk());

        verify(authorRepository).deleteById(Long.valueOf(author.getId()));
    }
}