package ru.otus.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private String firstName;
    private String lastName;
    private int score;
}
