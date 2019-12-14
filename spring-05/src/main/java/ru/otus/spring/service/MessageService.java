package ru.otus.spring.service;

import ru.otus.spring.domain.User;

/**
 * Сервис работы с сообщениями
 */
public interface MessageService {

    /**
     * Вывод приветсвенного сообщения, и запрос имени поьзователя
     *
     * @return - имя пользователя
     */
    String askName();

    /**
     * Запрос фамилии пользователя
     *
     * @return - фамилия пользователя
     */
    String askLastName();

    /**
     * Вывод сообщения
     *
     * @param question - вопрос
     * @return - ответ
     */
    String askQuestion(String question);

    /**
     * Вывод сообщения о правильном ответе
     */
    void correctAnswer();

    /**
     * Вывод сообщения о неправильном ответе
     *
     * @param correctAnswer - правильный ответ
     */
    void incorrectAnswer(String correctAnswer);

    /**
     * Вывод итового результата
     *
     * @param user - пользователь
     */
    void result(User user);
}
