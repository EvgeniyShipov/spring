package ru.otus.spring.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.MessageSource;
import ru.otus.spring.domain.User;

import java.util.Locale;

import static java.util.Locale.ENGLISH;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class LocaliseMessageServiceTest {

    private MessageSource messageSource;
    private InOutService inOutService;
    private LocaliseMessageService messageService;
    private Locale locale;

    @BeforeEach
    void setUp() {
        locale = ENGLISH;
        messageSource = mock(MessageSource.class);
        inOutService = mock(InOutService.class);
        messageService = new LocaliseMessageService(locale, messageSource, inOutService);
    }

    @Test
    void askName() {
        String message = "message";
        String name = "name";
        when(messageSource.getMessage(anyString(), any(), eq(locale))).thenReturn(message);
        when(inOutService.read()).thenReturn(name);

        String resultName = messageService.askName();

        assertEquals(name, resultName);

        verify(messageSource).getMessage(anyString(), any(), eq(locale));
        verify(inOutService).write(message);
        verify(inOutService).read();
        verifyNoMoreInteractions(messageSource);
        verifyNoMoreInteractions(inOutService);
    }

    @Test
    void askLastName() {
        String message = "message";
        String lastName = "name";
        when(messageSource.getMessage(anyString(), any(), eq(locale))).thenReturn(message);
        when(inOutService.read()).thenReturn(lastName);

        String resultLastName = messageService.askLastName();

        assertEquals(lastName, resultLastName);

        verify(messageSource).getMessage(anyString(), any(), eq(locale));
        verify(inOutService).write(message);
        verify(inOutService).read();
        verifyNoMoreInteractions(messageSource);
        verifyNoMoreInteractions(inOutService);
    }

    @Test
    void correctAnswer() {
        String message = "message";
        when(messageSource.getMessage(anyString(), any(), eq(locale))).thenReturn(message);

        messageService.correctAnswer();

        verify(messageSource).getMessage(anyString(), any(), eq(locale));
        verify(inOutService).write(message);
        verifyNoMoreInteractions(messageSource);
        verifyNoMoreInteractions(inOutService);
    }

    @Test
    void incorrectAnswer() {
        String message = "message";
        String correctAnswer = "name";
        Object[] params = {correctAnswer};
        when(messageSource.getMessage(anyString(), eq(params), eq(locale))).thenReturn(message);

        messageService.incorrectAnswer(correctAnswer);

        verify(messageSource).getMessage(anyString(), eq(params), eq(locale));
        verify(inOutService).write(message);
        verifyNoMoreInteractions(messageSource);
        verifyNoMoreInteractions(inOutService);
    }

    @Test
    void result() {
        String firstName = "firstName";
        String lastName = "lastName";
        int score = 5;
        User user = new User(firstName, lastName, score);

        String message = "message";
        Object[] params = {firstName, lastName, String.valueOf(score)};
        when(messageSource.getMessage(anyString(),  eq(params), eq(locale)))
                .thenReturn(message);

        messageService.result(user);

        verify(messageSource).getMessage(anyString(),  eq(params), eq(locale));
        verify(inOutService).write(message);
        verifyNoMoreInteractions(messageSource);
        verifyNoMoreInteractions(inOutService);
    }

    @Test
    void askQuestion() {
        String question = "question";
        String answer = "answer";
        when(inOutService.read()).thenReturn(answer);

        String resultAnswer = messageService.askQuestion(question);

        assertEquals(answer, resultAnswer);

        verify(inOutService).write(question);
        verify(inOutService).read();
        verifyNoMoreInteractions(messageSource);
        verifyNoMoreInteractions(inOutService);
    }
}