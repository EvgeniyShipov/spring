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
import ru.otus.spring.service.LibraryService;

import java.util.Collections;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthorController.class)
class AuthorControllerTest {

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
    void getAllAuthors() throws Exception {
        Author author = new Author().setId("1").setName("name");
        when(service.getAllAuthors()).thenReturn(Collections.singletonList(author));

        this.mvc.perform(get("/authors"))
                .andExpect(status().isOk());

        verify(service).getAllAuthors();
    }

    @Test
    void getAuthor() throws Exception {
        Author author = new Author().setId("1").setName("name");
        when(service.getAuthor(author.getId())).thenReturn(author);

        this.mvc.perform(get("/authors/" + author.getId()))
                .andExpect(status().isOk());

        verify(service).getAuthor(author.getId());
    }

    @Test
    void createAuthor() throws Exception {
        Author author = new Author().setId("1").setName("name").setSurname("surname").setPatronymic("patronymic");

        this.mvc.perform(get("/authors/create")
                .param("name", author.getName()))
                .andExpect(status().isOk());
    }

    @Test
    void createAuthor2() throws Exception {
        Author author = new Author().setId("1").setName("name").setSurname("surname").setPatronymic("patronymic");
        when(service.createAuthor(author.getName(), author.getSurname(), author.getPatronymic())).thenReturn(author);

        this.mvc.perform(post("/authors/create")
                .param("name", author.getName())
                .param("surname", author.getSurname())
                .param("patronymic", author.getPatronymic()))
                .andExpect(status().isOk());

        verify(service).createAuthor(author.getName(), author.getSurname(), author.getPatronymic());
    }

    @Test
    void updateAuthor() throws Exception {
        Author author = new Author().setId("1").setName("name").setSurname("surname").setPatronymic("patronymic");
        when(service.getAuthor(author.getId())).thenReturn(author);

        this.mvc.perform(post("/authors/update/" + author.getId())
                .param("name", author.getName())
                .param("surname", author.getSurname())
                .param("patronymic", author.getPatronymic()))
                .andExpect(status().isOk());

        verify(service).getAuthor(author.getId());
        verify(service).updateAuthor(author);
    }

    @Test
    void deleteAuthor() throws Exception {
        Author author = new Author().setId("1").setName("name");
        when(service.deleteAuthor(author.getId())).thenReturn(author);

        this.mvc.perform(get("/authors/delete/" + author.getId()))
                .andExpect(status().isOk());

        verify(service).deleteAuthor(author.getId());
    }
}