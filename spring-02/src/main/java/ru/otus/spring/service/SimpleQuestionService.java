package ru.otus.spring.service;

import lombok.Getter;
import lombok.extern.java.Log;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Question;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Log
@Service
public class SimpleQuestionService implements QuestionService {

    @Getter
    private List<Question> questions;

    public void init(Resource resource) {
        try {
            questions = getQuestions(resource);
        } catch (IOException e) {
            String error = "Произошла ошибка по время чтения ресурса с вопросами, -> " + e.getMessage();
            log.severe(error);
            throw new IllegalArgumentException(error);
        }
    }

    private List<Question> getQuestions(Resource resource) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()));

        List<Question> questions = new ArrayList<>();
        while (reader.ready()) {
            String[] line = reader.readLine().split(";");
            questions.add(new Question(line[0], line[1]));
        }
        return questions;

    }
}
