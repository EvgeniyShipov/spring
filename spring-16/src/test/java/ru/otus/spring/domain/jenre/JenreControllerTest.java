package ru.otus.spring.domain.jenre;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import ru.otus.spring.service.LibraryService;

import java.util.Collections;

import static org.mockito.Mockito.*;

class JenreControllerTest {

    private LibraryService service;
    private JenreController controller;
    private Model model;

    @BeforeEach
    void setUp() {
        model = mock(Model.class);
        service = mock(LibraryService.class);
        controller = new JenreController(service);
    }

    @Test
    void getAllJenre() {
        Jenre jenre = new Jenre().setId("1").setType("jenre");
        when(service.getAllJenre()).thenReturn(Collections.singletonList(jenre));

        controller.getAllJenre(model);

        verify(service).getAllJenre();
    }

    @Test
    void getJenre() {
        Jenre jenre = new Jenre().setId("1").setType("jenre");
        when(service.getJenre(jenre.getId())).thenReturn(jenre);

        controller.getJenre(jenre.getId(), model);

        verify(service).getJenre(jenre.getId());
    }

    @Test
    void createJenre() {
        Jenre jenre = new Jenre().setId("1").setType("jenre");
        when(service.createJenre(jenre.getType())).thenReturn(jenre);

        controller.createJenre(jenre.getType(), model);

        verify(service).createJenre(jenre.getType());
    }

    @Test
    void deleteJenre() {
        Jenre jenre = new Jenre().setId("1").setType("jenre");
        when(service.deleteJenre(jenre.getId())).thenReturn(jenre);

        controller.deleteJenre(jenre.getId(), model);

        verify(service).deleteJenre(jenre.getId());
    }
}