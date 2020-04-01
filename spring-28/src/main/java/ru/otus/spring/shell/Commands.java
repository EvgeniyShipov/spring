package ru.otus.spring.shell;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.spring.domain.Bug;
import ru.otus.spring.service.ITDepartment;
import ru.otus.spring.service.Program;

import java.util.Collection;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@ShellComponent
public class Commands {

    private final ITDepartment itDepartment;
    private final Program program;

    @SneakyThrows
    @ShellMethod(value = "start", key = "s")
    public void start() {
        Collection<Bug> bugs = program.getBugs();
        System.out.println("New bugs: " + bugs.stream().map(Bug::getName).collect(Collectors.joining(", ")));

        String result = itDepartment.fixBug(bugs);

        System.out.println("Bug fixed " + result);
    }
}
