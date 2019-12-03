package ru.otus.spring.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static ru.otus.spring.service.GameService.*;

class GameServiceTest {

    private GameService gameService;
    private QuestionService questionService;
    private InOutService inOutService;

    @BeforeEach
    public void setUp() {
        questionService = mock(QuestionService.class);
        inOutService = mock(InOutService.class);
        gameService = new GameService(questionService, inOutService);
    }

    @Test
    void startGameTest() {
        Map<String, String> questions = new HashMap<>();
        String question = "Вопрос";
        String answer = "Ответ";
        questions.put(question, answer);

        when(questionService.getQuestions()).thenReturn(questions);

        String firstName = "firstName";
        String lastName = "lastName";

        when(inOutService.read())
                .thenReturn(firstName)
                .thenReturn(lastName)
                .thenReturn(answer);

        gameService.startGame();

        verify(inOutService, times(3)).read();
        verify(inOutService).write(WELCOME_MESSAGE);
        verify(inOutService).write(ASK_LAST_NAME);
        verify(inOutService).write(question);
        verify(inOutService).write(CORRECT_ANSWER);
        verify(inOutService).writeFormat(eq(RESULT_MESSAGE), any());
        verify(questionService).getQuestions();
    }

    @Test
    void startGameNegativeTest() {
        Map<String, String> questions = new HashMap<>();
        String question = "Вопрос";
        String answer = "Ответ";
        questions.put(question, answer);

        when(questionService.getQuestions()).thenReturn(questions);

        String firstName = "firstName";
        String lastName = "lastName";

        when(inOutService.read())
                .thenReturn(firstName)
                .thenReturn(lastName)
                .thenReturn("не верный ответ");

        gameService.startGame();

        verify(inOutService, times(3)).read();
        verify(inOutService).write(WELCOME_MESSAGE);
        verify(inOutService).write(ASK_LAST_NAME);
        verify(inOutService).write(question);
        verify(inOutService).write(INCORRECT_ANSWER + answer);
        verify(inOutService).writeFormat(eq(RESULT_MESSAGE), any());
        verify(questionService).getQuestions();
    }
}