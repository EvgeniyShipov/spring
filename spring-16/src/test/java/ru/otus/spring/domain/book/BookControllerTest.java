package ru.otus.spring.domain.book;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import ru.otus.spring.domain.author.Author;
import ru.otus.spring.service.LibraryService;

import java.util.Collections;

import static org.mockito.Mockito.*;

class BookControllerTest {

    private LibraryService service;
    private BookController controller;
    private Model model;

    @BeforeEach
    void setUp() {
        model = mock(Model.class);
        service = mock(LibraryService.class);
        controller = new BookController(service);
    }

    @Test
    void getAllBooks() {
        Book book = new Book().setId("1").setTitle("title");
        when(service.getAllBooks()).thenReturn(Collections.singletonList(book));

        controller.getAllBooks(model);

        verify(service).getAllBooks();
    }

    @Test
    void getBook() {
        Book book = new Book().setId("1").setTitle("title");
        when(service.getBook(book.getId())).thenReturn(book);

        controller.getBook(book.getId(), model);

        verify(service).getBook(book.getId());
    }

    @Test
    void createBook() {
        String idJenre = "1";
        Author author = new Author().setId("1").setName("name");
        Book book = new Book().setId("1").setTitle("title").setAuthor(author);
        when(service.createBook(book.getId(), author.getId(), idJenre)).thenReturn(book);

        controller.createBook(book.getId(), author.getId(), idJenre, model);

        verify(service).createBook(book.getId(), author.getId(), idJenre);
    }

    @Test
    void deleteBook() {
        Author author = new Author().setId("1").setName("name");
        Book book = new Book().setId("1").setTitle("title").setAuthor(author);
        when(service.deleteBook(book.getId())).thenReturn(book);

        controller.deleteBook(book.getId(), model);

        verify(service).deleteBook(book.getId());
    }
}