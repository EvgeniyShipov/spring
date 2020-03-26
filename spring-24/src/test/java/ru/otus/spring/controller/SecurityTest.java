package ru.otus.spring.controller;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.spring.TestConfig;
import ru.otus.spring.service.LibraryService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = {AuthorController.class, BookController.class, CommentController.class, JenreController.class, MainController.class})
@ContextConfiguration(classes = TestConfig.class)
public class SecurityTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private LibraryService service;

    @ParameterizedTest
    @ValueSource(strings = {"/books", "/authors", "/comments", "/jenres", "/login"})
    @WithMockUser()
    void userTest(String url) throws Exception {
        mvc.perform(get(url))
                .andExpect(status().isOk());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "/books/create", "/books/update/1", "/books/delete/1",
            "/authors/create", "/authors/update/1", "/authors/delete/1",
            "/comments/create", "/comments/update/1", "/comments/delete/1",
            "/jenres/create", "/jenres/update/1", "/jenres/delete/1"
    })
    @WithMockUser()
    void userForbiddenTest(String url) throws Exception {
        mvc.perform(get(url))
                .andExpect(status().isForbidden());
    }

    @ParameterizedTest
    @ValueSource(strings = {"/books", "/authors", "/comments", "/jenres", "/login",
            "/books/create", "/books/update/1",
            "/authors/create", "/authors/update/1",
            "/comments/create", "/comments/update/1",
            "/jenres/create", "/jenres/update/1"})
    @WithMockUser(roles = "EDITOR")
    void editorTest(String url) throws Exception {
        mvc.perform(get(url))
                .andExpect(status().isOk());
    }

    @ParameterizedTest
    @ValueSource(strings = {"/books/delete/1", "/authors/delete/1", "/comments/delete/1", "/jenres/delete/1"})
    @WithMockUser(roles = "EDITOR")
    void editorForbiddenTest(String url) throws Exception {
        mvc.perform(get(url))
                .andExpect(status().isForbidden());
    }

    @ParameterizedTest
    @ValueSource(strings = {"/books", "/authors", "/comments", "/jenres", "/login",
            "/books/create", "/books/update/1", "/books/delete/1",
            "/authors/create", "/authors/update/1", "/authors/delete/1",
            "/comments/create", "/comments/update/1", "/comments/delete/1",
            "/jenres/create", "/jenres/update/1", "/jenres/delete/1"})
    @WithMockUser(roles = "ADMIN")
    void adminTest(String url) throws Exception {
        mvc.perform(get(url))
                .andExpect(status().isOk());
    }
}
