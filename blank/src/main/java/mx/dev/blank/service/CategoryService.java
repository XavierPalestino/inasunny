package mx.dev.blank.service;

import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import mx.dev.blank.dao.ProductDAO;
import mx.dev.blank.entity.Product;
import mx.dev.blank.exception.ResourceNotFoundException;
import mx.dev.blank.model.ProductDTO;
import mx.dev.blank.web.request.ProductRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategoryService {

  private final ProductDAO productDAO;

  /*CRUD*/

  @Transactional
  public void createCategory(final ProductRequest request) {

    final Product product = Product.newProduct(
            request.getProductName(),
            request.getProductCode(),
            request.getProductBrand(),
            request.getProductExpirationDate(),
            request.getProductPrice(),
            request.getProductCost(),
            request.getProductQuantity());
    productDAO.create(product);
  }

  @Transactional
  public void updateCategory(final int productId, final ProductRequest request) {

    final Product product = productDAO.findById(productId);
    if (product == null) {
      throw new ResourceNotFoundException("Product not found: " + productId);
    }

    product.update(request);

    productDAO.update(product);
  }

  @Transactional
  public void deleteCategory(final int categoryId) {
    final Product product = productDAO.findById(categoryId);
    if (product == null) {
      throw new ResourceNotFoundException("Product not found: " + categoryId);
    }

    productDAO.delete(product);
  }

  @Transactional(readOnly = true)
  public List<Product> findByBookId(final int bookId) {
    return productDAO.findBySaleId(bookId);
  }

  @Transactional(readOnly = true)
  public List<ProductDTO> findAll() {
    return productDAO.findAll()
            .stream()
            .map(product -> new ProductDTO(
                  product.getId(),
                  product.getProductName(),
                  product.getProductCode(),
                  product.getProductBrand(),
                  product.getProductExpirationDate(),
                  product.getProductPrice(),
                  product.getProductCost(),
                  product.getProductQuantity()))
            .collect(Collectors.toList());
  }
}
