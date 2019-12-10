package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Сервис ввода/вывода сообщений через консоль
 */
@RequiredArgsConstructor
public class ConsoleInOutService implements InOutService {

    private final Scanner reader;
    private final PrintStream writer;

    public String read() {
        return reader.nextLine();
    }

    public void write(String message) {
        writer.println(message);
    }
}
