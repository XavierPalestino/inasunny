package mx.dev.blank.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import mx.dev.blank.entity.Ranking;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RankingJpaDAO implements RankingDAO {

  @Setter(onMethod = @__(@PersistenceContext), value = AccessLevel.PACKAGE)
  private EntityManager em;

  /* create */
  @Override
  public void create(final Ranking ranking) {
    em.persist(ranking);
  }
}
