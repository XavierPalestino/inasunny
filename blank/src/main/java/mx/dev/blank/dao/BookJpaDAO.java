package mx.dev.blank.dao;

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
import mx.dev.blank.model.BookRankingDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BookJpaDAO implements BookDAO {

  @Setter(onMethod = @__(@PersistenceContext), value = AccessLevel.PACKAGE)
  private EntityManager em;

  /* create */
  @Override
  public void create(final Book book) {
    em.persist(book);
  }

  /* update */
  @Override
  public void update(final Book book) {
    em.merge(book);
  }

  /* Delete */
  @Override
  public void softDelete(final Book book) {
    em.merge(book);
  }

  /* Select * FROM books WHERE id = ? */
  @Override
  public Book findById(final int id) {
    return em.find(Book.class, id);
  }

  /* Select * FROM books WHERE id in ? */
  @Override
  public List<Book> findByIds(final List<Integer> ids) {
    final CriteriaBuilder builder = em.getCriteriaBuilder();
    final CriteriaQuery<Book> query = builder.createQuery(Book.class);
    final Root<Book> root = query.from(Book.class);

    query.select(root).where(root.get(Book_.id).in(ids));

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
  public List<Book> findBooks(
      final String sortField, final SortingOrder order, final Integer limit, final Integer offset) {
    final CriteriaBuilder builder = em.getCriteriaBuilder();
    final CriteriaQuery<Book> query = builder.createQuery(Book.class);
    final Root<Book> root = query.from(Book.class);

    if (StringUtils.isNotBlank(sortField)) {
      final Path<?> sortFieldValue = getSortField(root, sortField);
      if (SortingOrder.ASC == order) {
        query.orderBy(builder.asc(sortFieldValue));
      } else {
        query.orderBy(builder.desc(sortFieldValue));
      }
    }

    final TypedQuery<Book> typedQuery = em.createQuery(query);

    if (offset != null) {
      typedQuery.setFirstResult(offset);
    }

    if (limit != null) {
      typedQuery.setMaxResults(limit);
    }

    return typedQuery.getResultList();
  }

  private Path<?> getSortField(final Root<Book> root, final String sortField) {
    switch (sortField) {
      case "title":
        return root.get(Book_.title);
      case "pages":
        return root.get(Book_.pages);
      case "releaseDate":
        return root.get(Book_.releaseDate);
      default:
        return root.get(Book_.id);
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
  public List<Book> getBookByAuthor(final String author) {
    final CriteriaBuilder builder = em.getCriteriaBuilder();
    final CriteriaQuery<Book> query = builder.createQuery(Book.class);
    final Root<Book> root = query.from(Book.class);

    final Join<Book, Author> authorJoin = root.join(Book_.authors);
    query
        .select(root)
        .where(
            builder.or(
                builder.equal(authorJoin.get(Author_.name), author),
                builder.equal(authorJoin.get(Author_.firstName), author),
                builder.equal(authorJoin.get(Author_.secondName), author)));

    return em.createQuery(query).getResultList();
  }

  /* SELECT  * FROM book WHERE price BETWEENbetween ? AND ?;
   *
   * 4. Donde el precio esté en el rango de precioMinimo a precioMáximo
   *
   * */
  @Override
  public List<Book> getBooksByPrice(final BigDecimal priceMin, final BigDecimal priceMax) {

    final CriteriaBuilder builder = em.getCriteriaBuilder();
    final CriteriaQuery<Book> query = builder.createQuery(Book.class);
    final Root<Book> root = query.from(Book.class);

    query.select(root).where(builder.between(root.get(Book_.price), priceMin, priceMax));

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
    final Root<Book> root = query.from(Book.class);

    final Join<Book, Author> authorJoin = root.join(Book_.authors);

    query
        .select(root.get(Book_.id))
        .groupBy(root.get(Book_.id))
        .having(builder.equal(builder.count(authorJoin), authors));

    return em.createQuery(query).getResultList();
  }

  /* SELECT * FROM book WHERE date_publication BETWEEN ? AND ?;
   *
   * 6. Buscar aquellos libros en un rango de fechas (fechaInicial, fechaFinal) de publicación
   *
   * */

  @Override
  public List<Book> getBooksByDate(final Date startDate, final Date endDate) {

    final CriteriaBuilder builder = em.getCriteriaBuilder();
    final CriteriaQuery<Book> query = builder.createQuery(Book.class);
    final Root<Book> root = query.from(Book.class);

    query.select(root).where(builder.between(root.get(Book_.releaseDate), startDate, endDate));

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
  public Long getAmountOfBooksByCategory(final String category) {
    final CriteriaBuilder builder = em.getCriteriaBuilder();
    final CriteriaQuery<Long> query = builder.createQuery(Long.class);
    final Root<Book> root = query.from(Book.class);
    final Join<Book, Category> categoryJoin = root.join(Book_.categories);

    query
        .select(builder.count(root.get(Book_.id)))
        .where(builder.equal(categoryJoin.get(Category_.name), category));
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
  public List<Book> getBooksByCategory(final String category) {
    final CriteriaBuilder builder = em.getCriteriaBuilder();
    final CriteriaQuery<Book> query = builder.createQuery(Book.class);
    final Root<Book> root = query.from(Book.class);

    final Join<Book, Category> bookJoinCategory = root.join(Book_.categories);

    query.select(root).where(builder.equal(bookJoinCategory.get(Category_.name), category));

    return em.createQuery(query).getResultList();
  }

  /* SELECT book.id, book.title, AVG(ranking.score) FROM ranking
   * INNER JOIN book ON ranking.book_id = book.id
   *
   * Extra Ranking by book
   *
   * */

  @Override
  public List<BookRankingDTO> getRankings(final Integer limit, final Integer offset) {
    final CriteriaBuilder builder = em.getCriteriaBuilder();
    final CriteriaQuery<BookRankingDTO> query = builder.createQuery(BookRankingDTO.class);
    final Root<Ranking> root = query.from(Ranking.class);
    final Join<Ranking, Book> bookJoin = root.join(Ranking_.book);

    query
        .multiselect(
            bookJoin.get(Book_.id),
            bookJoin.get(Book_.title),
            builder.avg(root.get(Ranking_.score)))
        .groupBy(bookJoin.get(Book_.id), bookJoin.get(Book_.title));

    final TypedQuery<BookRankingDTO> typedQuery = em.createQuery(query);

    if (offset != null) {
      typedQuery.setFirstResult(offset);
    }

    if (limit != null) {
      typedQuery.setMaxResults(limit);
    }

    return typedQuery.getResultList();
  }
}
