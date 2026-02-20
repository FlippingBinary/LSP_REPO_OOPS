package org.howard.edu.lsp.assignment3;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration test for the ETLPipeline class.
 * Tests that the ETL pipeline correctly transforms product data from input CSV to output CSV,
 * comparing actual output against expected output file located in the project data directory.
 */
class ETLPipelineTest {

    @Test
    void run_transformsProductsCsvToExpectedOutput(@TempDir Path tempDir) throws IOException {
        Path inputPath = Paths.get("data", "products.csv");
        Path expectedOutputPath = Paths.get("data", "transformed_products.csv");
        Path actualOutputPath = tempDir.resolve("actual_output.csv");

        ETLPipeline pipeline = new ETLPipeline(inputPath, actualOutputPath);
        pipeline.run();

        assertTrue(Files.exists(actualOutputPath), "Output file should be created");

        String expectedContent = Files.readString(expectedOutputPath).trim();
        String actualContent = Files.readString(actualOutputPath).trim();

        assertEquals(expectedContent, actualContent);
    }
}
