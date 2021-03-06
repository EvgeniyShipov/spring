package ru.otus.spring.dao;

import ru.otus.spring.domain.Jenre;

import java.util.List;

public interface JenreDao {

    List<Jenre> getAll();

    Jenre getById(long id);

    void create(Jenre jenre);
}
