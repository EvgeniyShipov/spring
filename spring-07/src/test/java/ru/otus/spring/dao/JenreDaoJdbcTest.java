package ru.otus.spring.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.spring.domain.Jenre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Import(JenreDaoJdbc.class)
class JenreDaoJdbcTest {

    @Autowired
    private JenreDaoJdbc jenreDaoJdbc;

    @Test
    void getAll() {
        List<Jenre> jenres = jenreDaoJdbc.getAll();

        assertThat(jenres).contains(Jenre.values());
    }
}