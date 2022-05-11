package mx.dev.blank.web.request;

import java.io.Serializable;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor()
public class RankingRequest implements Serializable {
  private static final long serialVersionUID = 1L;

  @Max(5)
  private int score;

  @Min(1)
  private int bookId;
}
