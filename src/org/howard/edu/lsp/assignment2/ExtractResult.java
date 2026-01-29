package org.howard.edu.lsp.assignment2;

import java.util.List;

/** ExtractResult holds the statistics that are required output */
public class ExtractResult {
  private final List<Product> products;
  private final int rowsRead;
  private final int rowsSkipped;

  public ExtractResult(List<Product> products, int rowsRead, int rowsSkipped) {
    this.products = products;
    this.rowsRead = rowsRead;
    this.rowsSkipped = rowsSkipped;
  }

  public List<Product> getProducts() {
    return products;
  }

  public int getRowsRead() {
    return rowsRead;
  }

  public int getRowsSkipped() {
    return rowsSkipped;
  }
}
