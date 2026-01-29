package org.howard.edu.lsp.assignment2;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/** The CSV Loader writes products in CSV format */
public class CSVLoader {
  private static final String OUTPUT_HEADER = "ProductID,Name,Price,Category,PriceRange";

  public void load(List<TransformedProduct> products, Path outputPath) throws IOException {
    Files.createDirectories(outputPath.getParent());

    try (BufferedWriter writer = Files.newBufferedWriter(outputPath)) {
      writer.write(OUTPUT_HEADER);
      writer.newLine();

      for (TransformedProduct product : products) {
        writer.write(product.toCsvRow());
        writer.newLine();
      }
    }
  }
}
