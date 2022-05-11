package mx.dev.blank.service;

import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import mx.dev.blank.dao.CategoryDAO;
import mx.dev.blank.entity.Category;
import mx.dev.blank.exception.ResourceNotFoundException;
import mx.dev.blank.model.CategoryDTO;
import mx.dev.blank.web.request.CategoryRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategoryService {

  private final CategoryDAO categoryDAO;

  /*CRUD*/

  @Transactional
  public void createCategory(final CategoryRequest request) {

    final Category category = Category.newCategory(request.getName());
    categoryDAO.create(category);
  }

  @Transactional
  public void updateCategory(final int categoryId, final CategoryRequest request) {

    final Category category = categoryDAO.findById(categoryId);
    if (category == null) {
      throw new ResourceNotFoundException("Category not found: " + categoryId);
    }

    category.update(request);

    categoryDAO.update(category);
  }

  @Transactional
  public void deleteCategory(final int categoryId) {
    final Category category = categoryDAO.findById(categoryId);
    if (category == null) {
      throw new ResourceNotFoundException("Category not found: " + categoryId);
    }

    categoryDAO.delete(category);
  }

  @Transactional(readOnly = true)
  public List<Category> findByBookId(final int bookId) {
    return categoryDAO.findByBookId(bookId);
  }

  @Transactional(readOnly = true)
  public List<CategoryDTO> findAll() {
    return categoryDAO.findAll().stream().map(category -> {
      return new CategoryDTO(category.getId(), category.getName());
    }).collect(Collectors.toList());
  }
}
