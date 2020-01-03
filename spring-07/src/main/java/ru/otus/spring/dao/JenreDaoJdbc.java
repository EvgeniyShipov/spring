package ru.otus.spring.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Jenre;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class JenreDaoJdbc implements JenreDao {

    private final JdbcOperations jdbc;
    private final RowMapper<Jenre> rowMapper = new BeanPropertyRowMapper<>(Jenre.class);

    @Override
    public Jenre getById(long id) {
        return jdbc.queryForObject("select * from JENRE where id = ?", rowMapper, id);
    }

    @Override
    public void create(Jenre jenre) {
        jdbc.update("insert into JENRE (type) values (?)", jenre.getType());
    }

    public List<Jenre> getAll() {
        return jdbc.query("select * from JENRE", rowMapper);
    }
}
