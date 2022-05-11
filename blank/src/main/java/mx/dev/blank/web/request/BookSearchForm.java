package mx.dev.blank.web.request;

import lombok.Getter;
import lombok.Setter;
import mx.dev.blank.entity.SortingOrder;

@Getter
@Setter
public class BookSearchForm {

  public String sortField;
  public SortingOrder sortingOrder = SortingOrder.ASC;
  public Integer limit;
  public Integer offset;
}
