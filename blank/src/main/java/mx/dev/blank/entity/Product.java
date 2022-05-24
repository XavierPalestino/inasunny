package mx.dev.blank.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

import lombok.*;
import mx.dev.blank.web.request.ProductRequest;

@Entity
@Table(name = "product")
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private int id;

  @Column(name = "product_name", nullable = false, length = 50)
  private String productName;

  @Column(name = "product_code", nullable = false, length = 11)
  private int productCode;

  @Column(name = "product_brand", nullable = false, length = 50)
  private String productBrand;

  @Column(name = "product_expiration_date", nullable = false, length = 50)
  private Date productExpirationDate;

  @Column(name = "product_price", nullable = false, length = 11)
  private int productPrice;

  @Column(name = "product_cost", nullable = false, length = 11)
  private int productCost;

  @Column(name = "product_quantity", nullable = false, length = 50)
  private int productQuantity;

  public static Product newProduct(
          final String productName,
          final int productCode,
          final String productBrand,
          final Date productExpirationDate,
          final int productPrice,
          final int productCost,
          final int productQuantity) {
    return new Product(
            productName,
            productCode,
            productBrand,
            productExpirationDate,
            productPrice,
            productCost,
            productQuantity);
  }

  public Product(
          final String productName,
          final int productCode,
          final String productBrand,
          final Date productExpirationDate,
          final int productPrice,
          final int productCost,
          final int productQuantity
          ) {
    this.productName = productName;
    this.productCode = productCode;
    this.productBrand = productBrand;
    this.productExpirationDate = productExpirationDate;
    this.productPrice = productPrice;
    this.productCost = productCost;
    this.productQuantity = productQuantity;
  }

  public void update(final ProductRequest request) {
    this.productName = request.getProductName();
    this.productCode = request.getProductCode();
    this.productBrand = request.getProductBrand();
    this.productExpirationDate = request.getProductExpirationDate();
    this.productPrice = request.getProductPrice();
    this.productCost = request.getProductCost();
    this.productQuantity = request.getProductQuantity();
  }
}
