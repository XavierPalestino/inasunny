package mx.dev.blank.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class BookRankingDTO {

  private final int bookId;
  private final String title;
  private final double ranking;
}
