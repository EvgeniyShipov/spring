package ru.otus.spring.domain.jenre;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.spring.domain.author.AuthorRepository;
import ru.otus.spring.domain.book.BookRepository;
import ru.otus.spring.domain.comment.CommentRepository;
import ru.otus.spring.service.LibraryService;

import java.util.Collections;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(JenreController.class)
class JenreControllerTest {

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
    void getAllJenre() throws Exception {
        Jenre jenre = new Jenre().setId("1").setType("jenre");
        when(service.getAllJenre()).thenReturn(Collections.singletonList(jenre));

        this.mvc.perform(get("/jenre"))
                .andExpect(status().isOk());

        verify(service).getAllJenre();
    }

    @Test
    void getJenre() throws Exception {
        Jenre jenre = new Jenre().setId("1").setType("jenre");
        when(service.getJenre(jenre.getId())).thenReturn(jenre);

        this.mvc.perform(get("/jenre/" + jenre.getId()))
                .andExpect(status().isOk());

        verify(service).getJenre(jenre.getId());
    }

    @Test
    void createJenre() throws Exception {
        Jenre jenre = new Jenre().setId("1").setType("jenre");
        when(service.createJenre(jenre.getType())).thenReturn(jenre);

        this.mvc.perform(post("/jenre")
                .param("name", jenre.getType()))
                .andExpect(status().isOk());

        verify(service).createJenre(jenre.getType());
    }

    @Test
    void deleteJenre() throws Exception {
        Jenre jenre = new Jenre().setId("1").setType("jenre");
        when(service.deleteJenre(jenre.getId())).thenReturn(jenre);

        this.mvc.perform(delete("/jenre/" + jenre.getId()))
                .andExpect(status().isOk());

        verify(service).deleteJenre(jenre.getId());
    }
}