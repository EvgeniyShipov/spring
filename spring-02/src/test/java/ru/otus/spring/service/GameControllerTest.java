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
    private QuestionService questionService;
    private MessageService messageService;

    @BeforeEach
    void setUp() {
        questionService = mock(SimpleQuestionService.class);
        messageService = mock(LocaliseMessageService.class);
        gameController = new GameController(questionService, messageService);
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

        when(messageService.askName()).thenReturn(firstName);
        when(messageService.askLastName()).thenReturn(lastName);
        when(messageService.askQuestion(question)).thenReturn(answer);

        gameController.startGame();

        verify(messageService).askName();
        verify(messageService).askLastName();
        verify(messageService).askQuestion(question);
        verify(messageService).correctAnswer();
        verify(messageService).result(any(User.class));

        verifyNoMoreInteractions(messageService);
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

        when(messageService.askName()).thenReturn(firstName);
        when(messageService.askLastName()).thenReturn(lastName);
        when(messageService.askQuestion(question)).thenReturn("неверный ответ");

        gameController.startGame();

        verify(messageService).askName();
        verify(messageService).askLastName();
        verify(messageService).askQuestion(question);
        verify(messageService).incorrectAnswer(answer);
        verify(messageService).result(any(User.class));

        verifyNoMoreInteractions(messageService);
    }
}