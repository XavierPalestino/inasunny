package mx.dev.blank.web.request;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor()
public class ProductRequest implements Serializable {
  private static final long serialVersionUID = 1L;

  @NotNull
  @Size(max = 255)
  private String productName;

  @NotNull
  @Size(max = 11)
  private int productCode;

  @NotNull
  @Size(max = 255)
  private String productBrand;

  @NotNull
  @Size(max = 255)
  private Date productExpirationDate;

  @NotNull
  @Size(max = 11)
  private int productPrice;

  @NotNull
  @Size(max = 11)
  private int productCost;

  @NotNull
  @Size(max = 11)
  private int productQuantity;
}
