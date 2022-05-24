package mx.dev.blank.web.controller;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import mx.dev.blank.entity.User;
import mx.dev.blank.entity.Book;
import mx.dev.blank.entity.Category;
import mx.dev.blank.model.UserDTO;
import mx.dev.blank.model.BookDTO;
import mx.dev.blank.model.CategoryDTO;
import mx.dev.blank.service.UserService;
import mx.dev.blank.service.CategoryService;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
public class BookResourceAssembler {

  @Component
  @RequiredArgsConstructor
  public static class Factory {

    private final UserService userService;
    private final CategoryService categoryService;

    public BookResourceAssembler create(final List<String> expand) {
      return new BookResourceAssembler(userService, categoryService, expand);
    }
  }

  private final UserService userService;
  private final CategoryService categoryService;
  private final List<String> expand;

  public List<BookDTO> toDto(final List<Book> books) {
    return books.stream().map(this::toDto).collect(Collectors.toList());
  }

  public BookDTO toDto(final Book book) {
    final BookDTO dto = new BookDTO(book);

    if (expand.contains(AUTHOR_EXPAND)) {
      final List<User> users = userService.findByBookId(book.getId());
      final SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy");
      dto.addAuthors(
          users.stream()
              .map(
                  author -> {
                    return new UserDTO(
                        author.getId(),
                        author.getUsername(),
                        author.getName(),
                        author.getFirstSurname(),
                        author.getSecondSurname(),
                        author.getAddress(),
                        author.getPhoneNumber(),
                        author.getPassword());
                  })
              .collect(Collectors.toSet()));
    }

    if (expand.contains(CATEGORY_EXPAND)) {
      final List<Category> categories = categoryService.findByBookId(book.getId());
      dto.addCategories(
          categories.stream()
              .map(
                  category -> {
                    return new CategoryDTO(category.getId(), category.getName());
                  })
              .collect(Collectors.toSet()));
    }

    return dto;
  }

  private static final String AUTHOR_EXPAND = "authors";
  private static final String CATEGORY_EXPAND = "categories";
}
