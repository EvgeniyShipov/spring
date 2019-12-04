package ru.otus.spring.service;

import ru.otus.spring.domain.User;

public interface InOutService {

    void welcomeMessage();

    void askLastName();

    void correctAnswer();

    void incorrectAnswer(String correctAnswer);

    void result(User args);

    void write(String question);

    String read();
}
