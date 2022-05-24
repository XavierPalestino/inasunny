package mx.dev.blank.dao;

import java.util.List;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import mx.dev.blank.entity.Product;
import org.springframework.validation.annotation.Validated;

@Validated
public interface ProductDAO {

  void create(@NotNull Product product);

  void update(@NotNull Product product);

  void delete(@NotNull Product product);

  Product findById(@Min(1) int id);

  List<Product> findBySaleId(@Min(1) int bookId);

  List<Product> findAll();
}
