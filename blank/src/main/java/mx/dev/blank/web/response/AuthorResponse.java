package mx.dev.blank.web.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import mx.dev.blank.model.AuthorDTO;

import java.util.List;

@RequiredArgsConstructor
@Getter
public class AuthorResponse {

    private final List<AuthorDTO> authors;
}
