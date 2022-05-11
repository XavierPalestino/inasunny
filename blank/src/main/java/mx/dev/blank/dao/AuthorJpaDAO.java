package mx.dev.blank.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import mx.dev.blank.entity.Author;
import mx.dev.blank.entity.Book;
import mx.dev.blank.entity.Book_;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AuthorJpaDAO implements AuthorDAO {

  @Setter(onMethod = @__(@PersistenceContext), value = AccessLevel.PACKAGE)
  private EntityManager em;

  /* create */
  @Override
  public void create(final Author author) {
    em.persist(author);
  }

  /* update */
  @Override
  public void update(final Author author) {
    em.merge(author);
  }

  /* Delete */
  @Override
  public void delete(final Author author) {
    em.remove(author);
  }

  /* Select * FROM author WHERE id = ? */
  @Override
  public Author findById(final int id) {
    return em.find(Author.class, id);
  }

  @Override
  public List<Author> findByBookId(final int bookId) {

    final CriteriaBuilder builder = em.getCriteriaBuilder();
    final CriteriaQuery<Author> query = builder.createQuery(Author.class);
    final Root<Book> root = query.from(Book.class);

    final Join<Book, Author> authorJoin = root.join(Book_.authors);

    query.select(authorJoin).where(builder.equal(root.get(Book_.id), bookId));

    return em.createQuery(query).getResultList();
  }

  /* SELECT * FROM authors */
  @Override
  public List<Author> findAll() {

    final CriteriaBuilder builder = em.getCriteriaBuilder();
    final CriteriaQuery<Author> query = builder.createQuery(Author.class);
    final Root<Author> root = query.from(Author.class);

    query.select(root);

    return em.createQuery(query).getResultList();
  }
}
