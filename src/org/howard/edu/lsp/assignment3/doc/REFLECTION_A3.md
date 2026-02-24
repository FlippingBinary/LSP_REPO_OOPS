# Reflection

## What is different about the design?

Assignment 2 had seven classes in a flat structure. Assignment 3 has ten. The
three new classes are the `DataSource` interface and its two implementations,
`FileDataSource` and `StringDataSource`. These replaced the three overloaded
`extract()` methods in `CSVExtractor` (which previously accepted a `Path`, a
`String`, or a `BufferedReader`) with a single `extract(DataSource)` method. The
extractor no longer knows or cares where the data comes from; it just calls `source.open()`
and reads.

The other significant structural change is that `TransformedProduct` now extends
`Product` instead of duplicating its four fields. In assignment 2, both classes
independently declared `productId`, `name`, `price`, and `category` with their
own getters. In assignment 3, `TransformedProduct` calls `super(productId, name,
price, category)` and only adds the `priceRange` field.

A smaller change: `TransformedProduct.toCsvRow()` was removed. In assignment 2,
the data model class knew how to serialize itself to CSV, which mixed data representation
with output formatting. In assignment 3, the `CSVLoader` constructs the CSV row
directly from getter calls, keeping serialization logic in the class responsible
for output.

Finally, `ProductTransformer.determinePriceRange()` was changed from `public`
to `private`. It is an internal implementation detail of the transformation process,
not something callers should use directly. Assignment 2 exposed it publicly (and
tested it directly), while assignment 3 tests price range behavior through `transformProduct()`
instead.

Every class and every public method in assignment 3 has full Javadoc with `@param`,
`@return`, and `@throws` tags where applicable. Assignment 2 had only single-line
`/** ... */` comments on classes.

## How is Assignment 3 more object-oriented?

Assignment 2 was already structured around separate classes for each ETL stage,
but the classes were tightly coupled to specific input types and duplicated internal
structure.

Assignment 3 improves this in three ways: Inheritance, Polymorphism, and Encapsulation.

## Which OO ideas did you use?

**Inheritance**: `TransformedProduct extends Product` is a textbook IS-A relationship.
A transformed product is a product with one additional attribute. Rather than
copying four fields and four getters, the subclass inherits them and adds only
what is new. This eliminates roughly 20 lines of duplicated code and makes the
relationship between the two types explicit in the type hierarchy.

**Polymorphism**: The `DataSource` interface lets `CSVExtractor` work with any
data source without knowing the concrete type. `FileDataSource` reads from the
filesystem; `StringDataSource` reads from an in-memory string. The extractor calls
`source.open()` in both cases. This is runtime polymorphism — the same code path
handles different input types through a shared interface. It also made testing
cleaner: the tests use `StringDataSource` to pass CSV content directly, without
needing temporary files or separate string-based overloads.

**Encapsulation**: All fields across every class are `private final` with access
through getters, which was already true in assignment 2. What changed is that
encapsulation was strengthened by hiding `determinePriceRange()` (making it `private`)
and removing `toCsvRow()` from `TransformedProduct`. These changes ensure that
internal implementation details are not part of the public API. The Javadoc documentation
on every public method also supports encapsulation by defining the contract without
exposing how the method works internally.

## How I Tested That Assignment 3 Works the Same as Assignment 2

In my development branch, I already had tests for validating the behavior of the
code. The assignment 3 tests mirror the assignment 2 tests case for case. Every
test in `CSVExtractorTest`, `ProductTransformerTest`, and `ETLPipelineTest` verifies
the same behavior — same inputs, same expected outputs, same edge cases (empty
lines, wrong field counts, invalid IDs, invalid prices, whitespace trimming, rounding,
discount logic, category reclassification, and price range boundaries).

The key difference in the test code is how input is provided. Assignment 2's `CSVExtractorTest`
called `extractor.extract(csvString)` using the string overload. Assignment 3's
version wraps the same string in `new StringDataSource(csv)` and calls `extractor.extract(source)`.
The test data and assertions are identical.

For `ProductTransformerTest`, the assignment 2 tests directly called `determinePriceRange()`
because it was public. Since assignment 3 made that method private, the price
range tests instead create a `Product`, run it through `transformProduct()`, and
check `getPriceRange()` on the result. This actually tests the price range logic
in context rather than in isolation, which better reflects how the code is used.

The `ETLPipelineTest` is a full integration test in both assignments. It runs
the pipeline against the real `data/products.csv` input file, writes to a temp
directory, and compares the output byte-for-byte against `data/transformed_products.csv`.
Both versions pass with the same expected output file, confirming that the refactored
code produces identical results.

Assignment 3 also adds a `JavadocTest` class that was not in assignment 2. It
reads every source file as text, uses regex matching to find class declarations,
public methods, and constructors, and verifies that each one has a Javadoc comment
with the correct `@param`, `@return`, and `@throws` tags. This ensures the documentation
requirement is met programmatically rather than by manual inspection.
