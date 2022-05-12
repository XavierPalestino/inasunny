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
import mx.dev.blank.entity.Product;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProductJpaDAO implements ProductDAO {

  @Setter(onMethod = @__(@PersistenceContext), value = AccessLevel.PACKAGE)
  private EntityManager em;

  /* create */
  @Override
  public void create(final Product product) {
    em.persist(product);
  }

  /* update */
  @Override
  public void update(final Product product) {
    em.merge(product);
  }

  /* Delete */
  @Override
  public void delete(final Product product) {
    em.remove(product);
  }

  /* Select * FROM category WHERE id = ? */
  @Override
  public Product findById(final int id) {
    return em.find(Product.class, id);
  }

  @Override
  public List<Product> findBySaleId(final int bookId) {

    final CriteriaBuilder builder = em.getCriteriaBuilder();
    final CriteriaQuery<Product> query = builder.createQuery(Product.class);
    final Root<Book> root = query.from(Book.class);

    final Join<Book, Product> categoryJoin = root.join(Book_.categories);

    query.select(categoryJoin).where(builder.equal(root.get(Book_.id), bookId));

    return em.createQuery(query).getResultList();
  }

  /* SELECT * FROM category */
  @Override
  public List<Product> findAll() {

    final CriteriaBuilder builder = em.getCriteriaBuilder();
    final CriteriaQuery<Product> query = builder.createQuery(Product.class);
    final Root<Product> root = query.from(Product.class);

    query.select(root);

    return em.createQuery(query).getResultList();
  }
}
