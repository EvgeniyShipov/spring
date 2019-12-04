package ru.otus.spring.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

@Log
@Service
@RequiredArgsConstructor
public class SimpleGameService implements GameService {

    private final InOutService ioService;
    @Getter
    private Map<String, String> questions;

    public void init(Resource resource) {
        try {
            questions = getQuestions(resource);
        } catch (IOException e) {
            String error = "Произошла ошибка по время чтения ресурса с вопросами, ->" + e.getMessage();
            log.severe(error);
            throw new IllegalArgumentException(error);
        }
    }

    public void startGame() {
        User user = new User();
        ioService.welcomeMessage();

        user.setFirstName(ioService.read());

        ioService.askLastName();
        user.setLastName(ioService.read());

        questions.forEach((question, correctAnswer) -> {
            ioService.write(question);

            if (correctAnswer.equalsIgnoreCase(ioService.read())) {
                ioService.correctAnswer();
                user.setScore(user.getScore() + 1);
            } else {
                ioService.incorrectAnswer(correctAnswer);
            }
        });

        ioService.result(user);
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
