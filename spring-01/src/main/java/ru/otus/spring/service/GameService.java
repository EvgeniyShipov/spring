package ru.otus.spring.service;

import ru.otus.spring.domain.User;

import java.util.Scanner;

public class GameService {

    private final QuestionService questionService;
    private final Scanner scanner;

    public GameService(QuestionService questionService) {
        this.questionService = questionService;
        scanner = new Scanner(System.in);
    }

    public void startGame() {
        User user = new User();
        System.out.println("Добро пожаловать в игру!\nВведите имя");
        user.setFirstName(scanner.nextLine());

        System.out.println("Введите фамилию");
        user.setLastName(scanner.nextLine());

        questionService.getQuestions().forEach((question, correctAnswer) -> {
            System.out.println(question);

            if (correctAnswer.equalsIgnoreCase(scanner.nextLine())) {
                System.out.println("Это правильный ответ");
                user.setScore(user.getScore() + 1);
            } else {
                System.out.println("Ответ не верный, правильный ответ " + correctAnswer);
            }
        });

        System.out.printf("%s %s, ваш результат: %d", user.getFirstName(), user.getLastName(), user.getScore());
    }
}
