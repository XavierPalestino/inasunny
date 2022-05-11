package mx.dev.blank.web.controller;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import mx.dev.blank.entity.Book;
import mx.dev.blank.model.BookDTO;
import mx.dev.blank.model.BookRankingDTO;
import mx.dev.blank.service.BookService;
import mx.dev.blank.web.request.BookRequest;
import mx.dev.blank.web.request.BookSearchForm;
import mx.dev.blank.web.response.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/book")
public class BookRestController {

  private final BookService bookService;
  private final BookResourceAssembler.Factory assemblerFactory;

  @PostMapping
  public ResponseEntity<BaseResponse> createBook(
      @Valid @RequestBody final BookRequest bookRequest, final BindingResult errors) {

    if (errors.hasErrors()) {
      return ResponseEntity.badRequest().body(BaseResponse.fail(errors.getAllErrors()));
    }

    bookService.createBook(bookRequest);

    return ResponseEntity.ok(BaseResponse.success("Book created successfully"));
  }

  @PutMapping(path = "/{bookId}")
  public ResponseEntity<BaseResponse> updateBook(
      @PathVariable(name = "bookId") final int bookId,
      @Valid @RequestBody final BookRequest bookRequest,
      final BindingResult errors) {

    if (errors.hasErrors()) {
      return ResponseEntity.badRequest().body(BaseResponse.fail(errors.getAllErrors()));
    }

    bookService.updateBook(bookId, bookRequest);

    return ResponseEntity.ok(BaseResponse.success("Book updated successfully"));
  }

  @DeleteMapping(path = "/{bookId}")
  public ResponseEntity<BaseResponse> deleteBook(@PathVariable(name = "bookId") final int bookId) {

    bookService.deleteBook(bookId);
    return ResponseEntity.ok(BaseResponse.success("Book deleted successfully"));
  }

  /*
   * Ejercicio 1, 2, 9
   */
  @GetMapping
  public ResponseEntity<List<BookDTO>> getBooks(
      @ModelAttribute BookSearchForm form,
      @RequestParam(required = false, value = "expand", defaultValue = "")
          final List<String> expand) {

    final List<Book> books = bookService.getBooks(form);

    final BookResourceAssembler assembler = assemblerFactory.create(expand);

    return ResponseEntity.ok(assembler.toDto(books));

  }

  @GetMapping(path = "/{bookId}")
  public ResponseEntity<BookDTO> getBooks(
          @PathVariable(name = "bookId") final int bookId,
          @RequestParam(required = false, value = "expand", defaultValue = "")
          final List<String> expand) {

    final Book book = bookService.findByBookId(bookId);

    final BookResourceAssembler assembler = assemblerFactory.create(expand);

    return ResponseEntity.ok(assembler.toDto(book));


  }

  /*
   * Ejercicio 3
   */
  @GetMapping(value = "/author")
  public ResponseEntity<List<BookDTO>> getBooksByAuthor(
      @RequestParam(value = "author") String author,
      @RequestParam(required = false, value = "expand", defaultValue = "")
          final List<String> expand) {
    final List<Book> books = bookService.getBooksByAuthor(author);

    final BookResourceAssembler assembler = assemblerFactory.create(expand);
    return ResponseEntity.ok(assembler.toDto(books));
  }

  /*
   * Ejercicio 4
   */
  @GetMapping(value = "/price")
  public ResponseEntity<List<BookDTO>> getBooksByPrice(
      @RequestParam BigDecimal priceMin,
      @RequestParam BigDecimal priceMax,
      @RequestParam(required = false, value = "expand", defaultValue = "")
          final List<String> expand) {
    final List<Book> books = bookService.getBooksByPrice(priceMin, priceMax);

    final BookResourceAssembler assembler = assemblerFactory.create(expand);
    return ResponseEntity.ok(assembler.toDto(books));
  }

  /*
   * Ejercicio 5
   */
  @GetMapping(value = "/authors")
  public ResponseEntity<List<BookDTO>> getBooksByAmountAuthors(
      @RequestParam long authors,
      @RequestParam(required = false, value = "expand", defaultValue = "")
          final List<String> expand) {
    final List<Book> books = bookService.getBooksByAmountAuthors(authors);

    final BookResourceAssembler assembler = assemblerFactory.create(expand);
    return ResponseEntity.ok(assembler.toDto(books));
  }

  /*
   * Ejercicio 6
   */
  @GetMapping(value = "/datePublication")
  public ResponseEntity<List<BookDTO>> getBooksByDatePublication(
      @RequestParam String startDate,
      @RequestParam String endDate,
      @RequestParam(required = false, value = "expand", defaultValue = "")
          final List<String> expand)
      throws ParseException {

    final DateFormat date = new SimpleDateFormat("yyyy-MM-dd");
    final Date start = date.parse(startDate);
    final Date end = date.parse(endDate);

    final List<Book> books = bookService.getBooksByDate(start, end);

    final BookResourceAssembler assembler = assemblerFactory.create(expand);
    return ResponseEntity.ok(assembler.toDto(books));
  }

  /*
   * Ejercicio 7
   */
  @GetMapping(value = "/amountByCategory")
  public ResponseEntity<Long> getAmountOfBooksByCategory(@RequestParam String category) {
    final Long books = bookService.getAmountOfBooksByCategory(category);
    return ResponseEntity.ok(books);
  }

  /*
   * Ejercicio 8
   */
  @GetMapping(value = "/category")
  public ResponseEntity<List<BookDTO>> getBooksByCategory(
      @RequestParam String category,
      @RequestParam(required = false, value = "expand", defaultValue = "")
          final List<String> expand) {
    final List<Book> books = bookService.getBooksByCategory(category);

    final BookResourceAssembler assembler = assemblerFactory.create(expand);
    return ResponseEntity.ok(assembler.toDto(books));
  }

  /*
   * Extra
   */
  @GetMapping(value = "/ranking")
  public ResponseEntity<List<BookRankingDTO>> getBooksWithScore(
      @RequestParam Integer limit, @RequestParam(defaultValue = "0") int offset) {
    final List<BookRankingDTO> books = bookService.getBooksWithScore(limit, offset);
    return ResponseEntity.ok(books);
  }
}
