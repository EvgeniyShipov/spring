package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.User;

import java.io.PrintStream;
import java.util.Locale;
import java.util.Scanner;

@Service
@RequiredArgsConstructor
public class ConsoleInOutService implements InOutService {

    private final Scanner reader;
    private final PrintStream writer;
    private final MessageSource messageSource;
    private final Locale locale;

    @Override
    public void welcomeMessage() {
        writer.println(messageSource.getMessage("message.welcome", null, locale));
    }

    @Override
    public void askLastName() {
        writer.println(messageSource.getMessage("message.name", null, locale));
    }


    @Override
    public void correctAnswer() {
        writer.println(messageSource.getMessage("message.answer.correct", null, locale));
    }

    @Override
    public void incorrectAnswer(String correctAnswer) {
        writer.println(messageSource.getMessage("message.answer.incorrect",
                new String[]{correctAnswer},
                locale));
    }

    @Override
    public void result(User user) {
        writer.println(messageSource.getMessage("message.result",
                new String[]{user.getFirstName(), user.getLastName(), String.valueOf(user.getScore())},
                locale));
    }

    @Override
    public void write(String question) {
        writer.println(question);
    }

    @Override
    public String read() {
        return reader.nextLine();
    }
}
