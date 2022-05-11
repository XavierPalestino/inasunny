package mx.dev.blank.web.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.ObjectError;

@Setter
@Getter
public class BaseResponse {

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private List<ObjectError> fieldErrors;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String message;

  public static BaseResponse success(final String message) {
    final BaseResponse response = new BaseResponse();
    response.setMessage(message);
    return response;
  }

  public static BaseResponse fail(final List<ObjectError> fieldErrors) {
    final BaseResponse response = new BaseResponse();
    response.setFieldErrors(fieldErrors);
    return response;
  }
}
