package ru.otus.spring.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.spring.TestConfig;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Jenre;
import ru.otus.spring.service.LibraryService;

import java.util.Collections;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.ResultMatcher.matchAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WithMockUser(username = "user", authorities = {"ROLE_USER"})
@WebMvcTest(BookController.class)
@ContextConfiguration(classes = TestConfig.class)
class BookControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private LibraryService service;

    @Test
    void getAllBooks() throws Exception {
        Author author = new Author().setId(1).setName("name").setSurname("surname").setPatronymic("patronymic");
        Jenre jenre = new Jenre().setId(1).setType("jenre");
        Book book = new Book().setId(1).setTitle("title").setAuthor(author).setJenre(jenre);
        when(service.getAllBooks()).thenReturn(Collections.singletonList(book));

        this.mvc.perform(get("/books"))
                .andExpect(matchAll(
                        status().isOk(),
                        model().attributeExists("books"),
                        view().name("books")));

        verify(service).getAllBooks();
    }

    @Test
    void getBook() throws Exception {
        Book book = new Book().setId(1).setTitle("title");
        when(service.getBook(book.getId())).thenReturn(book);

        this.mvc.perform(get("/books/" + book.getId()))
                .andExpect(matchAll(
                        status().isOk(),
                        model().attributeExists("book"),
                        view().name("book")));

        verify(service).getBook(book.getId());
    }

    @Test
    void createBook() throws Exception {
        Jenre jenre = new Jenre().setId(1);
        Author author = new Author().setId(1).setName("name");
        Book book = new Book().setId(1).setTitle("title").setAuthor(author).setJenre(jenre);

        this.mvc.perform(get("/books/create")
                .param("book", book.toString()))
                .andExpect(matchAll(
                        status().isOk(),
                        model().size(3),
                        model().attributeExists("book"),
                        model().attributeExists("authors"),
                        model().attributeExists("jenres"),
                        view().name("book_new")));

        verify(service).getAllAuthors();
        verify(service).getAllJenre();
    }

    @Test
    void createBook2() throws Exception {
        Jenre jenre = new Jenre().setId(1);
        Author author = new Author().setId(1).setName("name");
        Book book = new Book().setId(1).setTitle("title").setAuthor(author).setJenre(jenre);
        when(service.createBook(book.getTitle(), book.getAuthor().getId(), book.getJenre().getId())).thenReturn(book);

        this.mvc.perform(post("/books/create")
                .param("title", book.getTitle())
                .param("author", String.valueOf(book.getAuthor().getId()))
                .param("jenre", String.valueOf(book.getJenre().getId())))
                .andExpect(redirectedUrl("/books"));

        verify(service).createBook(book.getTitle(), author.getId(), jenre.getId());
    }

    @Test
    void updateBook() throws Exception {
        Jenre jenre = new Jenre().setId(1);
        Author author = new Author().setId(1).setName("name");
        Book book = new Book().setId(1).setTitle("title").setAuthor(author).setJenre(jenre);

        when(service.getBook(book.getId())).thenReturn(book);
        when(service.getAuthor(author.getId())).thenReturn(author);
        when(service.getJenre(jenre.getId())).thenReturn(jenre);

        this.mvc.perform(post("/books/update/" + book.getId())
                .param("title", book.getTitle())
                .param("author", String.valueOf(book.getAuthor().getId()))
                .param("jenre", String.valueOf(book.getJenre().getId())))
                .andExpect(redirectedUrl("/books"));

        verify(service).getBook(book.getId());
        verify(service).getAuthor(author.getId());
        verify(service).getJenre(jenre.getId());
        verify(service).updateBook(book);
    }

    @Test
    void deleteBook() throws Exception {
        Author author = new Author().setId(1).setName("name");
        Book book = new Book().setId(1).setTitle("title").setAuthor(author);
        when(service.deleteBook(book.getId())).thenReturn(book);

        this.mvc.perform(post("/books/delete/" + book.getId()))
                .andExpect(redirectedUrl("/books"));

        verify(service).deleteBook(book.getId());
    }
}