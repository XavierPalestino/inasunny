package mx.dev.blank.entity;

import java.io.Serializable;
import javax.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import mx.dev.blank.web.request.UserRequest;

@Entity
@Table(name = "user")
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private int id;

  @Column(name = "username", nullable = false, length = 50)
  private String username;

  @Column(name = "name", nullable = false, length = 50)
  private String name;

  @Column(name = "first_surname", nullable = false, length = 50)
  private String firstSurname;

  @Column(name = "second_surname", length = 50)
  private String secondSurname;

  @Column(name = "address", nullable = false, length = 50)
  private String address;

  @Column(name = "phone_number", nullable = false, length = 11)
  private int phoneNumber;

  @Column(name = "password", nullable = false, length = 255)
  private String password;

  public static User newAuthor(
      final String username,
      final String name,
      final String firstSurname,
      final String secondSurname,
      final String address,
      final int phoneNumber,
      final String password) {
    return new User(
            username,
            name,
            firstSurname,
            secondSurname,
            address,
            phoneNumber,
            password);
  }

  private User(
          final String username,
          final String name,
          final String firstSurname,
          final String secondSurname,
          final String address,
          final int phoneNumber,
          final String password) {
    this.username = username;
    this.name = name;
    this.firstSurname = firstSurname;
    this.secondSurname = secondSurname;
    this.address = address;
    this.phoneNumber = phoneNumber;
    this.password = password;
  }

  public void update(final UserRequest request) {
    this.username = request.getUsername();
    this.name = request.getName();
    this.firstSurname = request.getFirstSurname();
    this.secondSurname = request.getSecondSurname();
    this.address = request.getAddress();
    this.phoneNumber = request.getPhoneNumber();
    this.password = request.getPassword();
  }
}
