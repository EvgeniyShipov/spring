package ru.otus.spring.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;

class GameServiceTest {

    @Test
    public void initTest() {
        try {
            GameService gameService = new GameService(mock(QuestionService.class));
        } catch (Exception e) {
            fail("Ошибка при создании объекта GameService");
        }
    }
}