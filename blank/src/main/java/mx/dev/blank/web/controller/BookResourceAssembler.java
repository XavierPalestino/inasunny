package mx.dev.blank.web.controller;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import mx.dev.blank.entity.Author;
import mx.dev.blank.entity.Book;
import mx.dev.blank.entity.Category;
import mx.dev.blank.model.AuthorDTO;
import mx.dev.blank.model.BookDTO;
import mx.dev.blank.model.CategoryDTO;
import mx.dev.blank.service.AuthorService;
import mx.dev.blank.service.CategoryService;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
public class BookResourceAssembler {

  @Component
  @RequiredArgsConstructor
  public static class Factory {

    private final AuthorService authorService;
    private final CategoryService categoryService;

    public BookResourceAssembler create(final List<String> expand) {
      return new BookResourceAssembler(authorService, categoryService, expand);
    }
  }

  private final AuthorService authorService;
  private final CategoryService categoryService;
  private final List<String> expand;

  public List<BookDTO> toDto(final List<Book> books) {
    return books.stream().map(this::toDto).collect(Collectors.toList());
  }

  public BookDTO toDto(final Book book) {
    final BookDTO dto = new BookDTO(book);

    if (expand.contains(AUTHOR_EXPAND)) {
      final List<Author> authors = authorService.findByBookId(book.getId());
      final SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy");
      dto.addAuthors(
          authors.stream()
              .map(
                  author -> {
                    return new AuthorDTO(
                        author.getId(),
                        author.getName(),
                        author.getFirstName(),
                        author.getSecondName(),
                        author.getBirthday() != null
                            ? df.format(author.getBirthday())
                            : "Desconocido");
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
