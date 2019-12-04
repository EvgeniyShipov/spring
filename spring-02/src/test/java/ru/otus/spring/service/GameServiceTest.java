package ru.otus.spring.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import ru.otus.spring.domain.User;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Iterator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class GameServiceTest {

    private SimpleGameService gameService;
    private ConsoleInOutService inOutService;

    @BeforeEach
    public void setUp() {
        inOutService = mock(ConsoleInOutService.class);
        gameService = new SimpleGameService(inOutService);

        Resource resource = new ClassPathResource("questions.CSV");
        gameService.init(resource);
    }

    @Test
    void startGameTest() {
        Iterator<String> answerIterator = gameService.getQuestions().values().iterator();

        when(inOutService.read())
                .thenReturn("firstName")
                .thenReturn("lastName")
                .thenReturn(answerIterator.next())
                .thenReturn(answerIterator.next())
                .thenReturn(answerIterator.next())
                .thenReturn(answerIterator.next())
                .thenReturn(answerIterator.next());

        gameService.startGame();

        verify(inOutService, times(7)).read();
        verify(inOutService).welcomeMessage();
        verify(inOutService).askLastName();
        verify(inOutService, times(5)).write(anyString());
        verify(inOutService, times(5)).correctAnswer();
        verify(inOutService).result(any(User.class));

        verifyNoMoreInteractions(inOutService);
    }

    @Test
    void startGameNegativeTest() {
        when(inOutService.read())
                .thenReturn("firstName")
                .thenReturn("lastName")
                .thenReturn("не верный ответ");

        gameService.startGame();

        verify(inOutService, times(7)).read();
        verify(inOutService).welcomeMessage();
        verify(inOutService).askLastName();
        verify(inOutService, times(5)).write(anyString());
        verify(inOutService, times(5)).incorrectAnswer(anyString());
        verify(inOutService).result(any(User.class));

        verifyNoMoreInteractions(inOutService);
    }

    @Test
    public void initTest() throws IOException {
        Resource resource = mock(Resource.class);

        String question = "Вопрос";
        String answer = "Ответ";
        ByteArrayInputStream inputStream = new ByteArrayInputStream((question + ";" + answer).getBytes());
        when(resource.getInputStream()).thenReturn(inputStream);

        gameService.init(resource);

        assertThat(gameService.getQuestions())
                .hasSize(1)
                .containsKeys(question)
                .containsValue(answer);
    }
}