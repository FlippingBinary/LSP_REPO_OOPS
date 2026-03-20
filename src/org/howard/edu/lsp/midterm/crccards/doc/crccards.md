# CRC Card Collaboration

`TaskManager` collaborates with `Task` because its responsibilities — "store tasks",
"add new tasks", "find a task by ID", and "return tasks by status" — all require
it to create, hold, and query `Task` objects. `Task`, on the other hand, needs
only its own data to fulfill its list of responsibilities — "store task information,"
"update task status," and "provide task details." It never even needs to know
that a `TaskManager` exists.
