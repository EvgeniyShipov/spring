package ru.otus.spring.service;

import org.springframework.core.io.Resource;

public interface GameService {

    void init(Resource resource);

    void startGame();
}
