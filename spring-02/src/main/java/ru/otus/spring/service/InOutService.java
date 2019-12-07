package ru.otus.spring.service;

/**
 * Сервис ввода/вывода сообщений
 */
public interface InOutService {

    /**
     * Чтение сообщения
     *
     * @return -сообщение
     */
    String read();

    /**
     * Вывод сообщения
     *
     * @param message сообщение
     */
    void write(String message);
}
