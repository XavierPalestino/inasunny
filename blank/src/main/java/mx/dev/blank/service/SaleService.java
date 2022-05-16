package mx.dev.blank.service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import mx.dev.blank.entity.dao.UserDAO;
import mx.dev.blank.entity.dao.SaleDAO;
import mx.dev.blank.entity.dao.ProductDAO;
import mx.dev.blank.entity.Product;
import mx.dev.blank.entity.Sale;
import mx.dev.blank.entity.User;
import mx.dev.blank.exception.ResourceNotFoundException;
import mx.dev.blank.web.request.SaleRequest;
import mx.dev.blank.web.request.BookSearchForm;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SaleService {

  private final SaleDAO saleDAO;
  private final UserDAO userDAO;
  private final ProductDAO productDAO;

  /*CRUD*/

  @Transactional
  public Sale createBook(final SaleRequest request) {

    final Set<Product> products = getProducts(request.getProducts());
    final Set<User> users = getAuthors(request.getUsers());

    final Sale sale =
        Sale.createNewSale(
                request.getSaleDate(),
                request.getSaleQuantity(),
                products,
                users);

    saleDAO.create(sale);
    return sale;
  }

  @Transactional
  public void updateBook(final int bookId, final SaleRequest request) {

    final Sale sale = saleDAO.findById(bookId);
    if (sale == null) {
      throw new ResourceNotFoundException("Book not found: " + bookId);
    }

    final Set<Product> categories = getProducts(request.getProducts());
    final Set<User> users = getAuthors(request.getUsers());

    sale.update(request, categories, users);

    saleDAO.update(sale);
  }

  @Transactional
  public void deleteBook(final int bookId) {
    final Sale sale = saleDAO.findById(bookId);
    if (sale == null) {
      throw new ResourceNotFoundException("Book not found: " + bookId);
    }

    sale.markAsDeleted();
    saleDAO.softDelete(sale);
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

  private Set<Product> getProducts(final Set<Integer> productsIDs) {
    final Set<Product> products = new HashSet<>();
    productsIDs.forEach(
        productID -> {
          final Product product = productDAO.findById(productID);
          if (product == null) {
            throw new ResourceNotFoundException("Category not found: " + productID);
          }
          products.add(product);
        });

    return products;
  }

  @Transactional(readOnly = true)
  public List<Sale> getBooks(final BookSearchForm form) {
    return saleDAO.findBooks(
        form.getSortField(), form.getSortingOrder(), form.getLimit(), form.getOffset());
  }

  @Transactional(readOnly = true)
  public List<Sale> getBooksByAuthor(final String author) {
    return saleDAO.getBookByAuthor(author);
  }

  @Transactional(readOnly = true)
  public List<Sale> getBooksByPrice(final BigDecimal priceMin, final BigDecimal priceMax) {
    return saleDAO.getBooksByPrice(priceMin, priceMax);
  }

  @SuppressWarnings("unchecked")
  @Transactional(readOnly = true)
  public List<Sale> getBooksByAmountAuthors(final long authors) {
    final List<Integer> ids = saleDAO.getBooksByAmountAuthors(authors);
    if (ids.isEmpty()) {
      return Collections.EMPTY_LIST;
    }

    return saleDAO.findByIds(ids);
  }

  @Transactional(readOnly = true)
  public List<Sale> getBooksByDate(final Date startDate, final Date endDate) {
    return saleDAO.getBooksByDate(startDate, endDate);
  }

  @Transactional(readOnly = true)
  public Long getAmountOfBooksByCategory(final String category) {
    return saleDAO.getAmountOfBooksByCategory(category);
  }

  @Transactional(readOnly = true)
  public List<Sale> getBooksByCategory(final String category) {
    return saleDAO.getBooksByCategory(category);
  }

  @Transactional(readOnly = true)
  public User findByAuthorId(final int authorId) {
    return userDAO.findById(authorId);
  }

  @Transactional(readOnly = true)
  public Sale findByBookId(final int bookId) {
    return saleDAO.findById(bookId);
  }
}
