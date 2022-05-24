package mx.dev.blank.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import mx.dev.blank.entity.Book;

@Getter
public class BookDTO implements Serializable {

  private static final long serialVersionUID = 1L;

  private final int id;
  private final String title;
  private final int pages;
  private final String isbn;
  private final BigDecimal price;
  private final String summary;
  private final String editorial;
  private final String releaseDate;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private Set<UserDTO> authors = new HashSet<>();

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private Set<CategoryDTO> categories = new HashSet<>();

  public BookDTO(final Book book) {
    this.id = book.getId();
    this.title = book.getTitle();
    this.pages = book.getPages();
    this.isbn = book.getIsbn();
    this.price = book.getPrice();
    this.summary = book.getSummary();
    this.editorial = book.getEditorial();
    this.releaseDate = new SimpleDateFormat("MM-dd-yyyy").format(book.getReleaseDate());
  }

  public void addAuthors(final Set<UserDTO> authors) {
    this.authors.addAll(authors);
  }

  public void addCategories(final Set<CategoryDTO> categories) {
    this.categories.addAll(categories);
  }
}
