package ru.otus.spring.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.domain.Author;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Transactional
@Repository
public class AuthorRepositoryJpa implements AuthorRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Author getById(long id) {
        return em.find(Author.class, id);
    }

    @Override
    public List<Author> getAll() {
        return em.createQuery("from Author", Author.class).getResultList();
    }

    @Override
    public void delete(Author author) {
        em.remove(author);
    }

    @Override
    public Author create(Author author) {
        if (author.getId() == 0) {
            em.persist(author);
            return author;
        }

        return em.merge(author);
    }
}
