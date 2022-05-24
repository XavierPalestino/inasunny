package mx.dev.blank.web.controller;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import mx.dev.blank.entity.Sale;
import mx.dev.blank.model.SaleDTO;
import mx.dev.blank.service.SaleService;
import mx.dev.blank.web.request.SaleRequest;
import mx.dev.blank.web.request.BookSearchForm;
import mx.dev.blank.web.response.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/book")
public class SaleRestController {

  private final SaleService saleService;
  private final SaleResourceAssembler.Factory assemblerFactory;

  @PostMapping
  public ResponseEntity<BaseResponse> createBook(
          @Valid @RequestBody final SaleRequest saleRequest, final BindingResult errors) {

    if (errors.hasErrors()) {
      return ResponseEntity.badRequest().body(BaseResponse.fail(errors.getAllErrors()));
    }

    saleService.createBook(saleRequest);

    return ResponseEntity.ok(BaseResponse.success("Book created successfully"));
  }

  @PutMapping(path = "/{bookId}")
  public ResponseEntity<BaseResponse> updateBook(
      @PathVariable(name = "bookId") final int bookId,
      @Valid @RequestBody final SaleRequest saleRequest,
      final BindingResult errors) {

    if (errors.hasErrors()) {
      return ResponseEntity.badRequest().body(BaseResponse.fail(errors.getAllErrors()));
    }

    saleService.updateBook(bookId, saleRequest);

    return ResponseEntity.ok(BaseResponse.success("Book updated successfully"));
  }

  @DeleteMapping(path = "/{bookId}")
  public ResponseEntity<BaseResponse> deleteBook(@PathVariable(name = "bookId") final int bookId) {

    saleService.deleteBook(bookId);
    return ResponseEntity.ok(BaseResponse.success("Book deleted successfully"));
  }

  /*
   * Ejercicio 1, 2, 9
   */
  @GetMapping
  public ResponseEntity<List<SaleDTO>> getBooks(
      @ModelAttribute BookSearchForm form,
      @RequestParam(required = false, value = "expand", defaultValue = "")
          final List<String> expand) {

    final List<Sale> sales = saleService.getBooks(form);

    final SaleResourceAssembler assembler = assemblerFactory.create(expand);

    return ResponseEntity.ok(assembler.toDto(sales));

  }

  @GetMapping(path = "/{bookId}")
  public ResponseEntity<SaleDTO> getBooks(
          @PathVariable(name = "bookId") final int bookId,
          @RequestParam(required = false, value = "expand", defaultValue = "")
          final List<String> expand) {

    final Sale sale = saleService.findByBookId(bookId);

    final SaleResourceAssembler assembler = assemblerFactory.create(expand);

    return ResponseEntity.ok(assembler.toDto(sale));


  }

  /*
   * Ejercicio 3
   */
  @GetMapping(value = "/author")
  public ResponseEntity<List<SaleDTO>> getBooksByAuthor(
      @RequestParam(value = "author") String author,
      @RequestParam(required = false, value = "expand", defaultValue = "")
          final List<String> expand) {
    final List<Sale> sales = saleService.getBooksByAuthor(author);

    final SaleResourceAssembler assembler = assemblerFactory.create(expand);
    return ResponseEntity.ok(assembler.toDto(sales));
  }

  /*
   * Ejercicio 4
   */
  @GetMapping(value = "/price")
  public ResponseEntity<List<SaleDTO>> getBooksByPrice(
      @RequestParam BigDecimal priceMin,
      @RequestParam BigDecimal priceMax,
      @RequestParam(required = false, value = "expand", defaultValue = "")
          final List<String> expand) {
    final List<Sale> sales = saleService.getBooksByPrice(priceMin, priceMax);

    final SaleResourceAssembler assembler = assemblerFactory.create(expand);
    return ResponseEntity.ok(assembler.toDto(sales));
  }

  /*
   * Ejercicio 5
   */
  @GetMapping(value = "/authors")
  public ResponseEntity<List<SaleDTO>> getBooksByAmountAuthors(
      @RequestParam long authors,
      @RequestParam(required = false, value = "expand", defaultValue = "")
          final List<String> expand) {
    final List<Sale> sales = saleService.getBooksByAmountAuthors(authors);

    final SaleResourceAssembler assembler = assemblerFactory.create(expand);
    return ResponseEntity.ok(assembler.toDto(sales));
  }

  /*
   * Ejercicio 6
   */
  @GetMapping(value = "/datePublication")
  public ResponseEntity<List<SaleDTO>> getBooksByDatePublication(
      @RequestParam String startDate,
      @RequestParam String endDate,
      @RequestParam(required = false, value = "expand", defaultValue = "")
          final List<String> expand)
      throws ParseException {

    final DateFormat date = new SimpleDateFormat("yyyy-MM-dd");
    final Date start = date.parse(startDate);
    final Date end = date.parse(endDate);

    final List<Sale> sales = saleService.getBooksByDate(start, end);

    final SaleResourceAssembler assembler = assemblerFactory.create(expand);
    return ResponseEntity.ok(assembler.toDto(sales));
  }

  /*
   * Ejercicio 7
   */
  @GetMapping(value = "/amountByCategory")
  public ResponseEntity<Long> getAmountOfBooksByCategory(@RequestParam String category) {
    final Long books = saleService.getAmountOfBooksByCategory(category);
    return ResponseEntity.ok(books);
  }

  /*
   * Ejercicio 8
   */
  @GetMapping(value = "/category")
  public ResponseEntity<List<SaleDTO>> getBooksByCategory(
      @RequestParam String category,
      @RequestParam(required = false, value = "expand", defaultValue = "")
          final List<String> expand) {
    final List<Sale> sales = saleService.getBooksByCategory(category);

    final SaleResourceAssembler assembler = assemblerFactory.create(expand);
    return ResponseEntity.ok(assembler.toDto(sales));
  }
}
