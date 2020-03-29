package ru.otus.spring.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Fix {

    private final String name;
    private final int timeToInstall;
}
