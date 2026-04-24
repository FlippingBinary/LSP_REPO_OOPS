package org.howard.edu.lsp.example.question2;

/**
 * A concrete report that displays course enrollment information.
 *
 * <p>Loads a course name and enrollment count in {@link #loadData()} and
 * formats them using the template workflow defined by {@link Report}.</p>
 *
 * @author Jon Musselwhite
 */
public class CourseReport extends Report {

  private String courseName;
  private int enrollment;

  /** {@inheritDoc} */
  @Override
  protected void loadData() {
    courseName = "CSCI 363";
    enrollment = 45;
  }

  /** {@inheritDoc} */
  @Override
  protected String formatHeader() {
    return "Course Report";
  }

  /** {@inheritDoc} */
  @Override
  protected String formatBody() {
    return "Course: " + courseName + "\nEnrollment: " + enrollment;
  }

  /** {@inheritDoc} */
  @Override
  protected String formatFooter() {
    return "End of Course Report";
  }
}
