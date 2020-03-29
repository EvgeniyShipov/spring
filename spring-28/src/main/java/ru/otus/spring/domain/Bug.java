package ru.otus.spring.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Bug {

    private final String name;
    private final int timeToFix;
}
