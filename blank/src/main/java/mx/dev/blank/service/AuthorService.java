package mx.dev.blank.service;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import mx.dev.blank.dao.AuthorDAO;
import mx.dev.blank.entity.Author;
import mx.dev.blank.exception.ResourceNotFoundException;
import mx.dev.blank.model.AuthorDTO;
import mx.dev.blank.web.request.AuthorRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthorService {

  private final AuthorDAO authorDAO;
  final SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy");

  /*CRUD*/

  @Transactional
  public void createAuthor(final AuthorRequest request) {
    final Author author =
        Author.newAuthor(
            request.getName(),
            request.getFirstSurname(),
            request.getSecondSurname(),
            request.getBirthdate());
    authorDAO.create(author);
  }

  @Transactional
  public void updateAuthor(final int authorId, final AuthorRequest request) {

    final Author author = authorDAO.findById(authorId);
    if (author == null) {
      throw new ResourceNotFoundException("Author not found: " + authorId);
    }

    author.update(request);

    authorDAO.update(author);
  }

  @Transactional
  public void deleteAuthor(final int authorId) {
    final Author author = authorDAO.findById(authorId);
    if (author == null) {
      throw new ResourceNotFoundException("Author not found: " + authorId);
    }

    authorDAO.delete(author);
  }

  @Transactional(readOnly = true)
  public List<Author> findByBookId(final int bookId) {
    return authorDAO.findByBookId(bookId);
  }

  @Transactional(readOnly = true)
  public List<AuthorDTO> findAll() {
    return authorDAO.findAll().stream().map(author -> {
      return new AuthorDTO(author.getId(), author.getName(), author.getFirstName(), author.getSecondName(),
              author.getBirthday() != null ? df.format(author.getBirthday()) : "Unknowed");
    }).collect(Collectors.toList());
  }
}
