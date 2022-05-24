package mx.dev.blank.model;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ProductDTO implements Serializable {

  private static final long serialVersionUID = 1L;

  private final int id;
  private final String productName;
  private final int productCode;
  private final String productBrand;
  private final Date productExpirationDate;
  private final int productPrice;
  private final int productCost;
  private final int productQuantity;
}
