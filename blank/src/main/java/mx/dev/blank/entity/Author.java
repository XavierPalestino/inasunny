package mx.dev.blank.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import mx.dev.blank.web.request.AuthorRequest;

@Entity
@Table(name = "author")
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Author implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private int id;

  @Column(name = "name", nullable = false, length = 50)
  private String name;

  @Column(name = "first_name", nullable = false, length = 50)
  private String firstName;

  @Column(name = "second_name", length = 50)
  private String secondName;

  @Column(name = "birthday")
  private Date birthday;

  public static Author newAuthor(
      final String name, final String firstName, final String secondName, final Date birthday) {
    return new Author(name, firstName, secondName, birthday);
  }

  private Author(
      final String name, final String firstName, final String secondName, final Date birthday) {
    this.name = name;
    this.firstName = firstName;
    this.secondName = secondName;
    this.birthday = birthday;
  }

  public void update(final AuthorRequest request) {
    this.name = request.getName();
    this.firstName = request.getFirstSurname();
    this.secondName = request.getSecondSurname();
    this.birthday = request.getBirthdate();
  }
}
