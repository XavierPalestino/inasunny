package mx.dev.blank.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import mx.dev.blank.web.request.SaleRequest;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "sale")
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
/*Soft delete*/
@Where(clause = "deleted = false")
public class Sale implements Serializable {

  private static final long serialVersionUID = 1L;

  private Sale(
      final Date saleDate,
      final int saleQuantity,
      final Set<Product> products,
      final Set<User> users) {
    this.releaseDate = saleDate;
    this.saleQuantity = saleQuantity;
    this.products.addAll(products);
    this.users.addAll(users);
  }

  public static Sale createNewSale(
      final Date saleDate,
      final int saleQuantity,
      final Set<Product> products,
      final Set<User> users) {
    return new Sale(
            saleDate,
            saleQuantity,
            products,
            users);
  }

  public void update(
          final SaleRequest request,
          final Set<Product> products,
          final Set<User> users) {
    this.releaseDate = request.getSaleDate();
    this.saleQuantity = request.getSaleQuantity();
    this.products.clear();
    this.products.addAll(products);
    this.users.clear();
    this.users.addAll(users);
  }

  public void markAsDeleted() {
    this.deleted = true;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private int id;

  @Column(name = "sale_date", nullable = false)
  private Date releaseDate;

  @Column(name = "sale_quantity", nullable = false)
  private int saleQuantity;

  @Column(name = "deleted", nullable = false)
  private boolean deleted;

  @ManyToMany
  @JoinTable(
      name = "book_category",
      joinColumns = {@JoinColumn(name = "book_id", nullable = false, updatable = false)},
      inverseJoinColumns = {@JoinColumn(name = "category_id", nullable = false, updatable = false)})
  private final Set<Product> products = new HashSet<>();

  @ManyToMany
  @JoinTable(
      name = "book_author",
      joinColumns = {@JoinColumn(name = "book_id", nullable = false, updatable = false)},
      inverseJoinColumns = {@JoinColumn(name = "author_id", nullable = false, updatable = false)})
  private final Set<User> users = new HashSet<>();
}
