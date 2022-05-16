package mx.dev.blank.service;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import mx.dev.blank.entity.dao.UserDAO;
import mx.dev.blank.entity.User;
import mx.dev.blank.exception.ResourceNotFoundException;
import mx.dev.blank.model.UserDTO;
import mx.dev.blank.web.request.UserRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserDAO userDAO;
  final SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy");

  /*CRUD*/

  @Transactional
  public void createAuthor(final UserRequest request) {
    final User user =
        User.newAuthor(
            request.getUsername(),
            request.getName(),
            request.getFirstSurname(),
            request.getSecondSurname(),
            request.getAddress(),
            request.getPhoneNumber(),
            request.getPassword());
    userDAO.create(user);
  }

  @Transactional
  public void updateAuthor(final int authorId, final UserRequest request) {

    final User user = userDAO.findById(authorId);
    if (user == null) {
      throw new ResourceNotFoundException("Author not found: " + authorId);
    }

    user.update(request);

    userDAO.update(user);
  }

  @Transactional
  public void deleteAuthor(final int authorId) {
    final User user = userDAO.findById(authorId);
    if (user == null) {
      throw new ResourceNotFoundException("Author not found: " + authorId);
    }

    userDAO.delete(user);
  }

  @Transactional(readOnly = true)
  public List<User> findByBookId(final int bookId) {
    return userDAO.findByBookId(bookId);
  }

  @Transactional(readOnly = true)
  public List<UserDTO> findAll() {
    return userDAO.findAll().stream().map(author -> {
      return new UserDTO(
              author.getId(),
              author.getUsername(),
              author.getName(),
              author.getFirstSurname(),
              author.getSecondSurname(),
              author.getAddress(),
              author.getPhoneNumber(),
              author.getPassword());
    }).collect(Collectors.toList());
  }
}
