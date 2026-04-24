package org.howard.edu.lsp.example.question2;

/**
 * Abstract base class for reports using the Template Method pattern.
 *
 * <p>The {@link #generateReport()} method defines the fixed workflow:
 * {@code loadData} &rarr; {@code formatHeader} &rarr; {@code formatBody}
 * &rarr; {@code formatFooter}. Subclasses override the abstract steps to
 * supply report-specific content.</p>
 *
 * @author Jon Musselwhite
 */
public abstract class Report {

  /**
   * Loads the data required by this report. Called first in the workflow.
   */
  protected abstract void loadData();

  /**
   * Returns the formatted header text for this report.
   *
   * @return the header string
   */
  protected abstract String formatHeader();

  /**
   * Returns the formatted body text for this report.
   *
   * @return the body string
   */
  protected abstract String formatBody();

  /**
   * Returns the formatted footer text for this report.
   *
   * @return the footer string
   */
  protected abstract String formatFooter();

  /**
   * Generates the full report by executing the template workflow:
   * load data, then print header, body, and footer sections.
   */
  public final void generateReport() {
    loadData();

    System.out.println("=== HEADER ===");
    System.out.println(formatHeader());
    System.out.println();
    System.out.println("=== BODY ===");
    System.out.println(formatBody());
    System.out.println();
    System.out.println("=== FOOTER ===");
    System.out.println();
    System.out.println(formatFooter());
  }
}
