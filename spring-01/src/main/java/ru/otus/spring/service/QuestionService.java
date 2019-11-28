package ru.otus.spring.service;

import lombok.Getter;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class QuestionService {

    @Getter
    private final Map<String, String> questions;

    public QuestionService(Resource resource) throws IOException {
        questions = getQuestions(resource);
    }

    private Map<String, String> getQuestions(Resource resource) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()));

        Map<String, String> questions = new HashMap<>();
        while (reader.ready()) {
            String[] line = reader.readLine().split(";");
            questions.put(line[0], line[1]);
        }
        return questions;

    }
}
