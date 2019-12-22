package ru.otus.spring.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Book;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BookDaoJdbc implements BookDao {
    private final JdbcOperations jdbc;
    private final RowMapper<Book> rowMapper = new BeanPropertyRowMapper<>(Book.class);

    @Override
    public Book getById(int id) {
        return jdbc.queryForObject("select * from BOOKS where ID = ?", rowMapper, id);
    }

    @Override
    public List<Book> getByAuthor(int id) {
        return jdbc.query("select * from BOOKS where ID_AUTHOR = ?", rowMapper, id);
    }

    @Override
    public List<Book> getAll() {
        return jdbc.query("select * from BOOKS", rowMapper);
    }

    @Override
    public void create(Book book) {
        jdbc.update("insert into BOOKS (TITLE, ID_AUTHOR, ID_JENRE) values (?, ?, ?)",
                book.getTitle(),
                book.getIdAuthor(),
                book.getIdJenre());
    }

    @Override
    public void deleteById(int id) {
        jdbc.update("delete from BOOKS where ID = ?", id);
    }

    @Override
    public void update(Book book) {
        jdbc.update("update BOOKS set TITLE = ?, ID_AUTHOR = ?, ID_JENRE = ? where ID = ?",
                book.getTitle(),
                book.getIdAuthor(),
                book.getIdJenre(),
                book.getId());
    }
}