package org.howard.edu.lsp.assignment2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/** The CSV extractor reads product data from a CSV-formatted string */
public class CSVExtractor {
  private static final int EXPECTED_FIELD_COUNT = 4;

  public ExtractResult extract(Path inputPath) throws IOException {
    try (BufferedReader reader = Files.newBufferedReader(inputPath)) {
      return extract(reader);
    }
  }

  public ExtractResult extract(String csvContent) throws IOException {
    try (BufferedReader reader = new BufferedReader(new StringReader(csvContent))) {
      return extract(reader);
    }
  }

  public ExtractResult extract(BufferedReader reader) throws IOException {
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
