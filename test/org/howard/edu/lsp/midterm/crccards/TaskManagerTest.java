package org.howard.edu.lsp.midterm.crccards;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskManagerTest {
  private TaskManager manager;

  @BeforeEach
  void setUp() {
    manager = new TaskManager();
  }

  @Test
  void addTask_storesTaskForLaterRetrieval() {
    Task task = new Task("T1", "Write report");
    manager.addTask(task);

    Task found = manager.findTask("T1");
    assertNotNull(found);
    assertSame(task, found);
  }

  @Test
  void addTask_duplicateIdThrowsIllegalArgumentException() {
    manager.addTask(new Task("T1", "Write report"));

    assertThrows(IllegalArgumentException.class, () ->
        manager.addTask(new Task("T1", "Duplicate task")));
  }

  @Test
  void addTask_differentIdsAllowed() {
    manager.addTask(new Task("T1", "Task one"));
    manager.addTask(new Task("T2", "Task two"));
    manager.addTask(new Task("T3", "Task three"));

    assertNotNull(manager.findTask("T1"));
    assertNotNull(manager.findTask("T2"));
    assertNotNull(manager.findTask("T3"));
  }

  @Test
  void findTask_returnsNullForNonexistentId() {
    manager.addTask(new Task("T1", "Write report"));

    assertNull(manager.findTask("T99"));
  }

  @Test
  void findTask_returnsNullFromEmptyManager() {
    assertNull(manager.findTask("T1"));
  }

  @Test
  void findTask_returnsCorrectTaskById() {
    Task t1 = new Task("T1", "Write report");
    Task t2 = new Task("T2", "Study for exam");
    manager.addTask(t1);
    manager.addTask(t2);

    assertSame(t2, manager.findTask("T2"));
  }

  @Test
  void getTasksByStatus_returnsMatchingTasks() {
    Task t1 = new Task("T1", "Write report");
    Task t2 = new Task("T2", "Study for exam");
    Task t3 = new Task("T3", "Submit homework");
    manager.addTask(t1);
    manager.addTask(t2);
    manager.addTask(t3);

    t2.setStatus("IN_PROGRESS");

    List<Task> openTasks = manager.getTasksByStatus("OPEN");
    assertEquals(2, openTasks.size());
    assertTrue(openTasks.contains(t1));
    assertTrue(openTasks.contains(t3));
  }

  @Test
  void getTasksByStatus_returnsEmptyListWhenNoneMatch() {
    manager.addTask(new Task("T1", "Write report"));

    List<Task> completeTasks = manager.getTasksByStatus("COMPLETE");
    assertNotNull(completeTasks);
    assertTrue(completeTasks.isEmpty());
  }

  @Test
  void getTasksByStatus_returnsEmptyListFromEmptyManager() {
    List<Task> tasks = manager.getTasksByStatus("OPEN");
    assertNotNull(tasks);
    assertTrue(tasks.isEmpty());
  }

  @Test
  void getTasksByStatus_isCaseSensitive() {
    manager.addTask(new Task("T1", "Write report"));

    List<Task> result = manager.getTasksByStatus("open");
    assertTrue(result.isEmpty());
  }

  @Test
  void getTasksByStatus_reflectsStatusChanges() {
    Task t1 = new Task("T1", "Write report");
    manager.addTask(t1);

    assertEquals(1, manager.getTasksByStatus("OPEN").size());
    assertEquals(0, manager.getTasksByStatus("COMPLETE").size());

    t1.setStatus("COMPLETE");

    assertEquals(0, manager.getTasksByStatus("OPEN").size());
    assertEquals(1, manager.getTasksByStatus("COMPLETE").size());
  }

  @Test
  void driverScenario_matchesExpectedOutput() {
    Task t1 = new Task("T1", "Write report");
    Task t2 = new Task("T2", "Study for exam");
    Task t3 = new Task("T3", "Submit homework");

    manager.addTask(t1);
    manager.addTask(t2);
    manager.addTask(t3);

    t2.setStatus("IN_PROGRESS");

    List<Task> openTasks = manager.getTasksByStatus("OPEN");
    assertEquals(2, openTasks.size());
    assertEquals("T1 Write report [OPEN]", openTasks.get(0).toString());
    assertEquals("T3 Submit homework [OPEN]", openTasks.get(1).toString());

    assertThrows(IllegalArgumentException.class, () ->
        manager.addTask(new Task("T1", "Duplicate task")));

    Task t4 = new Task("T4", "Invalid status test");
    t4.setStatus("DONE");
    assertEquals("T4 Invalid status test [UNKNOWN]", t4.toString());

    Task found = manager.findTask("T2");
    assertEquals("T2 Study for exam [IN_PROGRESS]", found.toString());

    assertNull(manager.findTask("T99"));
  }
}
