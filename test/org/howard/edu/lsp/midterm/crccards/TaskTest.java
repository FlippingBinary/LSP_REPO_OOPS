package org.howard.edu.lsp.midterm.crccards;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

  @Test
  void constructor_setsTaskIdAndDescription() {
    Task task = new Task("T1", "Write report");
    assertEquals("T1", task.getTaskId());
    assertEquals("Write report", task.getDescription());
  }

  @Test
  void constructor_defaultStatusIsOpen() {
    Task task = new Task("T1", "Write report");
    assertEquals("OPEN", task.getStatus());
  }

  @ParameterizedTest(name = "setStatus accepts valid status \"{0}\"")
  @ValueSource(strings = {"OPEN", "IN_PROGRESS", "COMPLETE"})
  void setStatus_acceptsValidStatuses(String status) {
    Task task = new Task("T1", "Write report");
    task.setStatus(status);
    assertEquals(status, task.getStatus());
  }

  @ParameterizedTest(name = "setStatus rejects invalid status \"{0}\" and sets UNKNOWN")
  @ValueSource(strings = {"DONE", "open", "in_progress", "complete", "", "CLOSED", "pending"})
  void setStatus_invalidStatusBecomesUnknown(String status) {
    Task task = new Task("T1", "Write report");
    task.setStatus(status);
    assertEquals("UNKNOWN", task.getStatus());
  }

  @Test
  void setStatus_isCaseSensitive() {
    Task task = new Task("T1", "Write report");

    task.setStatus("open");
    assertEquals("UNKNOWN", task.getStatus());

    task.setStatus("Open");
    assertEquals("UNKNOWN", task.getStatus());

    task.setStatus("OPEN");
    assertEquals("OPEN", task.getStatus());
  }

  @Test
  void toString_producesCorrectFormat() {
    Task task = new Task("T1", "Write report");
    assertEquals("T1 Write report [OPEN]", task.toString());
  }

  @Test
  void toString_reflectsCurrentStatus() {
    Task task = new Task("T1", "Write report");

    task.setStatus("IN_PROGRESS");
    assertEquals("T1 Write report [IN_PROGRESS]", task.toString());

    task.setStatus("COMPLETE");
    assertEquals("T1 Write report [COMPLETE]", task.toString());
  }

  @Test
  void toString_reflectsUnknownStatus() {
    Task task = new Task("T4", "Invalid status test");
    task.setStatus("DONE");
    assertEquals("T4 Invalid status test [UNKNOWN]", task.toString());
  }

  @Test
  void getTaskId_returnsConstructorValue() {
    Task task = new Task("ABC-123", "Some task");
    assertEquals("ABC-123", task.getTaskId());
  }

  @Test
  void getDescription_returnsConstructorValue() {
    Task task = new Task("T1", "A description");
    assertEquals("A description", task.getDescription());
  }
}
