package ru.otus.spring.service;

import org.springframework.core.io.Resource;
import ru.otus.spring.domain.Question;

import java.util.List;

/**
 * Сервис отвечающий за вопросы, ответы.
 */
public interface QuestionService {

    /**
     * метод инициализации сервиса
     *
     * @param resource - ресурс, из которого будут браться вопросы и ответы, разделенные символом - ";"
     */
    void init(Resource resource);
    /**
     * Получение вопросов
     *
     * @return список вопросов с ответами
     */
    List<Question> getQuestions();
}
