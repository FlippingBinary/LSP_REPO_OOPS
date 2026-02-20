package org.howard.edu.lsp.assignment3;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Handles CSV output writing for transformed products.
 *
 * This class is responsible for serializing a list of TransformedProduct
 * objects to CSV format. It encapsulates the CSV formatting logic, including
 * header generation and row serialization using direct getter method calls.
 */
public class CSVLoader {
  private static final String OUTPUT_HEADER = "ProductID,Name,Price,Category,PriceRange";

  /**
   * Writes a list of transformed products to a CSV file.
   *
   * Creates parent directories as needed and writes the CSV header followed by
   * each product formatted as a CSV row using inline getter calls for proper
   * encapsulation.
   *
   * @param products   the list of TransformedProduct objects to write
   * @param outputPath the file path where the CSV should be written
   * @throws IOException if an I/O error occurs during file writing
   */
  public void load(List<TransformedProduct> products, Path outputPath) throws IOException {
    Files.createDirectories(outputPath.getParent());

    try (BufferedWriter writer = Files.newBufferedWriter(outputPath)) {
      writer.write(OUTPUT_HEADER);
      writer.newLine();

      for (TransformedProduct product : products) {
        writer.write(product.getProductId() + "," + product.getName() + "," +
            product.getPrice().toPlainString() + "," + product.getCategory() + "," +
            product.getPriceRange());
        writer.newLine();
      }
    }
  }
}
