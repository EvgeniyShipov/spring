package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import ru.otus.spring.domain.User;

@Controller
@RequiredArgsConstructor
public class GameController {

    private final QuestionService questionService;
    private final MessageService messageService;

    private User user;

    public void login() {
        user = new User();
        user.setFirstName(messageService.askName());
        user.setLastName(messageService.askLastName());
    }

    public void startGame() {
        questionService.getQuestions().forEach(question -> {
            String userAnswer = messageService.askQuestion(question.getQuestion());
            String correctAnswer = question.getAnswer();

            if (correctAnswer.equalsIgnoreCase(userAnswer)) {
                messageService.correctAnswer();
                user.setScore(user.getScore() + 1);
            } else {
                messageService.incorrectAnswer(correctAnswer);
            }
        });

        messageService.result(user);
    }

    public boolean isLogin() {
        return user != null;
    }
}