package mx.dev.blank.web.controller;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import mx.dev.blank.model.UserDTO;
import mx.dev.blank.service.UserService;
import mx.dev.blank.web.request.UserRequest;
import mx.dev.blank.web.response.AuthorResponse;
import mx.dev.blank.web.response.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/author")
public class UserRestController {

  private final UserService userService;

  @CrossOrigin(origins = "http://localhost:8080")
  @PostMapping
  public ResponseEntity<BaseResponse> createAuthor(
          @Valid @RequestBody final UserRequest userRequest, final BindingResult errors) {

    if (errors.hasErrors()) {
      return ResponseEntity.badRequest().body(BaseResponse.fail(errors.getAllErrors()));
    }

    userService.createAuthor(userRequest);

    return ResponseEntity.ok(BaseResponse.success("Author created successfully"));
  }

  @CrossOrigin(origins = "http://localhost:8080")
  @PutMapping(path = "/{authorId}")
  public ResponseEntity<BaseResponse> updateAuthor(
      @PathVariable(name = "authorId") final int authorId,
      @Valid @RequestBody final UserRequest userRequest,
      final BindingResult errors) {

    if (errors.hasErrors()) {
      return ResponseEntity.badRequest().body(BaseResponse.fail(errors.getAllErrors()));
    }

    userService.updateAuthor(authorId, userRequest);

    return ResponseEntity.ok(BaseResponse.success("Author updated successfully"));
  }

  @CrossOrigin(origins = "http://localhost:8080")
  @DeleteMapping(path = "/{authorId}")
  public ResponseEntity<BaseResponse> deleteAuthor(
      @PathVariable(name = "authorId") final int authorId) {

    userService.deleteAuthor(authorId);
    return ResponseEntity.ok(BaseResponse.success("Author deleted successfully"));
  }

  @CrossOrigin(origins = "http://localhost:8080")
  @GetMapping(path = "/list")
  public ResponseEntity<AuthorResponse> getAuthors() {

    final List<UserDTO> retrieved = userService.findAll();

    return ResponseEntity.ok(new AuthorResponse(retrieved));
  }
}
