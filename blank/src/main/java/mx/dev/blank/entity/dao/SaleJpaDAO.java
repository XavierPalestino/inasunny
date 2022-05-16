package mx.dev.blank.entity.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import mx.dev.blank.entity.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SaleJpaDAO implements SaleDAO {

  @Setter(onMethod = @__(@PersistenceContext), value = AccessLevel.PACKAGE)
  private EntityManager em;

  /* create */
  @Override
  public void create(final Sale sale) {
    em.persist(sale);
  }

  /* update */
  @Override
  public void update(final Sale sale) {
    em.merge(sale);
  }

  /* Delete */
  @Override
  public void softDelete(final Sale sale) {
    em.merge(sale);
  }

  /* Select * FROM books WHERE id = ? */
  @Override
  public Sale findById(final int id) {
    return em.find(Sale.class, id);
  }

  /* Select * FROM books WHERE id in ? */
  @Override
  public List<Sale> findByIds(final List<Integer> ids) {
    final CriteriaBuilder builder = em.getCriteriaBuilder();
    final CriteriaQuery<Sale> query = builder.createQuery(Sale.class);
    final Root<Sale> root = query.from(Sale.class);

    query.select(root).where(root.get(Sale_.id).in(ids));

    return em.createQuery(query).getResultList();
  }

  /* SELECT * FROM books ORDER BY ? ASC|DESC LIMIT ?, ?
   *
   * 1. Mostrar todos los libros ordenados por año de publicación ascendente, los resultados deberán de ser paginados. (quedando 2 libros por página)
   * 2. Mostrar libros descendentes por año de publicación. (quedando 2 libros por página)
   * 9. Filtrar libros ascendente y descendente por número de páginas que contiene el libro.
   *
   * */
  @Override
  public List<Sale> findBooks(
      final String sortField, final SortingOrder order, final Integer limit, final Integer offset) {
    final CriteriaBuilder builder = em.getCriteriaBuilder();
    final CriteriaQuery<Sale> query = builder.createQuery(Sale.class);
    final Root<Sale> root = query.from(Sale.class);

    if (StringUtils.isNotBlank(sortField)) {
      final Path<?> sortFieldValue = getSortField(root, sortField);
      if (SortingOrder.ASC == order) {
        query.orderBy(builder.asc(sortFieldValue));
      } else {
        query.orderBy(builder.desc(sortFieldValue));
      }
    }

    final TypedQuery<Sale> typedQuery = em.createQuery(query);

    if (offset != null) {
      typedQuery.setFirstResult(offset);
    }

    if (limit != null) {
      typedQuery.setMaxResults(limit);
    }

    return typedQuery.getResultList();
  }

  private Path<?> getSortField(final Root<Sale> root, final String sortField) {
    switch (sortField) {
      case "saleQuantity":
        return root.get(Sale_.saleQuantity);
      case "saleDate":
        return root.get(Sale_.releaseDate);
      default:
        return root.get(Sale_.id);
    }
  }

  /*
   * SELECT * from book
   * INNER JOIN book_author ON book_author.book_id = book.id
   * INNER JOIN author ON book_author.author_id = author.id
   * WHERE author.name = "author-1" OR author.first_name = "fn-1" OR author.second_name="sn-name";
   *
   * 3. Buscar aquellos libros donde participó X autor haciendo match ya sea con su nombre o apellidos
   *
   * */
  @Override
  public List<Sale> getBookByAuthor(final String author) {
    final CriteriaBuilder builder = em.getCriteriaBuilder();
    final CriteriaQuery<Sale> query = builder.createQuery(Sale.class);
    final Root<Sale> root = query.from(Sale.class);

    final Join<Sale, User> authorJoin = root.join(Sale_.users);
    query
        .select(root)
        .where(
            builder.or(
                builder.equal(authorJoin.get(User_.name), author),
                builder.equal(authorJoin.get(User_.firstSurname), author),
                builder.equal(authorJoin.get(User_.secondSurname), author)));

    return em.createQuery(query).getResultList();
  }

  /* SELECT  * FROM book WHERE price BETWEENbetween ? AND ?;
   *
   * 4. Donde el precio esté en el rango de precioMinimo a precioMáximo
   *
   * */
  @Override
  public List<Sale> getBooksByPrice(final BigDecimal priceMin, final BigDecimal priceMax) {

    final CriteriaBuilder builder = em.getCriteriaBuilder();
    final CriteriaQuery<Sale> query = builder.createQuery(Sale.class);
    final Root<Sale> root = query.from(Sale.class);

    return em.createQuery(query).getResultList();
  }

  /* SELECT book.* FROM book
   * INNER JOIN book_author ON book_author.book_id = book.id
   * INNER JOIN author ON book_author.author_id = author.id
   * GROUP BY book.id
   * HAVING Count(*) = ?
   *
   * 5. Dado un número, buscar aquellos libros donde contengan ese  número de autores.
   *
   * */

  @Override
  public List<Integer> getBooksByAmountAuthors(final long authors) {

    final CriteriaBuilder builder = em.getCriteriaBuilder();
    final CriteriaQuery<Integer> query = builder.createQuery(Integer.class);
    final Root<Sale> root = query.from(Sale.class);

    final Join<Sale, User> authorJoin = root.join(Sale_.users);

    query
        .select(root.get(Sale_.id))
        .groupBy(root.get(Sale_.id))
        .having(builder.equal(builder.count(authorJoin), authors));

    return em.createQuery(query).getResultList();
  }

  /* SELECT * FROM book WHERE date_publication BETWEEN ? AND ?;
   *
   * 6. Buscar aquellos libros en un rango de fechas (fechaInicial, fechaFinal) de publicación
   *
   * */

  @Override
  public List<Sale> getBooksByDate(final Date startDate, final Date endDate) {

    final CriteriaBuilder builder = em.getCriteriaBuilder();
    final CriteriaQuery<Sale> query = builder.createQuery(Sale.class);
    final Root<Sale> root = query.from(Sale.class);

    query.select(root).where(builder.between(root.get(Sale_.releaseDate), startDate, endDate));

    return em.createQuery(query).getResultList();
  }

  /* SELECT Count(*) FROM book
   *  INNER JOIN book_category ON book_category.book_id = book.id
   *  INNER JOIN category ON book_category.category_id = category.id
   *  WHERE category.name = "Arte";
   *
   * 7. Número de libros que existe de x categoría
   *
   */
  @Override
  public Long getAmountOfBooksByCategory(final String product) {
    final CriteriaBuilder builder = em.getCriteriaBuilder();
    final CriteriaQuery<Long> query = builder.createQuery(Long.class);
    final Root<Sale> root = query.from(Sale.class);
    final Join<Sale, Product> categoryJoin = root.join(Sale_.products);

    query
        .select(builder.count(root.get(Sale_.id)))
        .where(builder.equal(categoryJoin.get(Product_.productName), product));
    return em.createQuery(query).getSingleResult();
  }

  /* SELECT book.* FROM category
   * INNER JOIN book_category ON book_category.book_id = book.id
   * INNER JOIN category ON book_category.category_id = category.id
   * WHERE category.name = ?;
   *
   * 8. Número de libros que existe de x categoría
   *
   * */
  @Override
  public List<Sale> getBooksByCategory(final String product) {
    final CriteriaBuilder builder = em.getCriteriaBuilder();
    final CriteriaQuery<Sale> query = builder.createQuery(Sale.class);
    final Root<Sale> root = query.from(Sale.class);

    final Join<Sale, Product> bookJoinCategory = root.join(Sale_.products);

    query.select(root).where(builder.equal(bookJoinCategory.get(Product_.productName), product));

    return em.createQuery(query).getResultList();
  }

}
