package ru.otus.spring.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import ru.otus.spring.service.GameController;

import static org.springframework.shell.Availability.available;
import static org.springframework.shell.Availability.unavailable;

@ShellComponent
@RequiredArgsConstructor
public class GameCommand {

    private final GameController gameController;

    @ShellMethod(value = "login", key = {"login", "l"})
    public void login() {
        gameController.login();
    }

    @ShellMethodAvailability("isLogin")
    @ShellMethod(value = "start game", key = {"start-game", "sg"})
    public void startGame() {
        gameController.startGame();
    }

    private Availability isLogin() {
        return gameController.isLogin()
                ? available()
                : unavailable("you need to login.");
    }
}
