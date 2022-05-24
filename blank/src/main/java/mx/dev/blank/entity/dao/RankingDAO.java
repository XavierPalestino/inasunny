package mx.dev.blank.entity.dao;

import javax.validation.constraints.NotNull;
import mx.dev.blank.entity.Ranking;
import org.springframework.validation.annotation.Validated;

@Validated
public interface RankingDAO {

  void create(@NotNull Ranking ranking);
}
