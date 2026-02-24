package org.howard.edu.lsp.assignment2;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class ETLPipelineTest {
    private static final Path DATA_DIR = Paths.get("data");

    @Test
    void run_transformsProductsCsvToExpectedOutput(@TempDir Path tempDir) throws IOException {
        Path inputPath = DATA_DIR.resolve("products.csv");
        Path expectedOutputPath = DATA_DIR.resolve("transformed_products.csv");
        Path actualOutputPath = tempDir.resolve("actual_output.csv");

        ETLPipeline pipeline = new ETLPipeline(inputPath, actualOutputPath);
        pipeline.run();

        assertTrue(Files.exists(actualOutputPath), "Output file should be created");

        String expectedContent = Files.readString(expectedOutputPath).trim();
        String actualContent = Files.readString(actualOutputPath).trim();

        assertEquals(expectedContent, actualContent);
    }
}
