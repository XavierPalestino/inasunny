package mx.dev.blank.entity.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import mx.dev.blank.entity.Sale;
import mx.dev.blank.entity.SortingOrder;
import org.springframework.validation.annotation.Validated;

@Validated
public interface SaleDAO {

  void create(@NotNull Sale sale);

  void update(@NotNull Sale sale);

  void softDelete(@NotNull Sale sale);

  Sale findById(@Min(1) int id);

  List<Sale> findByIds(@NotEmpty List<Integer> ids);

  // 1,2, 9
  List<Sale> findBooks(
      String sortField, SortingOrder order, @Min(1) Integer limit, @Min(0) Integer offset);

  // 3
  List<Sale> getBookByAuthor(@NotBlank String author);

  // 4
  List<Sale> getBooksByPrice(@NotNull BigDecimal priceMin, @NotNull BigDecimal priceMax);

  // 5
  List<Integer> getBooksByAmountAuthors(@Min(1) long amountAuthors);

  // 6
  List<Sale> getBooksByDate(@NotNull Date startDate, @NotNull Date endDate);

  // 7
  Long getAmountOfBooksByCategory(@NotBlank String category);

  // 8
  List<Sale> getBooksByCategory(@NotBlank String category);
}
