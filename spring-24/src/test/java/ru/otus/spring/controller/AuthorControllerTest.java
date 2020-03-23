package ru.otus.spring.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.spring.TestConfig;
import ru.otus.spring.domain.Author;
import ru.otus.spring.repository.AuthorRepository;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser(username = "user", authorities = {"ROLE_USER"})
@WebMvcTest(AuthorController.class)
@ContextConfiguration(classes = TestConfig.class)
class AuthorControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private AuthorRepository authorRepository;
    private ObjectMapper mapper = new ObjectMapper();

    @Test
    void getAllAuthors() throws Exception {
        Author author = new Author().setId(1).setName("name");
        when(authorRepository.findAll()).thenReturn(Collections.singletonList(author));

        mvc.perform(get("/authors"))
                .andExpect(status().isOk());

        verify(authorRepository).findAll();
    }

    @Test
    void getAuthor() throws Exception {
        Author author = new Author().setId(1).setName("name");
        when(authorRepository.findById(author.getId())).thenReturn(Optional.of(author));

        mvc.perform(get("/authors/" + author.getId()))
                .andExpect(status().isOk());

        verify(authorRepository).findById(author.getId());
    }

    @Test
    void createAuthor() throws Exception {
        Author author = new Author().setId(1).setName("name").setSurname("surname").setPatronymic("patronymic");
        when(authorRepository.save(author)).thenReturn(author);

        mvc.perform(post("/authors")
                .contentType(APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsString(author)))
                .andExpect(status().isCreated());

        verify(authorRepository).save(author);
    }

    @Test
    void updateAuthor() throws Exception {
        Author author = new Author().setId(1).setName("name").setSurname("surname").setPatronymic("patronymic");
        when(authorRepository.save(author)).thenReturn(author);

        mvc.perform(put("/authors/" + author.getId())
                .contentType(APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsString(author)))
                .andExpect(status().isOk());

        verify(authorRepository).save(author);
    }

    @Test
    void deleteAuthor() throws Exception {
        Author author = new Author().setId(1).setName("name");

        mvc.perform(delete("/authors/" + author.getId()))
                .andExpect(status().isOk());

        verify(authorRepository).deleteById(author.getId());
    }
}