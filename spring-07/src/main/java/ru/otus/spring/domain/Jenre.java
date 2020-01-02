package ru.otus.spring.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
public class Jenre {
    private long id;
    private String type;
}
