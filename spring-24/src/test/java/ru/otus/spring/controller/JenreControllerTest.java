package ru.otus.spring.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.spring.TestConfig;
import ru.otus.spring.domain.Jenre;
import ru.otus.spring.repository.JenreRepository;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser(username = "user", authorities = {"ROLE_ADMIN"})
@WebMvcTest(JenreController.class)
@ContextConfiguration(classes = TestConfig.class)
class JenreControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private JenreRepository jenreRepository;
    private ObjectMapper mapper = new ObjectMapper();

    @Test
    void getAllJenre() throws Exception {
        Jenre jenre = new Jenre().setId(1).setType("jenre");
        when(jenreRepository.findAll()).thenReturn(Collections.singletonList(jenre));

        mvc.perform(get("/jenres"))
                .andExpect(status().isOk());

        verify(jenreRepository).findAll();
    }

    @Test
    void getJenre() throws Exception {
        Jenre jenre = new Jenre().setId(1).setType("jenre");
        when(jenreRepository.findById(jenre.getId())).thenReturn(Optional.of(jenre));

        mvc.perform(get("/jenres/" + jenre.getId()))
                .andExpect(status().isOk());

        verify(jenreRepository).findById(jenre.getId());
    }

    @Test
    void createJenre() throws Exception {
        Jenre jenre = new Jenre().setId(1).setType("jenre");
        when(jenreRepository.save(jenre)).thenReturn(jenre);

        mvc.perform(post("/jenres")
                .contentType(APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsString(jenre)))
                .andExpect(status().isCreated());

        verify(jenreRepository).save(jenre);
    }

    @Test
    void updateJenre() throws Exception {
        Jenre jenre = new Jenre().setId(1).setType("jenre");
        when(jenreRepository.save(jenre)).thenReturn(jenre);

        mvc.perform(put("/jenres/" + jenre.getId())
                .contentType(APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsString(jenre)))
                .andExpect(status().isOk());

        verify(jenreRepository).save(jenre);
    }

    @Test
    void deleteJenre() throws Exception {
        Jenre jenre = new Jenre().setId(1).setType("jenre");

        mvc.perform(delete("/jenres/" + jenre.getId()))
                .andExpect(status().isOk());

        verify(jenreRepository).deleteById(jenre.getId());
    }
}