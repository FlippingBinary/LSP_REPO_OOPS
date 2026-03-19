# Development Log - Q1

I used `opencode` to create a starting point since AI tools were explicitly allowed
and we had limited time to complete the assignment.

## `opencode` Transcript

```md
[analyze-mode]
ANALYSIS MODE. Gather context before diving deep:
CONTEXT GATHERING (parallel):
- 1-2 explore agents (codebase patterns, implementations)
- 1-2 librarian agents (if external library involved)
- Direct tools: Grep, AST-grep, LSP for targeted searches
IF COMPLEX - DO NOT STRUGGLE ALONE. Consult specialists:
- **Oracle**: Conventional problems (architecture, debugging, complex logic)
- **Artistry**: Non-conventional problems (different approach needed)
SYNTHESIZE findings before proceeding.
---
Please review `src/org/howard/edu/lsp/midterm/crccards/INSTRUCTIONS.md` and create the described `.java` and `.md` files.
jon
I detect implementation intent — the user wants me to read instructions and create Java and Markdown files as described. Let me start by reading the instructions.
→ Read src/org/howard/edu/lsp/midterm/crccards/INSTRUCTIONS.md 
Now let me check existing code patterns in this package to match style, and see the existing assignment4 CRC cards for any related patterns:
→ Read src/org/howard/edu/lsp/assignment4/doc/crc_cards.md 
→ Read src/org/howard/edu/lsp/assignment3/Product.java 
→ Read src/org/howard/edu/lsp/assignment3/doc/REFLECTION_A3.md 
→ Read build.gradle 
Good. I have full context now. Let me also check the mise.toml to understand how assignments are run:
→ Read mise.toml 
Now I have full context. Let me create the todo list and implement everything.
[ ] Create Task.java with fields, constructor, getters, setStatus (with UNKNOWN fallback), toString
[ ] Create TaskManager.java with Map-based storage, addTask, findTask, getTasksByStatus
[ ] Create Driver.java (provided, verbatim)
[ ] Create doc/crccards.md explaining collaboration direction
[ ] Build and run to verify expected output matches
[ ] Create Task.java with fields, constructor, getters, setStatus (with UNKNOWN fallback), toString
[ ] Create TaskManager.java with Map-based storage, addTask, findTask, getTasksByStatus
[ ] Create Driver.java (provided, verbatim)
[ ] Create doc/crccards.md explaining collaboration direction
[ ] Build and run to verify expected output matches
Codebase style: disciplined — 2-space indentation, private final fields, full Javadoc with @param/@return/@throws. I'll match exactly.
Creating all four files in parallel:
← Wrote /home/jon/git/FlippingBinary/LSP_REPO_OOPS/src/org/howard/edu/lsp/midterm/crccards/Task.java
package org.howard.edu.lsp.midterm.crccards;
import java.util.Set;
/**
 * Represents a task with an identifier, description, and status.
 *
 * <p>Valid status values are {@code OPEN}, {@code IN_PROGRESS}, and
 * {@code COMPLETE}. Any other value supplied to {@link #setStatus(String)}
 * causes the status to become {@code UNKNOWN}.
 *
 * @author Jon Musselwhite
 */
public class Task {
  private static final Set<String> VALID_STATUSES =
      Set.of("OPEN", "IN_PROGRESS", "COMPLETE");
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
← Wrote /home/jon/git/FlippingBinary/LSP_REPO_OOPS/src/org/howard/edu/lsp/midterm/crccards/TaskManager.java
package org.howard.edu.lsp.midterm.crccards;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
/**
 * Manages a collection of {@link Task} objects, supporting addition,
 * lookup by identifier, and retrieval by status.
 *
 * <p>Internally, tasks are stored in a {@link LinkedHashMap} keyed by
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
← Wrote /home/jon/git/FlippingBinary/LSP_REPO_OOPS/src/org/howard/edu/lsp/midterm/crccards/Driver.java
package org.howard.edu.lsp.midterm.crccards;
import java.util.List;
public class Driver {
    public static void main(String[] args) {
        TaskManager manager = new TaskManager();
        Task t1 = new Task("T1", "Write report");
        Task t2 = new Task("T2", "Study for exam");
        Task t3 = new Task("T3", "Submit homework");
        manager.addTask(t1);
        manager.addTask(t2);
        manager.addTask(t3);
        t2.setStatus("IN_PROGRESS");
        System.out.println("OPEN TASKS");
        List<Task> openTasks = manager.getTasksByStatus("OPEN");
        for (Task t : openTasks) {
            System.out.println(t.toString());
        }
        System.out.println("\nTESTING DUPLICATE TASK ID");
        try {
            Task duplicate = new Task("T1", "Duplicate task");
            manager.addTask(duplicate);
        } catch (IllegalArgumentException e) {
            System.out.println("Duplicate task ID detected.");
        }
        System.out.println("\nTESTING INVALID STATUS");
        Task t4 = new Task("T4", "Invalid status test");
        t4.setStatus("DONE");
        System.out.println(t4.toString());
        System.out.println("\nTESTING FIND TASK");
        Task found = manager.findTask("T2");
        System.out.println(found);
        Task notFound = manager.findTask("T99");
        if (notFound == null) {
            System.out.println("Task not found.");
        }
    }
}
# Create doc directory for crccards
$ export CI=true DEBIAN_FRONTEND=noninteractive GIT_TERMINAL_PROMPT=0 GCM_INTERACTIVE=never HOMEBREW_NO_AUTO_UPDATE=1 GIT_EDITOR=: EDITOR=: VISUAL='' GIT_SEQUENCE_EDITOR=: GIT_MERGE_AUTOEDIT=no GIT_PAGER=cat PAGER=cat npm_config_yes=true PIP_NO_INPUT=1 YARN_ENABLE_IMMUTABLE_INSTALLS=false; mkdir -p /home/jon/git/FlippingBinary/LSP_REPO_OOPS/src/org/howard/edu/lsp/midterm/crccards/doc
Re: Javadoc comments — These are necessary and explicitly required by the assignment instructions: "All classes and public methods must include Javadoc comments" with "a brief description of the class and author name" and "@param descriptions where applicable, and @return descriptions where applicable." This matches the existing codebase pattern (see assignment3/Product.java). Keeping them.
Now writing the doc file:
← Wrote /home/jon/git/FlippingBinary/LSP_REPO_OOPS/src/org/howard/edu/lsp/midterm/crccards/doc/crccards.md
# CRC Card Collaboration
`TaskManager` collaborates with `Task` because its responsibilities — storing
tasks, adding new tasks, finding a task by ID, and returning tasks by status —
all require it to create, hold, and query `Task` objects. `Task`, on the other
hand, is only responsible for storing its own data and updating its own status,
so it never needs to know that a `TaskManager` exists. This one-way dependency
reflects the principle that a data-bearing class should not depend on the
manager that aggregates it.
[✓] Create Task.java with fields, constructor, getters, setStatus (with UNKNOWN fallback), toString
[✓] Create TaskManager.java with Map-based storage, addTask, findTask, getTasksByStatus
[✓] Create Driver.java (provided, verbatim)
[ ] Create doc/crccards.md explaining collaboration direction
[ ] Build and run to verify expected output matches
Now let me run diagnostics and build+run:
⚙ lsp_diagnostics [filePath=/home/jon/git/FlippingBinary/LSP_REPO_OOPS/src/org/howard/edu/lsp/midterm/crccards/Task.java]
⚙ lsp_diagnostics [filePath=/home/jon/git/FlippingBinary/LSP_REPO_OOPS/src/org/howard/edu/lsp/midterm/crccards/TaskManager.java]
⚙ lsp_diagnostics [filePath=/home/jon/git/FlippingBinary/LSP_REPO_OOPS/src/org/howard/edu/lsp/midterm/crccards/Driver.java]
# Build the project with Gradle
$ export CI=true DEBIAN_FRONTEND=noninteractive GIT_TERMINAL_PROMPT=0 GCM_INTERACTIVE=never HOMEBREW_NO_AUTO_UPDATE=1 GIT_EDITOR=: EDITOR=: VISUAL='' GIT_SEQUENCE_EDITOR=: GIT_MERGE_AUTOEDIT=no GIT_PAGER=cat PAGER=cat npm_config_yes=true PIP_NO_INPUT=1 YARN_ENABLE_IMMUTABLE_INSTALLS=false; mise run build 2>&1
[build] $ gradle build
Starting a Gradle Daemon (subsequent builds will be faster)
Reusing configuration cache.
> Task :processTestResources NO-SOURCE
> Task :processResources NO-SOURCE
> Task :compileJava
> Task :classes
> Task :jar
> Task :assemble
> Task :compileTestJava
> Task :testClasses
> Task :test
> Task :check
> Task :build
BUILD SUCCESSFUL in 4s
4 actionable tasks: 4 executed
Configuration cache entry reused.
Zero diagnostics, build passes. Now run the Driver:
# Run Driver to verify expected output
$ export CI=true DEBIAN_FRONTEND=noninteractive GIT_TERMINAL_PROMPT=0 GCM_INTERACTIVE=never HOMEBREW_NO_AUTO_UPDATE=1 GIT_EDITOR=: EDITOR=: VISUAL='' GIT_SEQUENCE_EDITOR=: GIT_MERGE_AUTOEDIT=no GIT_PAGER=cat PAGER=cat npm_config_yes=true PIP_NO_INPUT=1 YARN_ENABLE_IMMUTABLE_INSTALLS=false; java -cp /home/jon/git/FlippingBinary/LSP_REPO_OOPS/build/classes/java/main org.howard.edu.lsp.midterm.crccards.Driver 2>&1
OPEN TASKS
T1 Write report [OPEN]
T3 Submit homework [OPEN]
TESTING DUPLICATE TASK ID
Duplicate task ID detected.
TESTING INVALID STATUS
T4 Invalid status test [UNKNOWN]
TESTING FIND TASK
T2 Study for exam [IN_PROGRESS]
Task not found.
Output matches the expected output exactly.
[✓] Create Task.java with fields, constructor, getters, setStatus (with UNKNOWN fallback), toString
[✓] Create TaskManager.java with Map-based storage, addTask, findTask, getTasksByStatus
[✓] Create Driver.java (provided, verbatim)
[✓] Create doc/crccards.md explaining collaboration direction
[✓] Build and run to verify expected output matches
All done. Here's what was created:
| File | Purpose |
|---|---|
| Task.java | Data class with taskId, description, status; validates status against OPEN/IN_PROGRESS/COMPLETE (falls back to UNKNOWN); toString() formats as taskId description [status] |
| TaskManager.java | Manages tasks in a LinkedHashMap<String, Task> — constant-time lookup, insertion-order preserved, duplicate IDs throw IllegalArgumentException |
| Driver.java | Provided driver, verbatim copy |
| doc/crccards.md | Explains why TaskManager→Task is one-way: TaskManager's responsibilities require Task objects, but Task's responsibilities are self-contained |
Verification: Build succeeds, zero LSP diagnostics, runtime output matches expected output character-for-character.
```
