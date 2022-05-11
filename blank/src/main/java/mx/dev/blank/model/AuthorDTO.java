package mx.dev.blank.model;

import java.io.Serializable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class AuthorDTO implements Serializable {

  private static final long serialVersionUID = 1L;

  private final int id;
  private final String name;
  private final String firstName;
  private final String secondName;
  private final String birthday;


}
