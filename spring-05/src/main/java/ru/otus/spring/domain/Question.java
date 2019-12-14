package ru.otus.spring.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Question {

    private final String question;
    private final String answer;
}
