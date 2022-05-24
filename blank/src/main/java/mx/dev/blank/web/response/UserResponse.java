package mx.dev.blank.web.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import mx.dev.blank.model.UserDTO;

import java.util.List;

@RequiredArgsConstructor
@Getter
public class UserResponse {

    private final List<UserDTO> authors;
}
