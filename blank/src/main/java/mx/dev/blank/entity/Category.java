package mx.dev.blank.entity;

import java.io.Serializable;
import javax.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import mx.dev.blank.web.request.CategoryRequest;

@Entity
@Table(name = "category")
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private int id;

  @Column(name = "name", nullable = false, length = 50)
  private String name;

  public static Category newCategory(final String name) {
    return new Category(name);
  }

  public Category(final String name) {
    this.name = name;
  }

  public void update(final CategoryRequest request) {
    this.name = request.getName();
  }
}
