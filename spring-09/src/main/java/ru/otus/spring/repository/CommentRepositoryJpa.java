package ru.otus.spring.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.domain.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Transactional
@Repository
public class CommentRepositoryJpa implements CommentRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Comment getById(long id) {
        return em.find(Comment.class, id);
    }

    @Override
    public List<Comment> getByBookId(long idBook) {
        TypedQuery<Comment> query = em.createQuery("from Comment c where c.book.id = :id_book", Comment.class);
        query.setParameter("id_book", idBook);

        return query.getResultList();
    }

    @Override
    public List<Comment> getAll() {
        return em.createQuery("from Comment", Comment.class).getResultList();
    }

    @Override
    public void delete(Comment comment) {
        em.remove(comment);
    }

    @Override
    public Comment create(Comment comment) {
        if (comment.getId() == 0) {
            em.persist(comment);
            return comment;
        }

        return em.merge(comment);
    }
}
