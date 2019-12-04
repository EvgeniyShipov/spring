package ru.otus.spring.domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class User {

    private String firstName;
    private String lastName;
    private int score;
}
