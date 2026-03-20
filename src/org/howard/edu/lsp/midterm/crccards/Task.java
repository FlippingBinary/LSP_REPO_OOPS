package org.howard.edu.lsp.midterm.crccards;

import java.util.Set;

/**
 * Represents a task with an identifier, description, and status.
 *
 * Valid status values are {@code OPEN}, {@code IN_PROGRESS}, and
 * {@code COMPLETE}. Any other value supplied to {@link #setStatus(String)}
 * causes the status to become {@code UNKNOWN}.
 *
 * @author Jon Musselwhite
 */
public class Task {
  private static final Set<String> VALID_STATUSES = Set.of("OPEN", "IN_PROGRESS", "COMPLETE");

  private final String taskId;
  private final String description;
  private String status;

  /**
   * Constructs a new task with the given identifier and description.
   * The initial status is {@code OPEN}.
   *
   * @param taskId      the unique identifier for this task
   * @param description a brief description of the task
   */
  public Task(String taskId, String description) {
    this.taskId = taskId;
    this.description = description;
    this.status = "OPEN";
  }

  /**
   * Returns the task identifier.
   *
   * @return the unique identifier for this task
   */
  public String getTaskId() {
    return taskId;
  }

  /**
   * Returns the task description.
   *
   * @return a brief description of the task
   */
  public String getDescription() {
    return description;
  }

  /**
   * Returns the current status of the task.
   *
   * @return the task status
   */
  public String getStatus() {
    return status;
  }

  /**
   * Sets the status of the task. If the supplied value is not one of
   * {@code OPEN}, {@code IN_PROGRESS}, or {@code COMPLETE}, the status
   * is set to {@code UNKNOWN}. Comparison is case-sensitive.
   *
   * @param status the new status value
   */
  public void setStatus(String status) {
    this.status = VALID_STATUSES.contains(status) ? status : "UNKNOWN";
  }

  /**
   * Returns a string representation of this task in the format
   * {@code taskId description [status]}.
   *
   * @return the formatted task string
   */
  @Override
  public String toString() {
    return taskId + " " + description + " [" + status + "]";
  }
}
