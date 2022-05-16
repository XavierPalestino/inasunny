package mx.dev.blank.entity.dao;

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
import mx.dev.blank.entity.Sale;
import mx.dev.blank.entity.Sale_;
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
  public List<Product> findBySaleId(final int saleID) {

    final CriteriaBuilder builder = em.getCriteriaBuilder();
    final CriteriaQuery<Product> query = builder.createQuery(Product.class);
    final Root<Sale> root = query.from(Sale.class);

    final Join<Sale, Product> categoryJoin = root.join(Sale_.products);

    query.select(categoryJoin).where(builder.equal(root.get(Sale_.id), saleID));

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
