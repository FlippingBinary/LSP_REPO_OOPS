package org.howard.edu.lsp.assignment3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Orchestrates the Extract, Transform, Load (ETL) pipeline for CSV data
 * processing.
 *
 * This class serves as the main entry point for assignment 3, demonstrating
 * polymorphic input handling through the DataSource abstraction. The pipeline
 * extracts product data from a CSV source, applies business transformations,
 * and loads the results to an output file.
 */
public class ETLPipeline {
  private static final Path DEFAULT_INPUT_PATH = Paths.get("data", "products.csv");
  private static final Path DEFAULT_OUTPUT_PATH = Paths.get("data", "transformed_products.csv");

  private final CSVExtractor extractor;
  private final ProductTransformer transformer;
  private final CSVLoader loader;
  private final Path inputPath;
  private final Path outputPath;

  /**
   * Constructs an ETLPipeline with default input and output file paths.
   *
   * Delegates to the parameterized constructor using default paths: input from
   * "data/products.csv" and output to "data/transformed_products.csv".
   */
  public ETLPipeline() {
    this(DEFAULT_INPUT_PATH, DEFAULT_OUTPUT_PATH);
  }

  /**
   * Constructs an ETLPipeline with specified input and output file paths.
   *
   * Creates the internal ETL components (extractor, transformer, loader) and
   * stores the provided paths for pipeline execution.
   *
   * @param inputPath  the Path to the input CSV file containing product data
   * @param outputPath the Path where transformed product data will be written
   */
  public ETLPipeline(Path inputPath, Path outputPath) {
    this.extractor = new CSVExtractor();
    this.transformer = new ProductTransformer();
    this.loader = new CSVLoader();
    this.inputPath = inputPath;
    this.outputPath = outputPath;
  }

  /**
   * Main entry point for the ETL pipeline application.
   *
   * Creates a new ETLPipeline instance with default paths and executes the run
   * method.
   *
   * @param args command-line arguments (not used)
   */
  public static void main(String[] args) {
    ETLPipeline pipeline = new ETLPipeline();
    pipeline.run();
  }

  /**
   * Executes the ETL pipeline: extract, transform, load, and summarize.
   *
   * Orchestration steps:
   * 1. Verify input file exists
   * 2. Extract product data from FileDataSource wrapping the input path
   * 3. Transform extracted products using business rules
   * 4. Load transformed products to output CSV file
   * 5. Print execution summary
   */
  public void run() {
    if (!Files.exists(inputPath)) {
      System.out.println("Error: Input file not found: " + inputPath);
      return;
    }

    ExtractResult extractResult;
    try {
      extractResult = extractor.extract(new FileDataSource(inputPath));
    } catch (IOException e) {
      System.out.println("Error reading input file: " + e.getMessage());
      return;
    }

    List<TransformedProduct> transformedProducts = transformer.transform(extractResult.getProducts());

    try {
      loader.load(transformedProducts, outputPath);
    } catch (IOException e) {
      System.out.println("Error writing output file: " + e.getMessage());
      return;
    }

    printSummary(extractResult, transformedProducts.size());
  }

  private void printSummary(ExtractResult extractResult, int rowsTransformed) {
    System.out.println("Rows read (excluding header): " + extractResult.getRowsRead());
    System.out.println("Rows transformed: " + rowsTransformed);
    System.out.println("Rows skipped: " + extractResult.getSkippedRows());
    System.out.println("Output file: " + outputPath);
  }
}
