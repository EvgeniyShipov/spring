package ru.otus.spring.service;

import java.io.PrintStream;
import java.util.Scanner;

public class InOutService {

    private Scanner reader;
    private PrintStream writer;


    public InOutService() {
        this(new Scanner(System.in), System.out);
    }

    public InOutService(Scanner scanner, PrintStream printStream) {
        reader = scanner;
        writer = printStream;
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
