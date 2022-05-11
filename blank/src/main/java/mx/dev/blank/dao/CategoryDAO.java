package mx.dev.blank.dao;

import java.util.List;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import mx.dev.blank.entity.Category;
import org.springframework.validation.annotation.Validated;

@Validated
public interface CategoryDAO {

  void create(@NotNull Category category);

  void update(@NotNull Category category);

  void delete(@NotNull Category category);

  Category findById(@Min(1) int id);

  List<Category> findByBookId(@Min(1) int bookId);

  List<Category> findAll();
}
