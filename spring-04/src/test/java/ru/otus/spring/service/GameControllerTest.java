package ru.otus.spring.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.spring.domain.Question;
import ru.otus.spring.domain.User;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class GameControllerTest {

    @Autowired
    private GameController gameController;
    @MockBean
    private QuestionService questionService;
    @MockBean
    private MessageService messageService;

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