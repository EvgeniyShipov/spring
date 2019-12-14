package ru.otus.spring.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.spring.service.GameController;

@ShellComponent
@RequiredArgsConstructor
public class GameCommand {

    private final GameController gameController;

    @ShellMethod(value = "login", key = {"login", "l"})
    public void login() {
        gameController.login();
    }

    @ShellMethod(value = "start game", key = {"start-game", "sg"})
    public void startGame() {
        gameController.startGame();
    }
}
