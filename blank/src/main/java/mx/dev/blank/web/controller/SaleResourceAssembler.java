package mx.dev.blank.web.controller;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import mx.dev.blank.entity.Sale;
import mx.dev.blank.entity.User;
import mx.dev.blank.entity.Product;
import mx.dev.blank.model.UserDTO;
import mx.dev.blank.model.SaleDTO;
import mx.dev.blank.model.ProductDTO;
import mx.dev.blank.service.UserService;
import mx.dev.blank.service.CategoryService;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
public class SaleResourceAssembler {

  @Component
  @RequiredArgsConstructor
  public static class Factory {

    private final UserService userService;
    private final CategoryService categoryService;

    public SaleResourceAssembler create(final List<String> expand) {
      return new SaleResourceAssembler(userService, categoryService, expand);
    }
  }

  private final UserService userService;
  private final CategoryService categoryService;
  private final List<String> expand;

  public List<SaleDTO> toDto(final List<Sale> sales) {
    return sales.stream().map(this::toDto).collect(Collectors.toList());
  }

  public SaleDTO toDto(final Sale sale) {
    final SaleDTO dto = new SaleDTO(sale);

    if (expand.contains(AUTHOR_EXPAND)) {
      final List<User> users = userService.findByBookId(sale.getId());
      final SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy");
      dto.addUsers(
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
      final List<Product> categories = categoryService.findByBookId(sale.getId());
      dto.addProducts(
          categories.stream()
              .map(
                  product -> {
                    return new ProductDTO(
                            product.getId(),
                            product.getProductName(),
                            product.getProductCode(),
                            product.getProductBrand(),
                            product.getProductExpirationDate(),
                            product.getProductPrice(),
                            product.getProductCost(),
                            product.getProductQuantity());
                  })
              .collect(Collectors.toSet()));
    }

    return dto;
  }

  private static final String AUTHOR_EXPAND = "authors";
  private static final String CATEGORY_EXPAND = "categories";
}
