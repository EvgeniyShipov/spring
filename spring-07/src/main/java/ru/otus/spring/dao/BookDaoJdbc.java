package ru.otus.spring.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BookDaoJdbc implements BookDao {
    private final JdbcOperations jdbc;
    private final BookRowMapper rowMapper = new BookRowMapper();

    @Override
    public Book getById(long id) {
        return jdbc.queryForObject("select * from BOOKS B " +
                "inner join AUTHORS A on B.ID_AUTHOR = A.ID " +
                "inner join JENRE J on B.ID_JENRE = J.ID" +
                " where B.ID = ?", rowMapper, id);
    }

    @Override
    public List<Book> getByAuthor(Author author) {
        return jdbc.query("select * from BOOKS B inner join JENRE J on B.ID_JENRE = J.ID where ID_AUTHOR = ? ",
                rowMapper, author.getId());
    }

    @Override
    public List<Book> getAll() {
        return jdbc.query("select B.ID, B.TITLE, A.ID as ID_AUTHOR, A.NAME, A.SURNAME, A.PATRONYMIC, " +
                        "J.ID as ID_JENRE, J.TYPE " +
                        "from BOOKS B " +
                        "inner join AUTHORS A on B.ID_AUTHOR = A.ID " +
                        "inner join JENRE J on B.ID_JENRE = J.ID",
                rowMapper);
    }

    @Override
    public void create(Book book) {
        jdbc.update("insert into BOOKS (TITLE, ID_AUTHOR, ID_JENRE) values (?, ?, ?)",
                book.getTitle(),
                book.getAuthor().getId(),
                book.getJenre().getId());
    }

    @Override
    public void deleteById(long id) {
        jdbc.update("delete from BOOKS where ID = ?", id);
    }

    @Override
    public void update(Book book) {
        jdbc.update("update BOOKS set TITLE = ?, ID_AUTHOR = ?, ID_JENRE = ? where ID = ?",
                book.getTitle(),
                book.getAuthor().getId(),
                book.getJenre().getId(),
                book.getId());
    }
}