package mx.dev.blank.entity.dao;

import java.util.List;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import mx.dev.blank.entity.User;
import org.springframework.validation.annotation.Validated;

@Validated
public interface UserDAO {

  void create(@NotNull User user);

  void update(@NotNull User user);

  void delete(@NotNull User user);

  User findById(@Min(1) int id);

  List<User> findByBookId(@Min(1) int bookId);

  List<User> findAll();
}
