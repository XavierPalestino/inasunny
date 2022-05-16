package mx.dev.blank.web.request;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor()
public class SaleRequest implements Serializable {
  private static final long serialVersionUID = 1L;

  @Temporal(TemporalType.DATE)
  private Date saleDate;

  private int saleQuantity;

  @NotEmpty private Set<Integer> products;

  @NotEmpty private Set<Integer> users;
}
