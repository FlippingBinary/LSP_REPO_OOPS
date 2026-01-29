package org.howard.edu.lsp.assignment2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Extract, Transform, Load pipeline to process CSV data according to
 * requirements
 *
 * This is the main entrypoint class for asssignment 2
 **/
public class ETLPipeline {
  private static final Path DEFAULT_INPUT_PATH = Paths.get("data", "products.csv");
  private static final Path DEFAULT_OUTPUT_PATH = Paths.get("data", "transformed_products.csv");

  private final CSVExtractor extractor;
  private final ProductTransformer transformer;
  private final CSVLoader loader;
  private final Path inputPath;
  private final Path outputPath;

  public ETLPipeline() {
    this(DEFAULT_INPUT_PATH, DEFAULT_OUTPUT_PATH);
  }

  public ETLPipeline(Path inputPath, Path outputPath) {
    this.extractor = new CSVExtractor();
    this.transformer = new ProductTransformer();
    this.loader = new CSVLoader();
    this.inputPath = inputPath;
    this.outputPath = outputPath;
  }

  public static void main(String[] args) {
    ETLPipeline pipeline = new ETLPipeline();
    pipeline.run();
  }

  public void run() {
    if (!Files.exists(inputPath)) {
      System.out.println("Error: Input file not found: " + inputPath);
      return;
    }

    ExtractResult extractResult;
    try {
      extractResult = extractor.extract(inputPath);
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
    System.out.println("Rows skipped: " + extractResult.getRowsSkipped());
    System.out.println("Output file: " + outputPath);
  }
}
