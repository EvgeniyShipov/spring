package ru.otus.spring.domain.book;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.spring.domain.author.Author;
import ru.otus.spring.domain.author.AuthorRepository;
import ru.otus.spring.domain.comment.CommentRepository;
import ru.otus.spring.domain.jenre.Jenre;
import ru.otus.spring.domain.jenre.JenreRepository;
import ru.otus.spring.service.LibraryService;

import java.util.Collections;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
class BookControllerTest {

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
    void getAllBooks() throws Exception {
        Book book = new Book().setId("1").setTitle("title");
        when(service.getAllBooks()).thenReturn(Collections.singletonList(book));

        this.mvc.perform(get("/books"))
                .andExpect(status().isOk());

        verify(service).getAllBooks();
    }

    @Test
    void getBook() throws Exception {
        Book book = new Book().setId("1").setTitle("title");
        when(service.getBook(book.getId())).thenReturn(book);

        this.mvc.perform(get("/books/" + book.getId()))
                .andExpect(status().isOk());

        verify(service).getBook(book.getId());
    }

    @Test
    void createBook() throws Exception {
        Jenre jenre =  new Jenre().setId("1");
        Author author = new Author().setId("1").setName("name");
        Book book = new Book().setId("1").setTitle("title").setAuthor(author).setJenre(jenre);
        when(service.createBook(book.getTitle(), book.getAuthor().getId(), book.getJenre().getId())).thenReturn(book);

        this.mvc.perform(post("/books")
                .param("title", book.getTitle())
                .param("idAuthor", book.getAuthor().getId())
                .param("idJenre", book.getJenre().getId()))
                .andExpect(status().isOk());

        verify(service).createBook(book.getTitle(), author.getId(), jenre.getId());
    }

    @Test
    void deleteBook() throws Exception {
        Author author = new Author().setId("1").setName("name");
        Book book = new Book().setId("1").setTitle("title").setAuthor(author);
        when(service.deleteBook(book.getId())).thenReturn(book);

        this.mvc.perform(delete("/books/" + book.getId()))
                .andExpect(status().isOk());

        verify(service).deleteBook(book.getId());
    }
}