package ru.otus.spring.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.domain.Jenre;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Transactional
@Repository
public class JenreRepositoryJpa implements JenreRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Jenre getById(long id) {
        return em.find(Jenre.class, id);
    }

    @Override
    public Jenre create(Jenre jenre) {
        if (jenre.getId() == 0) {
            em.persist(jenre);
            return jenre;
        }

        return em.merge(jenre);
    }

    @Override
    public List<Jenre> getAll() {
        return em.createQuery("from Jenre", Jenre.class).getResultList();
    }
}
