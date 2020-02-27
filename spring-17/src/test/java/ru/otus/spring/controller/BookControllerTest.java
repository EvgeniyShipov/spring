package ru.otus.spring.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
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
    private ObjectMapper mapper = new ObjectMapper();

    @Test
    void getAllBooks() throws Exception {
        Author author = new Author().setId("1").setName("name").setSurname("surname").setPatronymic("patronymic");
        Jenre jenre = new Jenre().setId("1").setType("jenre");
        Book book = new Book().setId("1").setTitle("title").setAuthor(author).setJenre(jenre);
        when(bookRepository.findAll()).thenReturn(Collections.singletonList(book));

        mvc.perform(get("/books"))
                .andExpect(status().isOk());

        verify(bookRepository).findAll();
    }

    @Test
    void getBook() throws Exception {
        Book book = new Book().setId("1").setTitle("title");
        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));

        mvc.perform(get("/books/" + book.getId()))
                .andExpect(status().isOk());

        verify(bookRepository).findById(book.getId());
    }

    @Test
    void createBook() throws Exception {
        Jenre jenre = new Jenre().setId("1");
        Author author = new Author().setId("1").setName("name");
        Book book = new Book().setId("1").setTitle("title").setAuthor(author).setJenre(jenre);
        when(bookRepository.save(book)).thenReturn(book);

        mvc.perform(post("/books")
                .contentType(APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsString(book)))
                .andExpect(status().isCreated());

        verify(bookRepository).save(book);
    }

    @Test
    void updateBook() throws Exception {
        Jenre jenre = new Jenre().setId("1");
        Author author = new Author().setId("1").setName("name");
        Book book = new Book().setId("1").setTitle("title").setAuthor(author).setJenre(jenre);
        when(bookRepository.save(book)).thenReturn(book);

        mvc.perform(put("/books/" + book.getId())
                .contentType(APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsString(book)))
                .andExpect(status().isOk());

        verify(bookRepository).save(book);
    }

    @Test
    void deleteBook() throws Exception {
        Author author = new Author().setId("1").setName("name");
        Book book = new Book().setId("1").setTitle("title").setAuthor(author);

        mvc.perform(delete("/books/" + book.getId()))
                .andExpect(status().isOk());

        verify(bookRepository).deleteById(book.getId());
    }
}