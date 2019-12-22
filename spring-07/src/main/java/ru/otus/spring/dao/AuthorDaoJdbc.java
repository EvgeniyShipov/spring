package ru.otus.spring.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Author;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class AuthorDaoJdbc implements AuthorDao {
    private final JdbcOperations jdbc;
    private final RowMapper<Author> rowMapper = new BeanPropertyRowMapper<>(Author.class);

    @Override
    public Author getById(int id) {
        return jdbc.queryForObject("select * from AUTHORS where ID = ?", rowMapper, id);
    }

    @Override
    public List<Author> getAll() {
        return jdbc.query("select * from AUTHORS", rowMapper);
    }

    @Override
    public void deleteById(int id) {
        jdbc.update("delete from AUTHORS where ID = ?", id);
    }

    @Override
    public void create(Author author) {
        jdbc.update("insert into AUTHORS (NAME, SURNAME, PATRONYMIC) values (?, ?, ?)",
                author.getName(),
                author.getSurname(),
                author.getPatronymic());
    }
}
