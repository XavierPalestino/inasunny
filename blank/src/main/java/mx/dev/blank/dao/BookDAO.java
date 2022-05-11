package mx.dev.blank.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import mx.dev.blank.entity.Book;
import mx.dev.blank.entity.SortingOrder;
import mx.dev.blank.model.BookRankingDTO;
import org.springframework.validation.annotation.Validated;

@Validated
public interface BookDAO {

  void create(@NotNull Book book);

  void update(@NotNull Book book);

  void softDelete(@NotNull Book book);

  Book findById(@Min(1) int id);

  List<Book> findByIds(@NotEmpty List<Integer> ids);

  // 1,2, 9
  List<Book> findBooks(
      String sortField, SortingOrder order, @Min(1) Integer limit, @Min(0) Integer offset);

  // 3
  List<Book> getBookByAuthor(@NotBlank String author);

  // 4
  List<Book> getBooksByPrice(@NotNull BigDecimal priceMin, @NotNull BigDecimal priceMax);

  // 5
  List<Integer> getBooksByAmountAuthors(@Min(1) long amountAuthors);

  // 6
  List<Book> getBooksByDate(@NotNull Date startDate, @NotNull Date endDate);

  // 7
  Long getAmountOfBooksByCategory(@NotBlank String category);

  // 8
  List<Book> getBooksByCategory(@NotBlank String category);

  List<BookRankingDTO> getRankings(@Min(1) final Integer limit, @Min(0) final Integer offset);
}
