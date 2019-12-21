package ru.otus.spring.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
@RequiredArgsConstructor
public class GameCommand {


    @ShellMethod(value = "start", key = {"start", "s"})
    public void start() {

    }
}
