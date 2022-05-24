package mx.dev.blank.web.controller;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import mx.dev.blank.model.ProductDTO;
import mx.dev.blank.service.CategoryService;
import mx.dev.blank.web.request.ProductRequest;
import mx.dev.blank.web.response.BaseResponse;
import mx.dev.blank.web.response.CategoryResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/product")
public class CategoryRestController {

  private final CategoryService categoryService;

  @PostMapping
  public ResponseEntity<BaseResponse> createCategory(
          @Valid @RequestBody final ProductRequest productRequest, final BindingResult errors) {

    if (errors.hasErrors()) {
      return ResponseEntity.badRequest().body(BaseResponse.fail(errors.getAllErrors()));
    }

    categoryService.createCategory(productRequest);

    return ResponseEntity.ok(BaseResponse.success("Category created successfully"));
  }

  @PutMapping(path = "/{categoryId}")
  public ResponseEntity<BaseResponse> updateCategory(
      @PathVariable(name = "categoryId") final int categoryId,
      @Valid @RequestBody final ProductRequest productRequest,
      final BindingResult errors) {

    if (errors.hasErrors()) {
      return ResponseEntity.badRequest().body(BaseResponse.fail(errors.getAllErrors()));
    }

    categoryService.updateCategory(categoryId, productRequest);

    return ResponseEntity.ok(BaseResponse.success("Category updated successfully"));
  }

  @DeleteMapping(path = "/{categoryId}")
  public ResponseEntity<BaseResponse> deleteCategory(
      @PathVariable(name = "categoryId") final int categoryId) {

    categoryService.deleteCategory(categoryId);
    return ResponseEntity.ok(BaseResponse.success("Category deleted successfully"));
  }

  @GetMapping(path = "/list")
  public ResponseEntity<CategoryResponse> getCategory() {

    final List<ProductDTO> retrieved = categoryService.findAll();

    return ResponseEntity.ok(new CategoryResponse(retrieved));
  }
}
