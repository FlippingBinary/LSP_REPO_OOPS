package org.howard.edu.lsp.assignment3;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Extracts product data from CSV content provided by a DataSource.
 * 
 * This class demonstrates polymorphism by accepting any DataSource
 * implementation, enabling polymorphic handling of different input sources
 * (files, strings, etc.) without needing to know the concrete type.
 */
public class CSVExtractor {
  private static final int EXPECTED_FIELD_COUNT = 4;

  /**
   * Extracts product data from the given DataSource.
   * 
   * The method opens a BufferedReader from the source, parses CSV content,
   * and returns an ExtractResult containing successfully parsed products
   * along with row count metadata.
   * 
   * @param source the DataSource providing CSV content to extract
   * @return an ExtractResult containing extracted products and row counts
   * @throws IOException if the source cannot be opened or read
   */
  public ExtractResult extract(DataSource source) throws IOException {
    try (BufferedReader reader = source.open()) {
      List<Product> products = new ArrayList<>();
      int rowsRead = 0;
      int rowsSkipped = 0;

      String line;
      boolean isHeader = true;

      while ((line = reader.readLine()) != null) {
        if (isHeader) {
          isHeader = false;
          continue;
        }

        rowsRead++;

        String trimmedLine = line.trim();
        if (trimmedLine.isEmpty()) {
          rowsSkipped++;
          continue;
        }

        String[] fields = line.split(",");
        if (fields.length != EXPECTED_FIELD_COUNT) {
          rowsSkipped++;
          continue;
        }

        for (int i = 0; i < fields.length; i++) {
          fields[i] = fields[i].trim();
        }

        Product product = parseProduct(fields);
        if (product == null) {
          rowsSkipped++;
          continue;
        }

        products.add(product);
      }

      return new ExtractResult(products, rowsRead, rowsSkipped);
    }
  }

  private Product parseProduct(String[] fields) {
    int productId;
    try {
      productId = Integer.parseInt(fields[0]);
    } catch (NumberFormatException e) {
      return null;
    }

    BigDecimal price;
    try {
      price = new BigDecimal(fields[2]);
    } catch (NumberFormatException e) {
      return null;
    }

    return new Product(productId, fields[1], price, fields[3]);
  }
}
