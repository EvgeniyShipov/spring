package ru.otus.spring.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class QuestionServiceTest {

    @Autowired
    private QuestionService questionService;

    @BeforeEach
    void setUp() {
        Resource resource = new ClassPathResource("questions_en.CSV");
        questionService.init(resource);
    }

    @Test
    void initTest() throws IOException {
        Resource resource = mock(Resource.class);

        String question = "Вопрос";
        String answer = "Ответ";
        ByteArrayInputStream inputStream = new ByteArrayInputStream((question + ";" + answer).getBytes());
        when(resource.getInputStream()).thenReturn(inputStream);

        assertDoesNotThrow(() -> questionService.init(resource));

        assertThat(questionService.getQuestions()).hasSize(1);
        assertEquals(question, questionService.getQuestions().get(0).getQuestion());
        assertEquals(answer, questionService.getQuestions().get(0).getAnswer());
    }

    @Test
    void errorInitTest() throws IOException {
        Resource resource = mock(Resource.class);

        when(resource.getInputStream()).thenThrow(new IOException());

        assertThrows(IllegalArgumentException.class, () -> questionService.init(resource));
    }
}