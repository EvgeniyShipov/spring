package ru.otus.spring.service;

import ru.otus.spring.domain.User;

public class GameService {

    static final String WELCOME_MESSAGE = "Добро пожаловать в игру!\nВведите имя";
    static final String ASK_LAST_NAME = "Введите фамилию";
    static final String CORRECT_ANSWER = "Это правильный ответ";
    static final String INCORRECT_ANSWER = "Ответ не верный, правильный ответ ";
    static final String RESULT_MESSAGE = "%s %s, ваш результат: %d";

    private final QuestionService questionService;
    private final InOutService ioService;

    public GameService(QuestionService questionService, InOutService ioService) {
        this.questionService = questionService;
        this.ioService = ioService;
    }

    public void startGame() {
        User user = new User();
        ioService.write(WELCOME_MESSAGE);

        user.setFirstName(ioService.read());

        ioService.write(ASK_LAST_NAME);
        user.setLastName(ioService.read());

        questionService.getQuestions().forEach((question, correctAnswer) -> {
            ioService.write(question);

            if (correctAnswer.equalsIgnoreCase(ioService.read())) {
                ioService.write(CORRECT_ANSWER);
                user.setScore(user.getScore() + 1);
            } else {
                ioService.write(INCORRECT_ANSWER + correctAnswer);
            }
        });

        ioService.writeFormat(RESULT_MESSAGE, user.getFirstName(), user.getLastName(), user.getScore());
    }
}
