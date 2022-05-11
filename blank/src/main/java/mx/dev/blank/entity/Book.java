package mx.dev.blank.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import mx.dev.blank.web.request.BookRequest;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "book")
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
/*Soft delete*/
@Where(clause = "deleted = false")
public class Book implements Serializable {

  private static final long serialVersionUID = 1L;

  private Book(
      final String title,
      final int pages,
      final String isbn,
      final BigDecimal price,
      final String summary,
      final String editorial,
      final Date releaseDate,
      final Set<Category> categories,
      final Set<Author> authors) {
    this.title = title;
    this.pages = pages;
    this.isbn = isbn;
    this.price = price;
    this.summary = summary;
    this.editorial = editorial;
    this.releaseDate = releaseDate;
    this.categories.addAll(categories);
    this.authors.addAll(authors);
  }

  public static Book createNewBook(
      final String title,
      final int pages,
      final String isbn,
      final BigDecimal price,
      final String summary,
      final String editorial,
      final Date datePublication,
      final Set<Category> categories,
      final Set<Author> authors) {
    return new Book(
        title, pages, isbn, price, summary, editorial, datePublication, categories, authors);
  }

  public void update(
      final BookRequest request, final Set<Category> categories, final Set<Author> authors) {
    this.title = request.getTitle();
    this.pages = request.getPages();
    this.isbn = request.getIsbn();
    this.price = request.getPrice();
    this.summary = request.getSummary();
    this.editorial = request.getEditorial();
    this.releaseDate = request.getDatePublication();
    this.categories.clear();
    this.categories.addAll(categories);
    this.authors.clear();
    this.authors.addAll(authors);
  }

  public void markAsDeleted() {
    this.deleted = true;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private int id;

  @Column(name = "title", nullable = false, length = 50)
  private String title;

  @Column(name = "pages", nullable = false)
  private int pages;

  @Column(name = "isbn", nullable = false, length = 13)
  private String isbn;

  @Column(name = "price", nullable = false, scale = 2, precision = 8)
  private BigDecimal price;

  @Column(name = "summary", nullable = false, length = 350)
  private String summary;

  @Column(name = "editorial", nullable = false, length = 35)
  private String editorial;

  @Column(name = "release_date", nullable = false)
  private Date releaseDate;

  @Column(name = "deleted", nullable = false)
  private boolean deleted;

  @ManyToMany
  @JoinTable(
      name = "book_category",
      joinColumns = {@JoinColumn(name = "book_id", nullable = false, updatable = false)},
      inverseJoinColumns = {@JoinColumn(name = "category_id", nullable = false, updatable = false)})
  private final Set<Category> categories = new HashSet<>();

  @ManyToMany
  @JoinTable(
      name = "book_author",
      joinColumns = {@JoinColumn(name = "book_id", nullable = false, updatable = false)},
      inverseJoinColumns = {@JoinColumn(name = "author_id", nullable = false, updatable = false)})
  private final Set<Author> authors = new HashSet<>();
}
