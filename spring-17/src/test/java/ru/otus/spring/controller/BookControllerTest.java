package ru.otus.spring.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Jenre;
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

@WebMvcTest(BookController.class)
class BookControllerTest {

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
    void getAllBooks() throws Exception {
        Author author = new Author().setId("1").setName("name").setSurname("surname").setPatronymic("patronymic");
        Jenre jenre = new Jenre().setId("1").setType("jenre");
        Book book = new Book().setId("1").setTitle("title").setAuthor(author).setJenre(jenre);
        when(bookRepository.findAll()).thenReturn(Collections.singletonList(book));

        this.mvc.perform(get("/books"))
                .andExpect(status().isOk());

        verify(bookRepository).findAll();
    }

    @Test
    void getBook() throws Exception {
        Book book = new Book().setId("1").setTitle("title");
        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));

        this.mvc.perform(get("/books/" + book.getId()))
                .andExpect(status().isOk());

        verify(bookRepository).findById(book.getId());
    }

    @Test
    void createBook() throws Exception {
        Jenre jenre = new Jenre().setId("1");
        Author author = new Author().setId("1").setName("name");
        Book book = new Book().setId("1").setTitle("title").setAuthor(author).setJenre(jenre);
        when(bookRepository.save(book)).thenReturn(book);

        this.mvc.perform(post("/books")
                .param("title", book.getTitle())
                .param("author", book.getAuthor().getId())
                .param("jenre", book.getJenre().getId()))
                .andExpect(status().isOk());

        verify(bookRepository).save(book);
    }

    @Test
    void updateBook() throws Exception {
        Jenre jenre = new Jenre().setId("1");
        Author author = new Author().setId("1").setName("name");
        Book book = new Book().setId("1").setTitle("title").setAuthor(author).setJenre(jenre);
        when(bookRepository.save(book)).thenReturn(book);

        this.mvc.perform(patch("/books/" + book.getId())
                .param("title", book.getTitle())
                .param("author", book.getAuthor().getId())
                .param("jenre", book.getJenre().getId()))
                .andExpect(status().isOk());

        verify(bookRepository).save(book);
    }

    @Test
    void deleteBook() throws Exception {
        Author author = new Author().setId("1").setName("name");
        Book book = new Book().setId("1").setTitle("title").setAuthor(author);

        this.mvc.perform(delete("/books/" + book.getId()))
                .andExpect(status().isOk());

        verify(bookRepository).deleteById(Long.valueOf(book.getId()));
    }
}