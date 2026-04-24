package org.howard.edu.lsp.example.question2;

/**
 * A concrete report that displays student information.
 *
 * <p>Loads a student name and GPA in {@link #loadData()} and formats
 * them using the template workflow defined by {@link Report}.</p>
 *
 * @author Jon Musselwhite
 */
public class StudentReport extends Report {

  private String studentName;
  private double gpa;

  /** {@inheritDoc} */
  @Override
  protected void loadData() {
    studentName = "John Doe";
    gpa = 3.8;
  }

  /** {@inheritDoc} */
  @Override
  protected String formatHeader() {
    return "Student Report";
  }

  /** {@inheritDoc} */
  @Override
  protected String formatBody() {
    return "Student Name: " + studentName + "\nGPA: " + gpa;
  }

  /** {@inheritDoc} */
  @Override
  protected String formatFooter() {
    return "End of Student Report";
  }
}
