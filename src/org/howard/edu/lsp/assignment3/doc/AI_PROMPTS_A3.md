**Prompt**: Please review assignment 2 to determine if there are any ways to make it more object-oriented. Assignment 3 will be to redesign assignment 2 into multiple classes with clearer responsibilities (if possible). The program must still meet the exact same requirements as assignment 2 with the same inputs, same outputs, same transformations, same error handling, and same relative paths. Even if it is already as object-oriented as it reasonably can be, it will need one public class per file and javadocs for each class and all public methods.

---

**Response**: Unfortunately, the literal response was lost. The AI assistant didn't preserve the full conversation in the chat window. But it had asked several questions. Question 1 was asking for confirmation of the assignment naming convention. Question 2 asked about adding tests. Question 3 asked how aggressive of a refactor to make it. Question 4 asked if a task should be added to a taskfile (the task file is not part of the submission).

---

**Prompt**: Q1: Correct. Q2: The existing tests should remain the same because they are correct, but add tests to ensure the new javadoc requirement is fully met if you can. Q3: Use the minimum aggressiveness necessary to demonstrate all relevant object-oriented concepts, such as encapsulation, inheritance, and polymorphism. Q4: Yes.

---

**Response**:

# Draft: Assignment 3 — OOP Redesign of Assignment 2

## Analysis Summary

### Current Architecture (Assignment 2)
7 classes in `org.howard.edu.lsp.assignment2`:

| Class | Lines | Responsibilities | SRP Status |
|-------|-------|-----------------|------------|
| ETLPipeline | 75 | Orchestration, file validation, error handling, dependency wiring, output printing | ⚠️ 5 responsibilities |
| CSVExtractor | 90 | File I/O, string parsing, BufferedReader parsing, field validation, Product creation | ⚠️ 4-5 responsibilities |
| CSVLoader | 27 | Directory creation, CSV writing | ✅ Focused |
| Product | 35 | Data container (immutable DTO) | ✅ Focused |
| TransformedProduct | 46 | Data container + CSV serialization (toCsvRow) | ⚠️ 2 responsibilities |
| ProductTransformer | 59 | Batch iteration, price/name/category transformation, price range classification | ⚠️ 3 responsibilities |
| ExtractResult | 29 | Result container with stats | ✅ Focused |

### What's Already Good
- Clear ETL separation (Extract → Transform → Load)
- Immutable data objects (Product, ExtractResult)
- Constants extracted (thresholds, field counts)
- Well-tested (3 test files, ~25 test cases covering edge cases)
- Good error handling (validation skips bad rows, IOExceptions caught)

### OOP Improvement Opportunities

**1. TransformedProduct.toCsvRow() — Mixed Responsibility**
- Data model shouldn't know about CSV formatting
- Serialization belongs in the Loader

**2. ProductTransformer.transformProduct() — Multiple Transformations in One Method**
- Name uppercasing, price discounting, category reclassification, rounding, and price range — all in one method
- Each could be its own method or strategy, but this is a small program
- Could extract: NameTransformation, PriceTransformation, CategoryTransformation, PriceRangeClassifier

**3. CSVExtractor — Parsing + Validation + I/O**
- Three `extract()` overloads handle different input sources
- `parseProduct()` combines validation and construction
- Could separate: CSVReader (I/O) from ProductParser (validation + creation)

**4. ETLPipeline.run() — Orchestration + Error Handling + Printing**
- File existence check, two separate try-catches, summary printing
- Could extract summary printing, but may be over-engineering

**5. No Interfaces**
- No Extractor/Transformer/Loader interfaces
- Tight coupling in ETLPipeline (creates dependencies directly)

### Assessment: Is It Already OOP Enough?

**Verdict: It's reasonably well-designed but NOT maximally OOP.**

The current design already separates E, T, and L into distinct classes. The data models are clean.
However, there ARE legitimate improvements:
- Moving `toCsvRow()` out of TransformedProduct
- Extracting individual transformation steps as named methods
- Adding Javadocs (currently minimal single-line comments)

The biggest wins for an "OOP redesign" assignment would be:
1. **One public class per file** — Already satisfied ✅
2. **Javadocs** — Only single-line `/** comments */` exist, no @param/@return
3. **Better method decomposition** — Extract helper methods in transformer/extractor
4. **Move toCsvRow()** — Serialization out of data model

### Behavioral Contract (MUST PRESERVE)

**Input**: `data/products.csv` — 4 columns: ProductID,Name,Price,Category
**Output**: `data/transformed_products.csv` — 5 columns: ProductID,Name,Price,Category,PriceRange

**Transformations**:
1. Name → UPPERCASE
2. Electronics price × 0.90 (10% discount)
3. Price → 2 decimal places, HALF_UP rounding
4. Electronics with post-discount price > 500 → "Premium Electronics" category
5. PriceRange: ≤10="Low", ≤100="Medium", ≤500="High", >500="Premium"

**Validation** (rows skipped silently):
- Empty lines
- Wrong field count (≠4)
- Invalid ProductID (non-integer)
- Invalid Price (non-decimal)

**Summary output** (stdout):
```
Rows read (excluding header): N
Rows transformed: N
Rows skipped: N
Output file: data/transformed_products.csv
```

**Error messages**:
- "Error: Input file not found: {path}"
- "Error reading input file: {message}"
- "Error writing output file: {message}"

### Test Infrastructure
- JUnit 5.10.2 (JUnit Jupiter)
- Java 21
- Gradle build system
- 3 test files with ~25 test cases
- Tests use string-based CSV input for unit tests, temp dirs for integration

## Resolved Questions
- **Package name**: `org.howard.edu.lsp.assignment3` (confirmed by user)
- **Tests**: Keep existing tests as-is. Add new tests to verify Javadoc completeness.
- **Assignment 2 files**: Remain untouched.
- **Aggressiveness**: Minimum necessary to demonstrate encapsulation, inheritance, and polymorphism.

## OOP Concept Mapping (Oracle-Informed)

### Inheritance: TransformedProduct extends Product — NATURAL ✅
TransformedProduct duplicates all 4 fields of Product. It IS-A Product with one additional field
(priceRange) and one extra behavior (toCsvRow). Constructor chains with super(). Eliminates ~20 lines
of duplication. Textbook inheritance use case.

### Polymorphism: Where does it fit?
- **Polymorphic transformation steps (Strategy pattern)**: FORCED ❌ — Oracle says the 5 transformation
  steps are tightly coupled and sequential (discount before category check, rounding before price range).
  Would require mutable intermediate state and create 4-5 single-method classes for trivial logic.
- **Separate ETL interfaces (Extractor/Transformer/Loader)**: FORCED ❌ — Three interfaces each with one
  implementation and completely different signatures. Textbook interface bloat.
- **DataSource abstraction**: NATURAL ✅ — CSVExtractor already has 3 overloads for Path, String,
  BufferedReader. A DataSource interface with multiple implementations (FileDataSource, StringDataSource)
  is a clean polymorphism demonstration. The extractor then accepts a DataSource instead of overloads.

### Encapsulation: Already demonstrated but can be strengthened
- Product/TransformedProduct already have private final fields with getters
- Move toCsvRow() out of TransformedProduct (data model shouldn't know serialization format)
- Better method visibility in ProductTransformer (determinePriceRange could be private)

## Proposed Design (Minimum Changes)

### Class List for Assignment 3:
1. **Product** — Base class. Private final fields, getters, Javadoc. (modified from assignment2)
2. **TransformedProduct extends Product** — Adds priceRange. Inherits 4 fields via super(). No toCsvRow(). (inheritance demo)
3. **DataSource** (interface) — `BufferedReader open() throws IOException` (polymorphism demo)
4. **FileDataSource** implements DataSource — wraps Path (polymorphism demo)
5. **StringDataSource** implements DataSource — wraps String (polymorphism demo, used by tests)
6. **CSVExtractor** — accepts DataSource, parses CSV. Single extract(DataSource) method.
7. **ProductTransformer** — same logic, better method decomposition, Javadoc.
8. **CSVLoader** — absorbs toCsvRow() formatting logic. Writes CSV.
9. **ExtractResult** — unchanged except Javadoc.
10. **ETLPipeline** — orchestrator, main(). Uses FileDataSource.

### New class count: 10 (was 7, +3: DataSource interface, FileDataSource, StringDataSource)
### Removed: toCsvRow() from TransformedProduct → moved to CSVLoader

## Scope Boundaries
- INCLUDE: New package assignment3, all classes with Javadoc, inheritance, polymorphism, encapsulation demo
- INCLUDE: New Javadoc verification tests
- INCLUDE: mise.toml update for `mise run 3`
- EXCLUDE: Modifying assignment 1 or assignment 2 files
- EXCLUDE: Changing data files or build.gradle
- EXCLUDE: Changing any behavioral contract (inputs, outputs, transformations, error handling)

