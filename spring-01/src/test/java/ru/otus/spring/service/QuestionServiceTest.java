package ru.otus.spring.service;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.Resource;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class QuestionServiceTest {

    @Test
    public void initTest() throws IOException {

        Resource resource = mock(Resource.class);
        String question = "Вопрос";
        String answer = "Ответ";
        ByteArrayInputStream inputStream = new ByteArrayInputStream((question + ";" + answer).getBytes());
        when(resource.getInputStream()).thenReturn(inputStream);

        QuestionService questionService = new QuestionService(resource);

        assertThat(questionService.getQuestions())
                .hasSize(1)
                .containsKeys(question)
                .containsValue(answer);
    }
}