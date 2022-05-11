package mx.dev.blank.dao;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import mx.dev.blank.DAOTestConfig;
import mx.dev.blank.DBTestConfig;
import mx.dev.blank.entity.Book;
import mx.dev.blank.entity.SortingOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@ContextConfiguration(classes = {DAOTestConfig.class, DBTestConfig.class})
@TestExecutionListeners({
  DependencyInjectionTestExecutionListener.class,
  DbUnitTestExecutionListener.class
})
public class BookDAOTest extends AbstractTransactionalTestNGSpringContextTests {

  private static final String DBUNIT_XML = "classpath:dbunit/dao/book.xml";

  @Autowired private BookDAO bookDAO;

  @DataProvider
  Object[][] findById_dataProvider() {
    return new Object[][] {
      {"Id exists", 1, 1},
      {"Id doesn't exist", 1000, null}
    };
  }

  @Test(dataProvider = "findById_dataProvider")
  @DatabaseSetup(DBUNIT_XML)
  public void findById_should_return_book(
      final String testCase, final int bookId, final Integer expectedId) {
    final Book book = bookDAO.findById(bookId);

    if (expectedId == null) {
      assertThat(book).isNull();
    } else {
      assertThat(book).isNotNull();
      assertThat(book.getId()).isEqualTo(expectedId);
    }
  }

  @DataProvider
  Object[][] findByIds_dataProvider() {
    return new Object[][] {
      {"Ids exists", Arrays.asList(1, 2, 50), Arrays.asList(1, 2)},
      {"Ids don't exist", Arrays.asList(100, 200, 50), Collections.emptyList()}
    };
  }

  @Test(dataProvider = "findByIds_dataProvider")
  @DatabaseSetup(DBUNIT_XML)
  public void findByIds_should_return_book(
      final String testCase, final List<Integer> bookIds, final List<Integer> expectedIds) {
    final List<Book> books = bookDAO.findByIds(bookIds);

    assertThat(books)
        .hasSameSizeAs(expectedIds)
        .extracting(Book::getId)
        .containsOnlyElementsOf(expectedIds);
  }

  @DataProvider
  Object[][] findBooks_dataProvider() {
    return new Object[][] {
      {"Title asc, offset 1", "title", SortingOrder.ASC, null, 1, Arrays.asList(2, 3, 4)},
      {"Title desc, limit 2", "title", SortingOrder.DESC, 2, 0, Arrays.asList(4, 3)},
      {"Pages asc, offset 1", "pages", SortingOrder.ASC, null, 1, Arrays.asList(1, 4, 3)},
      {"Pages desc, limit 2", "pages", SortingOrder.DESC, 2, 0, Arrays.asList(3, 4)},
      {
        "Release date asc, offset 1",
        "releaseDate",
        SortingOrder.ASC,
        null,
        1,
        Arrays.asList(4, 1, 3)
      },
      {"Release date desc, limit 2", "releaseDate", SortingOrder.DESC, 2, 0, Arrays.asList(3, 1)},
      {
        "Incorrect sort no limit",
        "summary",
        SortingOrder.ASC,
        null,
        null,
        Arrays.asList(1, 2, 3, 4)
      },
    };
  }

  @Test(dataProvider = "findBooks_dataProvider")
  @DatabaseSetup(DBUNIT_XML)
  public void findBooks_should_return_book(
      final String testCase,
      final String sortField,
      final SortingOrder order,
      final Integer limit,
      final Integer offset,
      final List<Integer> expectedIds) {
    final List<Book> books = bookDAO.findBooks(sortField, order, limit, offset);

    assertThat(books)
        .hasSameSizeAs(expectedIds)
        .extracting(Book::getId)
        .containsExactlyElementsOf(expectedIds);
  }

  @DataProvider
  Object[][] getBookByAuthor_dataProvider() {
    return new Object[][] {
      {"Name found", "author-1", Arrays.asList(1, 2)},
      {"FirstName found", "fn-3", Collections.singletonList(1)},
      {"SecondName found", "sn-1", Arrays.asList(1, 2)},
      {"No found", "Roberto", Collections.emptyList()}
    };
  }

  @Test(dataProvider = "getBookByAuthor_dataProvider")
  @DatabaseSetup(DBUNIT_XML)
  public void getBookByAuthor_should_return_book(
      final String testCase, final String filterText, final List<Integer> expectedIds) {
    final List<Book> books = bookDAO.getBookByAuthor(filterText);

    assertThat(books)
        .hasSameSizeAs(expectedIds)
        .extracting(Book::getId)
        .containsOnlyElementsOf(expectedIds);
  }

  @DataProvider
  Object[][] getBooksByPrice_dataProvider() {
    return new Object[][] {
      {"Books found", new BigDecimal(450), new BigDecimal(700), Arrays.asList(1, 2)},
      {"No found", new BigDecimal(150), new BigDecimal(300), Collections.emptyList()}
    };
  }

  @Test(dataProvider = "getBooksByPrice_dataProvider")
  @DatabaseSetup(DBUNIT_XML)
  public void getBooksByPrice_should_return_book(
      final String testCase,
      final BigDecimal minPrice,
      final BigDecimal maxPrice,
      final List<Integer> expectedIds) {
    final List<Book> books = bookDAO.getBooksByPrice(minPrice, maxPrice);

    assertThat(books)
        .hasSameSizeAs(expectedIds)
        .extracting(Book::getId)
        .containsOnlyElementsOf(expectedIds);
  }

  @DataProvider
  Object[][] getBooksByAmountAuthors_dataProvider() {
    return new Object[][] {
      {"Books found", 1, Arrays.asList(2, 3)},
      {"No found", 5, Collections.emptyList()}
    };
  }

  @Test(dataProvider = "getBooksByAmountAuthors_dataProvider")
  @DatabaseSetup(DBUNIT_XML)
  public void getBooksByAmountAuthors_should_return_number(
      final String testCase, final long numAuthors, final List<Integer> expectedIds) {
    final List<Integer> bookIds = bookDAO.getBooksByAmountAuthors(numAuthors);

    assertThat(bookIds).hasSameSizeAs(expectedIds).containsOnlyElementsOf(expectedIds);
  }

  @DataProvider
  Object[][] getBooksByDate_dataProvider() {

    final Date startDate = new Date();
    startDate.setTime(200534400000L); // 1976-05-10

    final Date endDate = new Date();
    endDate.setTime(219024000000L); // 1976-12-10

    return new Object[][] {
      {"Books found", startDate, endDate, Collections.singletonList(3)},
      {"No found", endDate, new Date(), Collections.emptyList()}
    };
  }

  @Test(dataProvider = "getBooksByDate_dataProvider")
  @DatabaseSetup(DBUNIT_XML)
  public void getBooksByDate_should_return_book(
      final String testCase,
      final Date startDate,
      final Date endDate,
      final List<Integer> expectedIds) {
    final List<Book> books = bookDAO.getBooksByDate(startDate, endDate);

    assertThat(books)
        .hasSameSizeAs(expectedIds)
        .extracting(Book::getId)
        .containsOnlyElementsOf(expectedIds);
  }

  @DataProvider
  Object[][] getAmountOfBooksByCategory_dataProvider() {
    return new Object[][] {
      {"Books found", "Terror", 2},
      {"No found", "Biografia", 0}
    };
  }

  @Test(dataProvider = "getAmountOfBooksByCategory_dataProvider")
  @DatabaseSetup(DBUNIT_XML)
  public void getBooksByCategory_should_return_count(
      final String testCase, final String categoryName, final long expected) {
    final long count = bookDAO.getAmountOfBooksByCategory(categoryName);

    assertThat(count).isEqualTo(expected);
  }

  @DataProvider
  Object[][] getBooksByCategory_dataProvider() {
    return new Object[][] {
      {"Books found", "Terror", Arrays.asList(1, 3)},
      {"No found", "Biografia", Collections.emptyList()}
    };
  }

  @Test(dataProvider = "getBooksByCategory_dataProvider")
  @DatabaseSetup(DBUNIT_XML)
  public void getBooksByCategory_should_return_books(
      final String testCase, final String categoryName, final List<Integer> expectedIds) {
    final List<Book> books = bookDAO.getBooksByCategory(categoryName);

    assertThat(books)
        .hasSameSizeAs(expectedIds)
        .extracting(Book::getId)
        .containsOnlyElementsOf(expectedIds);
  }
}
