package org.howard.edu.lsp.assignment3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

/**
 * DataSource implementation that reads CSV content from a String.
 *
 * Encapsulates in-memory string-based data retrieval behind the DataSource interface.
 */
public class StringDataSource implements DataSource {
  private final String content;

  /**
   * Constructs a StringDataSource with the given CSV content string.
   *
   * @param content the CSV content as a String
   */
  public StringDataSource(String content) {
    this.content = content;
  }

  /**
   * Opens and returns a BufferedReader for this source's string content.
   *
   * @return a BufferedReader positioned at the start of the string
   * @throws IOException if the reader cannot be created (unlikely for string sources)
   */
  @Override
  public BufferedReader open() throws IOException {
    return new BufferedReader(new StringReader(content));
  }
}
