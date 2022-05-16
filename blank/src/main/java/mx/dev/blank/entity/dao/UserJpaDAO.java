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
import mx.dev.blank.entity.User;
import mx.dev.blank.entity.Sale_;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserJpaDAO implements UserDAO {

  @Setter(onMethod = @__(@PersistenceContext), value = AccessLevel.PACKAGE)
  private EntityManager em;

  /* create */
  @Override
  public void create(final User user) {
    em.persist(user);
  }

  /* update */
  @Override
  public void update(final User user) {
    em.merge(user);
  }

  /* Delete */
  @Override
  public void delete(final User user) {
    em.remove(user);
  }

  /* Select * FROM author WHERE id = ? */
  @Override
  public User findById(final int id) {
    return em.find(User.class, id);
  }

  @Override
  public List<User> findByBookId(final int bookId) {

    final CriteriaBuilder builder = em.getCriteriaBuilder();
    final CriteriaQuery<User> query = builder.createQuery(User.class);
    final Root<Sale> root = query.from(Sale.class);

    final Join<Sale, User> authorJoin = root.join(Sale_.users);

    query.select(authorJoin).where(builder.equal(root.get(Sale_.id), bookId));

    return em.createQuery(query).getResultList();
  }

  /* SELECT * FROM authors */
  @Override
  public List<User> findAll() {

    final CriteriaBuilder builder = em.getCriteriaBuilder();
    final CriteriaQuery<User> query = builder.createQuery(User.class);
    final Root<User> root = query.from(User.class);

    query.select(root);

    return em.createQuery(query).getResultList();
  }
}
