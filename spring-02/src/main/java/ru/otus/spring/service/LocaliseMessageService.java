package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.User;

import java.util.Locale;

/**
 * Сервис работы с локализованными сообщениями
 */
@Service
@RequiredArgsConstructor
public class LocaliseMessageService implements MessageService {

    private final Locale locale;
    private final MessageSource messageSource;
    private final InOutService inOutService;

    @Override
    public String askName() {
        String messageWelcome = messageSource.getMessage("message.welcome", null, locale);
        inOutService.write(messageWelcome);
        return inOutService.read();
    }

    @Override
    public String askLastName() {
        String messageName = messageSource.getMessage("message.name", null, locale);
        inOutService.write(messageName);
        return inOutService.read();
    }

    @Override
    public void correctAnswer() {
        String correctAnswer = messageSource.getMessage("message.answer.correct", null, locale);
        inOutService.write(correctAnswer);
    }

    @Override
    public void incorrectAnswer(String correctAnswer) {
        String incorrectAnswer = messageSource.getMessage("message.answer.incorrect",
                new String[]{correctAnswer},
                locale);
        inOutService.write(incorrectAnswer);
    }

    @Override
    public void result(User user) {
        String result = messageSource.getMessage("message.result",
                new String[]{user.getFirstName(), user.getLastName(), String.valueOf(user.getScore())},
                locale);
        inOutService.write(result);
    }

    @Override
    public String askQuestion(String question) {
        inOutService.write(question);
        return inOutService.read();
    }
}
