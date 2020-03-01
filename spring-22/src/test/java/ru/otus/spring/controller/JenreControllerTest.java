package ru.otus.spring.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.spring.domain.Jenre;
import ru.otus.spring.repository.*;
import ru.otus.spring.service.LibraryService;
import ru.otus.spring.service.LibraryUserDetailsService;

import java.util.Collections;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.ResultMatcher.matchAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WithMockUser(username = "user", authorities = {"ROLE_USER"})
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
    @MockBean
    private LibraryUserDetailsService libraryUserDetailsService;
    @MockBean
    private UserRepository userRepository;

    @Test
    void getAllJenre() throws Exception {
        Jenre jenre = new Jenre().setId("1").setType("jenre");
        when(service.getAllJenre()).thenReturn(Collections.singletonList(jenre));

        this.mvc.perform(get("/jenres"))
                .andExpect(matchAll(
                        status().isOk(),
                        model().size(1),
                        model().attributeExists("jenres"),
                        view().name("jenres")));

        verify(service).getAllJenre();
    }

    @Test
    void getJenre() throws Exception {
        Jenre jenre = new Jenre().setId("1").setType("jenre");
        when(service.getJenre(jenre.getId())).thenReturn(jenre);

        this.mvc.perform(get("/jenres/" + jenre.getId()))
                .andExpect(matchAll(
                        status().isOk(),
                        model().size(1),
                        model().attributeExists("jenre"),
                        view().name("jenre")));

        verify(service).getJenre(jenre.getId());
    }

    @Test
    void createJenre() throws Exception {
        Jenre jenre = new Jenre().setId("1").setType("jenre");

        this.mvc.perform(get("/jenres/create")
                .param("jenre", jenre.toString()))
                .andExpect(matchAll(
                        status().isOk(),
                        view().name("jenre_new")));
    }

    @Test
    void createJenre2() throws Exception {
        Jenre jenre = new Jenre().setId("1").setType("jenre");
        when(service.createJenre(jenre.getType())).thenReturn(jenre);

        this.mvc.perform(post("/jenres/create")
                .param("type", jenre.getType()))
                .andExpect(redirectedUrl("/jenres"));

        verify(service).createJenre(jenre.getType());
    }

    @Test
    void updateJenre() throws Exception {
        Jenre jenre = new Jenre().setId("1").setType("jenre");
        when(service.getJenre(jenre.getId())).thenReturn(jenre);

        this.mvc.perform(post("/jenres/update/" + jenre.getId()))
                .andExpect(redirectedUrl("/jenres"));

        verify(service).getJenre(jenre.getId());
        verify(service).updateJenre(jenre);
        verify(service).getAllJenre();
    }

    @Test
    void deleteJenre() throws Exception {
        Jenre jenre = new Jenre().setId("1").setType("jenre");
        when(service.deleteJenre(jenre.getId())).thenReturn(jenre);

        this.mvc.perform(post("/jenres/delete/" + jenre.getId()))
                .andExpect(redirectedUrl("/jenres"));

        verify(service).deleteJenre(jenre.getId());
    }
}