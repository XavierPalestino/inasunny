package mx.dev.blank.service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import mx.dev.blank.dao.UserDAO;
import mx.dev.blank.dao.BookDAO;
import mx.dev.blank.dao.CategoryDAO;
import mx.dev.blank.entity.User;
import mx.dev.blank.entity.Book;
import mx.dev.blank.entity.Category;
import mx.dev.blank.exception.ResourceNotFoundException;
import mx.dev.blank.model.BookRankingDTO;
import mx.dev.blank.web.request.BookRequest;
import mx.dev.blank.web.request.BookSearchForm;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookService {

  private final BookDAO bookDAO;
  private final UserDAO userDAO;
  private final CategoryDAO categoryDAO;

  /*CRUD*/

  @Transactional
  public Book createBook(final BookRequest request) {

    final Set<Category> categories = getCategories(request.getCategories());
    final Set<User> users = getAuthors(request.getAuthors());

    final Book book =
        Book.createNewBook(
            request.getTitle(),
            request.getPages(),
            request.getIsbn(),
            request.getPrice(),
            request.getSummary(),
            request.getEditorial(),
            request.getDatePublication(),
            categories,
                users);

    bookDAO.create(book);
    return book;
  }

  @Transactional
  public void updateBook(final int bookId, final BookRequest request) {

    final Book book = bookDAO.findById(bookId);
    if (book == null) {
      throw new ResourceNotFoundException("Book not found: " + bookId);
    }

    final Set<Category> categories = getCategories(request.getCategories());
    final Set<User> users = getAuthors(request.getAuthors());

    book.update(request, categories, users);

    bookDAO.update(book);
  }

  @Transactional
  public void deleteBook(final int bookId) {
    final Book book = bookDAO.findById(bookId);
    if (book == null) {
      throw new ResourceNotFoundException("Book not found: " + bookId);
    }

    book.markAsDeleted();
    bookDAO.softDelete(book);
  }

  private Set<User> getAuthors(final Set<Integer> authorIds) {
    final Set<User> users = new HashSet<>();
    authorIds.forEach(
        authorId -> {
          final User user = userDAO.findById(authorId);
          if (user == null) {
            throw new ResourceNotFoundException("Author not found: " + authorId);
          }
          users.add(user);
        });

    return users;
  }

  private Set<Category> getCategories(final Set<Integer> categoryIds) {
    final Set<Category> categories = new HashSet<>();
    categoryIds.forEach(
        categoryId -> {
          final Category category = categoryDAO.findById(categoryId);
          if (category == null) {
            throw new ResourceNotFoundException("Category not found: " + categoryId);
          }
          categories.add(category);
        });

    return categories;
  }

  @Transactional(readOnly = true)
  public List<Book> getBooks(final BookSearchForm form) {
    return bookDAO.findBooks(
        form.getSortField(), form.getSortingOrder(), form.getLimit(), form.getOffset());
  }

  @Transactional(readOnly = true)
  public List<Book> getBooksByAuthor(final String author) {
    return bookDAO.getBookByAuthor(author);
  }

  @Transactional(readOnly = true)
  public List<Book> getBooksByPrice(final BigDecimal priceMin, final BigDecimal priceMax) {
    return bookDAO.getBooksByPrice(priceMin, priceMax);
  }

  @SuppressWarnings("unchecked")
  @Transactional(readOnly = true)
  public List<Book> getBooksByAmountAuthors(final long authors) {
    final List<Integer> ids = bookDAO.getBooksByAmountAuthors(authors);
    if (ids.isEmpty()) {
      return Collections.EMPTY_LIST;
    }

    return bookDAO.findByIds(ids);
  }

  @Transactional(readOnly = true)
  public List<Book> getBooksByDate(final Date startDate, final Date endDate) {
    return bookDAO.getBooksByDate(startDate, endDate);
  }

  @Transactional(readOnly = true)
  public Long getAmountOfBooksByCategory(final String category) {
    return bookDAO.getAmountOfBooksByCategory(category);
  }

  @Transactional(readOnly = true)
  public List<Book> getBooksByCategory(final String category) {
    return bookDAO.getBooksByCategory(category);
  }

  @Transactional(readOnly = true)
  public List<BookRankingDTO> getBooksWithScore(final Integer limit, final Integer offset) {
    return bookDAO.getRankings(limit, offset);
  }

  @Transactional(readOnly = true)
  public User findByAuthorId(final int authorId) {
    return userDAO.findById(authorId);
  }

  @Transactional(readOnly = true)
  public Book findByBookId(final int bookId) {
    return bookDAO.findById(bookId);
  }
}
