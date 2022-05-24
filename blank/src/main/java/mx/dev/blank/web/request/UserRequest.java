package mx.dev.blank.web.request;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor()
public class UserRequest implements Serializable {
  private static final long serialVersionUID = 1L;

  @NotNull
  @Size(max = 255)
  private String username;

  @NotNull
  @Size(max = 255)
  private String name;


  @NotNull
  @Size(max = 11)
  private int phoneNumber;

  @NotNull
  @Size(min = 8, max = 18)
  private String password;
}
