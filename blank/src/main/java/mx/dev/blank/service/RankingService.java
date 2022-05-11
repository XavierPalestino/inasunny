package mx.dev.blank.service;

import lombok.RequiredArgsConstructor;
import mx.dev.blank.dao.BookDAO;
import mx.dev.blank.dao.RankingDAO;
import mx.dev.blank.entity.Book;
import mx.dev.blank.entity.Ranking;
import mx.dev.blank.exception.ResourceNotFoundException;
import mx.dev.blank.web.request.RankingRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RankingService {

  private final RankingDAO rankingDAO;
  private final BookDAO bookDAO;

  /*CRUD*/

  @Transactional
  public void createRanking(final RankingRequest request) {

    final Book book = bookDAO.findById(request.getBookId());
    if (book == null) {
      throw new ResourceNotFoundException("Book not found: " + request.getBookId());
    }

    final Ranking ranking = Ranking.newRanking(request.getScore(), book);
    rankingDAO.create(ranking);
  }
}
