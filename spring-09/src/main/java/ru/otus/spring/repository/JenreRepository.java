package ru.otus.spring.repository;

import ru.otus.spring.domain.Jenre;

import java.util.List;

public interface JenreRepository {

    List<Jenre> getAll();

    Jenre getById(long id);

    Jenre create(Jenre jenre);
}
