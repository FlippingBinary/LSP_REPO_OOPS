package org.howard.edu.lsp.example.question2;

import java.util.ArrayList;
import java.util.List;

/**
 * Driver demonstrating the Template Method pattern with polymorphism.
 *
 * <p>Creates a list of {@link Report} instances, iterates over them, and
 * calls {@link Report#generateReport()} on each — showing that the same
 * method invocation produces different output depending on the concrete
 * subclass.</p>
 *
 * @author Jon Musselwhite
 */
public class Driver {

  /**
   * Entry point. Builds a polymorphic list of reports and generates each one.
   *
   * @param args command-line arguments (unused)
   */
  public static void main(String[] args) {
    List<Report> reports = new ArrayList<>();
    reports.add(new StudentReport());
    reports.add(new CourseReport());

    for (Report report : reports) {
      report.generateReport();
    }
  }
}
