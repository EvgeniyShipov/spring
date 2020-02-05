package ru.otus.spring.domain.author;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;
import ru.otus.spring.domain.book.BookRepository;
import ru.otus.spring.domain.comment.CommentRepository;
import ru.otus.spring.domain.jenre.JenreRepository;
import ru.otus.spring.service.LibraryService;

import java.util.Collections;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthorController.class)
class AuthorControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private AuthorController controller;
    @MockBean
    private LibraryService service;
    @MockBean
    private Model model;

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
                .andExpect(status().isOk())
                .andExpect(content().string("authors"));

        verify(service).getAllAuthors();
    }

    @Test
    void getAuthor() {
        Author author = new Author().setId("1").setName("name");
        when(service.getAuthor(author.getId())).thenReturn(author);

        controller.getAuthor(author.getId(), model);

        verify(service).getAuthor(author.getId());
    }

    @Test
    void createAuthor() {
        Author author = new Author().setId("1").setName("name").setSurname("surname").setPatronymic("patronymic");
        when(service.createAuthor(author.getName(), author.getSurname(), author.getPatronymic())).thenReturn(author);

        controller.createAuthor(author.getName(), author.getSurname(), author.getPatronymic(), model);

        verify(service).createAuthor(author.getName(), author.getSurname(), author.getPatronymic());
    }

    @Test
    void deleteAuthor() {
        Author author = new Author().setId("1").setName("name");
        when(service.deleteAuthor(author.getId())).thenReturn(author);

        controller.deleteAuthor(author.getId(), model);

        verify(service).deleteAuthor(author.getId());
    }
}