package ru.otus.spring.service;

import lombok.AllArgsConstructor;

import java.io.PrintStream;
import java.util.Scanner;

@AllArgsConstructor
public class InOutService {

    private final Scanner reader;
    private final PrintStream writer;

    public InOutService() {
        this(new Scanner(System.in), System.out);
    }

    public void write(String line) {
        writer.println(line);
    }

    public void writeFormat(String format, Object... args) {
        writer.printf(format, args);
    }

    public String read() {
        return reader.nextLine();
    }
}
