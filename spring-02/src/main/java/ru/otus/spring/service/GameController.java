package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import ru.otus.spring.domain.User;

@Controller
@RequiredArgsConstructor
public class GameController {

    private final QuestionService questionService;
    private final InOutService ioService;

    public void startGame() {
        User user = new User();
        user.setFirstName(ioService.askName());
        user.setLastName(ioService.askLastName());

        questionService.getQuestions().forEach(question -> {
            String userAnswer = ioService.askQuestion(question.getQuestion());
            String correctAnswer = question.getAnswer();

            if (correctAnswer.equalsIgnoreCase(userAnswer)) {
                ioService.correctAnswer();
                user.setScore(user.getScore() + 1);
            } else {
                ioService.incorrectAnswer(correctAnswer);
            }
        });

        ioService.result(user);
    }
}
