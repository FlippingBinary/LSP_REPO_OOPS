# LSP_REPO_OOPS

A Java console application with an assignment picker menu.

## Prerequisites

- Java 21 or later
- Gradle (or use the Gradle wrapper if available)

## Project Structure

```text
src/
└── org/howard/edu/lsp/
    ├── assignment1/
    │   └── HelloWorld.java                 # Assignment 1: Hello World
    └── assignment2/
        ├── data/
        │   ├── products.csv                # Input data
        │   └── transformed_products.csv    # Expected output
        └── ETLPipeline.java                # Assignment 2: ETL Pipeline
```

## Build and Run

This project uses [Mise-en-Place](https://mise.jdx.dev) to manage the development
environment and tasks.

Build the project:

```bash
mise run build
```

Run an assignment (automatically builds as well):

```bash
mise run 1  # runs assignment 1
mise run 2  # runs assignment 2
```
