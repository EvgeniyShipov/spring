package ru.otus.spring.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Transactional
@Repository
public class BookRepositoryJpa implements BookRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Book getById(long id) {
        return em.find(Book.class, id);
    }

    @Override
    public List<Book> getByAuthor(Author author) {
        TypedQuery<Book> query = em.createQuery("from Book b " +
                "join fetch b.author " +
                "join fetch b.jenre " +
                "where b.author.id = :id_author", Book.class);
        query.setParameter("id_author", author.getId());

        return query.getResultList();
    }

    @Override
    public List<Book> getAll() {
        return em.createQuery("from Book b " +
                "join fetch b.author " +
                "join fetch b.jenre ", Book.class).getResultList();
    }

    @Override
    public Book create(Book book) {
        if (book.getId() == 0) {
            em.persist(book);
            return book;
        }

        return em.merge(book);
    }

    @Override
    public void delete(Book book) {
        em.remove(book);
    }

    @Override
    public void update(Book book) {
        em.merge(book);
    }
}