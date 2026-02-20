package org.howard.edu.lsp.assignment3;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests to verify Javadoc completeness for all assignment3 source files.
 *
 * This test reads Java source files as text and uses regex matching to verify:
 * - Class/interface-level Javadoc exists
 * - All public methods have method-level Javadoc
 * - @param tags exist for each parameter
 * - @return tags exist for non-void methods
 * - @throws tags exist for methods declaring exceptions
 */
class JavadocTest {
  private static final Path SOURCE_DIR = Paths.get("src", "org", "howard", "edu", "lsp", "assignment3");

  private static final List<String> SOURCE_FILES = List.of(
      "Product.java",
      "TransformedProduct.java",
      "DataSource.java",
      "FileDataSource.java",
      "StringDataSource.java",
      "ExtractResult.java",
      "CSVExtractor.java",
      "ProductTransformer.java",
      "CSVLoader.java",
      "ETLPipeline.java");

  // Pattern to match Javadoc comment: /** ... */
  private static final Pattern JAVADOC_PATTERN = Pattern.compile("/\\*\\*.*?\\*/", Pattern.DOTALL);

  // Pattern to match class/interface declaration - must be at start of line or
  // after whitespace and must have "public" before "class" or "interface"
  private static final Pattern CLASS_DECLARATION_PATTERN = Pattern
      .compile("^\\s*public\\s+(?:abstract\\s+)?(?:class|interface)\\s+(\\w+)", Pattern.MULTILINE);

  // Pattern to match public method declarations (including static, not
  // constructors)
  private static final Pattern PUBLIC_METHOD_PATTERN = Pattern.compile(
      "^\\s*public\\s+(?:static\\s+)?(?!class|interface)(?:<[^>]+>\\s+)?(\\S+)\\s+(\\w+)\\s*\\(([^)]*)\\)(?:\\s*throws\\s+([\\w,\\s]+))?\\s*[{;]",
      Pattern.MULTILINE);

  // Pattern to match public constructor declarations
  private static final Pattern PUBLIC_CONSTRUCTOR_PATTERN = Pattern
      .compile("^\\s*public\\s+(\\w+)\\s*\\(([^)]*)\\)(?:\\s*throws\\s+([\\w,\\s]+))?\\s*\\{", Pattern.MULTILINE);

  @ParameterizedTest(name = "File {0} has class-level Javadoc")
  @ValueSource(strings = {
      "Product.java",
      "TransformedProduct.java",
      "DataSource.java",
      "FileDataSource.java",
      "StringDataSource.java",
      "ExtractResult.java",
      "CSVExtractor.java",
      "ProductTransformer.java",
      "CSVLoader.java",
      "ETLPipeline.java"
  })
  void verifyClassLevelJavadoc(String fileName) throws IOException {
    Path filePath = SOURCE_DIR.resolve(fileName);
    assertTrue(Files.exists(filePath), "Source file should exist: " + filePath);

    String content = Files.readString(filePath);

    // Find the class/interface declaration
    Matcher classMatcher = CLASS_DECLARATION_PATTERN.matcher(content);
    assertTrue(classMatcher.find(), "Should find class/interface declaration in " + fileName);

    String className = classMatcher.group(1);
    int classPosition = classMatcher.start();

    // Check that there's a Javadoc comment before the class declaration
    String contentBeforeClass = content.substring(0, classPosition);

    // Find the last Javadoc before the class declaration
    Matcher javadocMatcher = JAVADOC_PATTERN.matcher(contentBeforeClass);
    boolean foundJavadoc = false;
    int lastJavadocEnd = -1;

    while (javadocMatcher.find()) {
      foundJavadoc = true;
      lastJavadocEnd = javadocMatcher.end();
    }

    assertTrue(foundJavadoc,
        "Class " + className + " in " + fileName + " should have Javadoc comment");

    // Verify the Javadoc is immediately before the class (only
    // whitespace/annotations between)
    String between = contentBeforeClass.substring(lastJavadocEnd).trim();
    // Remove any annotations that might be between Javadoc and class
    between = between.replaceAll("@\\w+(?:\\([^)]*\\))?\\s*", "").trim();
    assertTrue(between.isEmpty() || between.startsWith("public") || between.startsWith("abstract"),
        "Javadoc should be immediately before class declaration in " + fileName +
            ", found: '" + between + "'");
  }

  @ParameterizedTest(name = "File {0} has complete method Javadoc")
  @ValueSource(strings = {
      "Product.java",
      "TransformedProduct.java",
      "DataSource.java",
      "FileDataSource.java",
      "StringDataSource.java",
      "ExtractResult.java",
      "CSVExtractor.java",
      "ProductTransformer.java",
      "CSVLoader.java",
      "ETLPipeline.java"
  })
  void verifyPublicMethodJavadoc(String fileName) throws IOException {
    Path filePath = SOURCE_DIR.resolve(fileName);
    String content = Files.readString(filePath);

    List<String> missingJavadoc = new ArrayList<>();
    List<String> missingParamTags = new ArrayList<>();
    List<String> missingReturnTags = new ArrayList<>();
    List<String> missingThrowsTags = new ArrayList<>();

    // Check public methods
    Matcher methodMatcher = PUBLIC_METHOD_PATTERN.matcher(content);
    while (methodMatcher.find()) {
      String returnType = methodMatcher.group(1);
      String methodName = methodMatcher.group(2);
      String params = methodMatcher.group(3);
      String throwsClause = methodMatcher.group(4);
      int methodPosition = methodMatcher.start();

      // Find Javadoc before this method
      String javadoc = findJavadocBefore(content, methodPosition);

      if (javadoc == null) {
        missingJavadoc.add(methodName + "(" + params + ")");
        continue;
      }

      // Check @param tags for each parameter
      if (!params.trim().isEmpty()) {
        String[] paramList = params.split(",");
        for (String param : paramList) {
          param = param.trim();
          if (param.isEmpty())
            continue;

          // Extract parameter name (last word before potential array brackets or
          // generics)
          String[] parts = param.replaceAll("<[^>]+>", "").replaceAll("\\[\\]", "").trim().split("\\s+");
          if (parts.length >= 2) {
            String paramName = parts[parts.length - 1];
            if (!javadoc.contains("@param " + paramName)) {
              missingParamTags.add(methodName + " -> @param " + paramName);
            }
          }
        }
      }

      // Check @return tag for non-void methods
      if (!returnType.equals("void") && !javadoc.contains("@return")) {
        missingReturnTags.add(methodName + " (returns " + returnType + ")");
      }

      // Check @throws tag for methods declaring exceptions
      if (throwsClause != null && !throwsClause.trim().isEmpty()) {
        String[] exceptions = throwsClause.split(",");
        for (String exception : exceptions) {
          exception = exception.trim();
          if (!javadoc.contains("@throws " + exception)) {
            missingThrowsTags.add(methodName + " -> @throws " + exception);
          }
        }
      }
    }

    // Check public constructors
    Matcher constructorMatcher = PUBLIC_CONSTRUCTOR_PATTERN.matcher(content);
    while (constructorMatcher.find()) {
      String constructorName = constructorMatcher.group(1);
      String params = constructorMatcher.group(2);
      String throwsClause = constructorMatcher.group(3);
      int constructorPosition = constructorMatcher.start();

      // Find Javadoc before this constructor
      String javadoc = findJavadocBefore(content, constructorPosition);

      if (javadoc == null) {
        missingJavadoc.add("Constructor " + constructorName + "(" + params + ")");
        continue;
      }

      // Check @param tags for each parameter
      if (!params.trim().isEmpty()) {
        String[] paramList = params.split(",");
        for (String param : paramList) {
          param = param.trim();
          if (param.isEmpty())
            continue;

          String[] parts = param.replaceAll("<[^>]+>", "").replaceAll("\\[\\]", "").trim().split("\\s+");
          if (parts.length >= 2) {
            String paramName = parts[parts.length - 1];
            if (!javadoc.contains("@param " + paramName)) {
              missingParamTags.add(constructorName + " -> @param " + paramName);
            }
          }
        }
      }

      // Check @throws tag for constructors declaring exceptions
      if (throwsClause != null && !throwsClause.trim().isEmpty()) {
        String[] exceptions = throwsClause.split(",");
        for (String exception : exceptions) {
          exception = exception.trim();
          if (!javadoc.contains("@throws " + exception)) {
            missingThrowsTags.add("Constructor " + constructorName + " -> @throws " + exception);
          }
        }
      }
    }

    // Build error message if any issues found
    StringBuilder errors = new StringBuilder();
    if (!missingJavadoc.isEmpty()) {
      errors.append("Missing Javadoc for: ").append(missingJavadoc).append("\n");
    }
    if (!missingParamTags.isEmpty()) {
      errors.append("Missing @param tags: ").append(missingParamTags).append("\n");
    }
    if (!missingReturnTags.isEmpty()) {
      errors.append("Missing @return tags: ").append(missingReturnTags).append("\n");
    }
    if (!missingThrowsTags.isEmpty()) {
      errors.append("Missing @throws tags: ").append(missingThrowsTags).append("\n");
    }

    assertTrue(errors.isEmpty(),
        "Javadoc issues in " + fileName + ":\n" + errors);
  }

  @Test
  void verifyAllSourceFilesExist() {
    for (String fileName : SOURCE_FILES) {
      Path filePath = SOURCE_DIR.resolve(fileName);
      assertTrue(Files.exists(filePath),
          "Source file should exist: " + fileName);
    }
  }

  @Test
  void verifyAllTenFilesChecked() {
    assertEquals(10, SOURCE_FILES.size(),
        "Should check exactly 10 source files");
  }

  /**
   * Finds the Javadoc comment immediately before the given position in the
   * content.
   *
   * @param content  the source file content
   * @param position the position of the method/constructor declaration
   * @return the Javadoc comment text, or null if none found
   */
  private String findJavadocBefore(String content, int position) {
    // Look back up to 800 characters for a Javadoc comment
    int searchStart = Math.max(0, position - 800);
    String searchArea = content.substring(searchStart, position);

    Matcher javadocMatcher = JAVADOC_PATTERN.matcher(searchArea);
    String lastJavadoc = null;
    int lastJavadocEnd = -1;

    while (javadocMatcher.find()) {
      lastJavadoc = javadocMatcher.group();
      lastJavadocEnd = javadocMatcher.end();
    }

    if (lastJavadoc == null) {
      return null;
    }

    // Verify the Javadoc is close to the method (only whitespace/annotations
    // between)
    String between = searchArea.substring(lastJavadocEnd).trim();
    // Remove annotations (including @Override)
    between = between.replaceAll("@\\w+(?:\\([^)]*\\))?\\s*", "").trim();

    // Should be empty or start with method modifiers
    if (between.isEmpty() || between.startsWith("public")) {
      return lastJavadoc;
    }

    return null;
  }
}
