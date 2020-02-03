package ru.otus.spring.domain.author;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import ru.otus.spring.service.LibraryService;

import java.util.Collections;

import static org.mockito.Mockito.*;

class AuthorControllerTest {

    private LibraryService service;
    private AuthorController controller;
    private Model model;

    @BeforeEach
    void setUp() {
        model = mock(Model.class);
        service = mock(LibraryService.class);
        controller = new AuthorController(service);
    }

    @Test
    void getAllAuthors() {
        Author author = new Author().setId("1").setName("name");
        when(service.getAllAuthors()).thenReturn(Collections.singletonList(author));

        controller.getAllAuthors(model);

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