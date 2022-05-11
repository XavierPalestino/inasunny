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
import mx.dev.blank.entity.Book;
import mx.dev.blank.entity.Book_;
import mx.dev.blank.entity.Category;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CategoryJpaDAO implements CategoryDAO {

  @Setter(onMethod = @__(@PersistenceContext), value = AccessLevel.PACKAGE)
  private EntityManager em;

  /* create */
  @Override
  public void create(final Category category) {
    em.persist(category);
  }

  /* update */
  @Override
  public void update(final Category category) {
    em.merge(category);
  }

  /* Delete */
  @Override
  public void delete(final Category category) {
    em.remove(category);
  }

  /* Select * FROM category WHERE id = ? */
  @Override
  public Category findById(final int id) {
    return em.find(Category.class, id);
  }

  @Override
  public List<Category> findByBookId(final int bookId) {

    final CriteriaBuilder builder = em.getCriteriaBuilder();
    final CriteriaQuery<Category> query = builder.createQuery(Category.class);
    final Root<Book> root = query.from(Book.class);

    final Join<Book, Category> categoryJoin = root.join(Book_.categories);

    query.select(categoryJoin).where(builder.equal(root.get(Book_.id), bookId));

    return em.createQuery(query).getResultList();
  }

  /* SELECT * FROM category */
  @Override
  public List<Category> findAll() {

    final CriteriaBuilder builder = em.getCriteriaBuilder();
    final CriteriaQuery<Category> query = builder.createQuery(Category.class);
    final Root<Category> root = query.from(Category.class);

    query.select(root);

    return em.createQuery(query).getResultList();
  }
}
