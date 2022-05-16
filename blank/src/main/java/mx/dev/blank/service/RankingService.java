package mx.dev.blank.service;

import lombok.RequiredArgsConstructor;
import mx.dev.blank.entity.dao.SaleDAO;
import mx.dev.blank.entity.dao.RankingDAO;
import mx.dev.blank.entity.Sale;
import mx.dev.blank.entity.Ranking;
import mx.dev.blank.exception.ResourceNotFoundException;
import mx.dev.blank.web.request.RankingRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RankingService {

  private final RankingDAO rankingDAO;
  private final SaleDAO saleDAO;

  /*CRUD*/

  @Transactional
  public void createRanking(final RankingRequest request) {

    final Sale sale = saleDAO.findById(request.getBookId());
    if (sale == null) {
      throw new ResourceNotFoundException("Book not found: " + request.getBookId());
    }

    final Ranking ranking = Ranking.newRanking(request.getScore(), sale);
    rankingDAO.create(ranking);
  }
}
