package org.howard.edu.lsp.assignment3;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * DataSource implementation that reads CSV content from a file.
 * Encapsulates file-based data retrieval behind the DataSource interface.
 */
public class FileDataSource implements DataSource {
  private final Path path;

  /**
   * Constructs a FileDataSource for the given file path.
   *
   * @param path the Path to the CSV file to read
   */
  public FileDataSource(Path path) {
    this.path = path;
  }

  /**
   * Opens and returns a BufferedReader for the file at this source's path.
   *
   * @return a BufferedReader positioned at the start of the file
   * @throws IOException if the file cannot be opened or read
   */
  @Override
  public BufferedReader open() throws IOException {
    return Files.newBufferedReader(path);
  }
}
