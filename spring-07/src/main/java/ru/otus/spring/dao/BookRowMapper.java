package ru.otus.spring.dao;

import org.springframework.jdbc.core.RowMapper;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Jenre;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookRowMapper implements RowMapper<Book> {
    @Override
    public Book mapRow(ResultSet resultSet, int i) throws SQLException {
        return new Book()
                .setId(resultSet.getLong("id"))
                .setTitle(resultSet.getString("title"))
                .setAuthor(new Author()
                        .setId(resultSet.getLong("id_author"))
                        .setName(resultSet.getString("name"))
                        .setSurname(resultSet.getString("surname"))
                        .setPatronymic(resultSet.getString("patronymic")))
                .setJenre(new Jenre()
                        .setId(resultSet.getLong("id_jenre"))
                        .setType(resultSet.getString("type")));
    }
}
