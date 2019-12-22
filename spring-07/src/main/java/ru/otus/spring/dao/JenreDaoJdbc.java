package ru.otus.spring.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Jenre;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class JenreDaoJdbc implements JenreDao {

    private final JdbcOperations jdbc;

    public List<Jenre> getAll() {
        return jdbc.query("select * from JENRE",
                (rs, rowNum) -> Jenre.valueOf(rs.getString("name")));
    }
}
