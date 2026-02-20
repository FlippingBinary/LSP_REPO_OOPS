package org.howard.edu.lsp.assignment3;

import java.util.List;

/**
 * ExtractResult encapsulates the results of a CSV extraction operation.
 * 
 * This immutable data container holds the extracted Product records along with
 * metadata about the extraction process (number of skipped rows).
 */
public class ExtractResult {
  private final List<Product> products;
  private final int rowsRead;
  private final int skippedRows;

  /**
   * Constructs an ExtractResult with the extracted products and row counts.
   * 
   * @param products the list of successfully extracted Product records
   * @param rowsRead the number of rows read from the CSV
   * @param skippedRows the number of rows skipped during extraction
   */
  public ExtractResult(List<Product> products, int rowsRead, int skippedRows) {
    this.products = products;
    this.rowsRead = rowsRead;
    this.skippedRows = skippedRows;
  }

  /**
   * Returns the list of extracted products.
   * 
   * @return the list of Product records extracted from the CSV
   */
  public List<Product> getProducts() {
    return products;
  }

  /**
   * Returns the number of rows read from the CSV.
   * 
   * @return the count of rows that were read
   */
  public int getRowsRead() {
    return rowsRead;
  }

  /**
   * Returns the number of rows skipped during extraction.
   * 
   * @return the count of rows that were skipped
   */
  public int getSkippedRows() {
    return skippedRows;
  }
}
