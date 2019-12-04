package ru.otus.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.otus.spring.config.AppConfig;
import ru.otus.spring.service.GameController;

public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        GameController controller = context.getBean(GameController.class);
        controller.startGame();
    }
}
