package org.howard.edu.lsp.assignment2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CSVExtractorTest {
  private CSVExtractor extractor;

  @BeforeEach
  void setUp() {
    extractor = new CSVExtractor();
  }

  @Test
  void extract_parsesValidCsvRow() throws IOException {
    String csv = "ProductID,Name,Price,Category\n1,Laptop,999.99,Electronics";
    ExtractResult result = extractor.extract(csv);

    assertEquals(1, result.getProducts().size());
    Product product = result.getProducts().get(0);
    assertEquals(1, product.getProductId());
    assertEquals("Laptop", product.getName());
    assertEquals(new BigDecimal("999.99"), product.getPrice());
    assertEquals("Electronics", product.getCategory());
  }

  @Test
  void extract_skipsEmptyLines() throws IOException {
    String csv = "ProductID,Name,Price,Category\n1,Laptop,999.99,Electronics\n\n2,Phone,499.99,Electronics";
    ExtractResult result = extractor.extract(csv);

    assertEquals(2, result.getProducts().size());
    assertEquals(3, result.getRowsRead());
    assertEquals(1, result.getRowsSkipped());
  }

  @Test
  void extract_skipsRowsWithWrongFieldCount() throws IOException {
    String csv = "ProductID,Name,Price,Category\n1,Laptop,999.99,Electronics\n2,Phone,499.99";
    ExtractResult result = extractor.extract(csv);

    assertEquals(1, result.getProducts().size());
    assertEquals(1, result.getRowsSkipped());
  }

  @Test
  void extract_skipsRowsWithInvalidProductId() throws IOException {
    String csv = "ProductID,Name,Price,Category\nabc,Laptop,999.99,Electronics";
    ExtractResult result = extractor.extract(csv);

    assertEquals(0, result.getProducts().size());
    assertEquals(1, result.getRowsSkipped());
  }

  @Test
  void extract_skipsRowsWithInvalidPrice() throws IOException {
    String csv = "ProductID,Name,Price,Category\n1,Laptop,notaprice,Electronics";
    ExtractResult result = extractor.extract(csv);

    assertEquals(0, result.getProducts().size());
    assertEquals(1, result.getRowsSkipped());
  }

  @Test
  void extract_trimsFieldWhitespace() throws IOException {
    String csv = "ProductID,Name,Price,Category\n  1  ,  Laptop  ,  999.99  ,  Electronics  ";
    ExtractResult result = extractor.extract(csv);

    assertEquals(1, result.getProducts().size());
    Product product = result.getProducts().get(0);
    assertEquals("Laptop", product.getName());
    assertEquals("Electronics", product.getCategory());
  }

  @Test
  void extract_handlesEmptyFile() throws IOException {
    String csv = "ProductID,Name,Price,Category";
    ExtractResult result = extractor.extract(csv);

    assertEquals(0, result.getProducts().size());
    assertEquals(0, result.getRowsRead());
    assertEquals(0, result.getRowsSkipped());
  }

  @Test
  void extract_handlesMultipleValidRows() throws IOException {
    String csv = """
        ProductID,Name,Price,Category
        1,Laptop,999.99,Electronics
        2,Chair,149.99,Furniture
        3,Book,19.99,Books
        """;
    ExtractResult result = extractor.extract(csv);

    assertEquals(3, result.getProducts().size());
    assertEquals(3, result.getRowsRead());
    assertEquals(0, result.getRowsSkipped());
  }

  @Test
  void extract_countsRowsCorrectlyExcludingHeader() throws IOException {
    String csv = "ProductID,Name,Price,Category\n1,Laptop,999.99,Electronics\n2,Phone,499.99,Electronics";
    ExtractResult result = extractor.extract(csv);

    assertEquals(2, result.getRowsRead());
  }
}
