package org.howard.edu.lsp.midterm.strategy;

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

class JavadocTest {
  private static final Path SOURCE_DIR = Paths.get("src", "org", "howard", "edu", "lsp", "midterm", "strategy");

  private static final List<String> SOURCE_FILES = List.of(
      "PricingStrategy.java",
      "PriceCalculator.java",
      "RegularPricing.java",
      "MemberPricing.java",
      "VipPricing.java",
      "HolidayPricing.java",
      "Driver.java");

  private static final Pattern JAVADOC_PATTERN = Pattern.compile("/\\*\\*.*?\\*/", Pattern.DOTALL);

  private static final Pattern CLASS_DECLARATION_PATTERN = Pattern
      .compile("^\\s*public\\s+(?:abstract\\s+)?(?:class|interface)\\s+(\\w+)", Pattern.MULTILINE);

  private static final Pattern PUBLIC_METHOD_PATTERN = Pattern.compile(
      "^\\s*public\\s+(?:static\\s+)?(?!class|interface)(?:<[^>]+>\\s+)?(\\S+)\\s+(\\w+)\\s*\\(([^)]*)\\)(?:\\s*throws\\s+([\\w,\\s]+))?\\s*[{;]",
      Pattern.MULTILINE);

  private static final Pattern PUBLIC_CONSTRUCTOR_PATTERN = Pattern
      .compile("^\\s*public\\s+(\\w+)\\s*\\(([^)]*)\\)(?:\\s*throws\\s+([\\w,\\s]+))?\\s*\\{", Pattern.MULTILINE);

  @ParameterizedTest(name = "File {0} has class-level Javadoc")
  @ValueSource(strings = {
      "PricingStrategy.java",
      "PriceCalculator.java",
      "RegularPricing.java",
      "MemberPricing.java",
      "VipPricing.java",
      "HolidayPricing.java",
      "Driver.java"
  })
  void verifyClassLevelJavadoc(String fileName) throws IOException {
    Path filePath = SOURCE_DIR.resolve(fileName);
    assertTrue(Files.exists(filePath), "Source file should exist: " + filePath);

    String content = Files.readString(filePath);

    Matcher classMatcher = CLASS_DECLARATION_PATTERN.matcher(content);
    assertTrue(classMatcher.find(), "Should find class/interface declaration in " + fileName);

    String className = classMatcher.group(1);
    int classPosition = classMatcher.start();

    String contentBeforeClass = content.substring(0, classPosition);

    Matcher javadocMatcher = JAVADOC_PATTERN.matcher(contentBeforeClass);
    boolean foundJavadoc = false;
    int lastJavadocEnd = -1;

    while (javadocMatcher.find()) {
      foundJavadoc = true;
      lastJavadocEnd = javadocMatcher.end();
    }

    assertTrue(foundJavadoc,
        "Class " + className + " in " + fileName + " should have Javadoc comment");

    String between = contentBeforeClass.substring(lastJavadocEnd).trim();
    between = between.replaceAll("@\\w+(?:\\([^)]*\\))?\\s*", "").trim();
    assertTrue(between.isEmpty() || between.startsWith("public") || between.startsWith("abstract"),
        "Javadoc should be immediately before class declaration in " + fileName +
            ", found: '" + between + "'");
  }

  @ParameterizedTest(name = "File {0} has complete method Javadoc")
  @ValueSource(strings = {
      "PricingStrategy.java",
      "PriceCalculator.java",
      "RegularPricing.java",
      "MemberPricing.java",
      "VipPricing.java",
      "HolidayPricing.java",
      "Driver.java"
  })
  void verifyPublicMethodJavadoc(String fileName) throws IOException {
    Path filePath = SOURCE_DIR.resolve(fileName);
    String content = Files.readString(filePath);

    List<String> missingJavadoc = new ArrayList<>();
    List<String> missingParamTags = new ArrayList<>();
    List<String> missingReturnTags = new ArrayList<>();
    List<String> missingThrowsTags = new ArrayList<>();

    Matcher methodMatcher = PUBLIC_METHOD_PATTERN.matcher(content);
    while (methodMatcher.find()) {
      String returnType = methodMatcher.group(1);
      String methodName = methodMatcher.group(2);
      String params = methodMatcher.group(3);
      String throwsClause = methodMatcher.group(4);
      int methodPosition = methodMatcher.start();

      String javadoc = findJavadocBefore(content, methodPosition);

      if (javadoc == null) {
        missingJavadoc.add(methodName + "(" + params + ")");
        continue;
      }

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
              missingParamTags.add(methodName + " -> @param " + paramName);
            }
          }
        }
      }

      if (!returnType.equals("void") && !javadoc.contains("@return")) {
        missingReturnTags.add(methodName + " (returns " + returnType + ")");
      }

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

    Matcher constructorMatcher = PUBLIC_CONSTRUCTOR_PATTERN.matcher(content);
    while (constructorMatcher.find()) {
      String constructorName = constructorMatcher.group(1);
      String params = constructorMatcher.group(2);
      String throwsClause = constructorMatcher.group(3);
      int constructorPosition = constructorMatcher.start();

      String javadoc = findJavadocBefore(content, constructorPosition);

      if (javadoc == null) {
        missingJavadoc.add("Constructor " + constructorName + "(" + params + ")");
        continue;
      }

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

  private String findJavadocBefore(String content, int position) {
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

    String between = searchArea.substring(lastJavadocEnd).trim();
    between = between.replaceAll("@\\w+(?:\\([^)]*\\))?\\s*", "").trim();

    if (between.isEmpty() || between.startsWith("public")) {
      return lastJavadoc;
    }

    return null;
  }
}
