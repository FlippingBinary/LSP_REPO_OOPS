package org.howard.edu.lsp.midterm.crccards;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Manages a collection of {@link Task} objects, supporting addition,
 * lookup by identifier, and retrieval by status.
 *
 * Internally, tasks are stored in a {@link LinkedHashMap} keyed by
 * task ID to provide constant-time lookup while preserving insertion
 * order.
 *
 * @author Jon Musselwhite
 */
public class TaskManager {
  private final Map<String, Task> tasks = new LinkedHashMap<>();

  /**
   * Adds a task to this manager.
   *
   * @param task the task to add
   * @throws IllegalArgumentException if a task with the same ID already exists
   */
  public void addTask(Task task) {
    if (tasks.containsKey(task.getTaskId())) {
      throw new IllegalArgumentException(
          "Duplicate task ID: " + task.getTaskId());
    }
    tasks.put(task.getTaskId(), task);
  }

  /**
   * Finds and returns the task with the specified identifier.
   *
   * @param taskId the identifier of the task to find
   * @return the matching {@link Task}, or {@code null} if no task with
   *         the given ID exists
   */
  public Task findTask(String taskId) {
    return tasks.get(taskId);
  }

  /**
   * Returns a list of all tasks whose current status matches the
   * specified value. Comparison is case-sensitive.
   *
   * @param status the status to filter by
   * @return a list of tasks with the given status; may be empty but
   *         never {@code null}
   */
  public List<Task> getTasksByStatus(String status) {
    List<Task> result = new ArrayList<>();
    for (Task task : tasks.values()) {
      if (task.getStatus().equals(status)) {
        result.add(task);
      }
    }
    return result;
  }
}
