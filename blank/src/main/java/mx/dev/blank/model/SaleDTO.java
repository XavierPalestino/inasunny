package mx.dev.blank.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import mx.dev.blank.entity.Sale;

@Getter
public class SaleDTO implements Serializable {

  private static final long serialVersionUID = 1L;

  private final int id;
  private final String saleDate;
  private final int saleQuantity;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private Set<UserDTO> userDTOS = new HashSet<>();

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private Set<ProductDTO> productDTOS = new HashSet<>();

  public SaleDTO(final Sale sale) {
    this.id = sale.getId();
    this.saleDate = new SimpleDateFormat("MM-dd-yyyy").format(sale.getReleaseDate());
    this.saleQuantity = sale.getSaleQuantity();
  }

  public void addUsers(final Set<UserDTO> users) {
    this.userDTOS.addAll(users);
  }

  public void addProducts(final Set<ProductDTO> productDTOS) {
    this.productDTOS.addAll(productDTOS);
  }
}
