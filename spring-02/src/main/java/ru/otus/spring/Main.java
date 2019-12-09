package ru.otus.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import ru.otus.spring.service.GameController;

@ComponentScan
public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);

        GameController controller = context.getBean(GameController.class);
        controller.startGame();
    }
}
