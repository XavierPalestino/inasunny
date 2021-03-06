package mx.dev.blank.entity;

import java.io.Serializable;
import javax.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "ranking")
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Ranking implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private int id;

  @Column(name = "score", nullable = false)
  private int score;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "book_id", nullable = false)
  private Sale sale;

  @Column(name = "book_id", updatable = false, insertable = false)
  private int bookId;

  public static Ranking newRanking(final int score, final Sale sale) {
    return new Ranking(score, sale);
  }

  private Ranking(final int score, final Sale sale) {
    this.score = score;
    this.sale = sale;
    this.bookId = sale.getId();
  }
}
