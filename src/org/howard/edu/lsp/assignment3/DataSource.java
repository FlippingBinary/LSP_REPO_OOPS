package org.howard.edu.lsp.assignment3;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Abstraction for data sources that provide CSV content via a BufferedReader.
 *
 * This interface enables polymorphic handling of different input sources
 * (files, strings, etc.) without the consumer needing to know the concrete
 * type.
 */
public interface DataSource {
  /**
   * Opens and returns a BufferedReader for reading CSV data from this source.
   *
   * @return a BufferedReader positioned at the start of the data
   * @throws IOException if the source cannot be opened or read
   */
  BufferedReader open() throws IOException;
}
