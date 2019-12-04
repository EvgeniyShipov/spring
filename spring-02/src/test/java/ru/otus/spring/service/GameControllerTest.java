package ru.otus.spring.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.spring.domain.Question;
import ru.otus.spring.domain.User;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class GameControllerTest {

    private GameController gameController;
    private SimpleQuestionService questionService;
    private ConsoleInOutService inOutService;

    @BeforeEach
    void setUp() {
        questionService = mock(SimpleQuestionService.class);
        inOutService = mock(ConsoleInOutService.class);
        gameController = new GameController(questionService, inOutService);
    }

    @Test
    void startGameTest() {
        List<Question> questions = new ArrayList<>();
        String question = "Вопрос";
        String answer = "Ответ";
        questions.add(new Question(question, answer));

        when(questionService.getQuestions()).thenReturn(questions);

        String firstName = "firstName";
        String lastName = "lastName";

        when(inOutService.askName()).thenReturn(firstName);
        when(inOutService.askLastName()).thenReturn(lastName);
        when(inOutService.askQuestion(question)).thenReturn(answer);

        gameController.startGame();

        verify(inOutService).askName();
        verify(inOutService).askLastName();
        verify(inOutService).askQuestion(question);
        verify(inOutService).correctAnswer();
        verify(inOutService).result(any(User.class));

        verifyNoMoreInteractions(inOutService);
    }

    @Test
    void startGameNegativeTest() {
        List<Question> questions = new ArrayList<>();
        String question = "Вопрос";
        String answer = "Ответ";
        questions.add(new Question(question, answer));

        when(questionService.getQuestions()).thenReturn(questions);

        String firstName = "firstName";
        String lastName = "lastName";

        when(inOutService.askName()).thenReturn(firstName);
        when(inOutService.askLastName()).thenReturn(lastName);
        when(inOutService.askQuestion(question)).thenReturn("неверный ответ");

        gameController.startGame();

        verify(inOutService).askName();
        verify(inOutService).askLastName();
        verify(inOutService).askQuestion(question);
        verify(inOutService).incorrectAnswer(answer);
        verify(inOutService).result(any(User.class));

        verifyNoMoreInteractions(inOutService);
    }
}