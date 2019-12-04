package ru.otus.spring.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GameServiceTest {

    private SimpleQuestionService gameService;

    @BeforeEach
    void setUp() {
        gameService = new SimpleQuestionService();
        Resource resource = new ClassPathResource("questions.CSV");
        gameService.init(resource);
    }

    @Test
    void initTest() throws IOException {
        Resource resource = mock(Resource.class);

        String question = "Вопрос";
        String answer = "Ответ";
        ByteArrayInputStream inputStream = new ByteArrayInputStream((question + ";" + answer).getBytes());
        when(resource.getInputStream()).thenReturn(inputStream);

        assertDoesNotThrow(() -> gameService.init(resource));

        assertThat(gameService.getQuestions()).hasSize(1);
        assertEquals(question, gameService.getQuestions().get(0).getQuestion());
        assertEquals(answer, gameService.getQuestions().get(0).getAnswer());
    }

    @Test
    void errorInitTest() throws IOException {
        Resource resource = mock(Resource.class);

        when(resource.getInputStream()).thenThrow(new IOException());

        assertThrows(IllegalArgumentException.class, () -> gameService.init(resource));
    }
}