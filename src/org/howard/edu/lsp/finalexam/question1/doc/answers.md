Part 1:

Shared Resource #1:
The `nextId` field — an integer counter read and incremented by every thread that
calls `addRequest()`.

Shared Resource #2:
The `requests` list — an `ArrayList` that multiple threads append to concurrently.

Concurrency Problem:
A race condition can occur. Two threads may interleave their reads and writes of
`nextId`, producing duplicate IDs, or interleave their `ArrayList.add()` calls,
corrupting the list or losing entries.

Why `addRequest()` is unsafe:
`addRequest()` performs a compound action that is not atomic: it calls `getNextId()`
(read-then-increment of `nextId`) and then calls `requests.add()`. Between these
steps another thread can execute the same sequence, causing concurrent modification
of the unsynchronized `ArrayList` and mismatched IDs.

Part 2:

Fix A:
Not correct. Synchronizing only `getNextId()` protects the ID counter,
but `addRequest()` still calls `getNextId()` and then `requests.add()` as two
separate unsynchronized steps. Another thread can interleave between the ID assignment
and the list insertion, so the `ArrayList` is still accessed concurrently without
protection.

Fix B:
Correct. Synchronizing `addRequest()` makes the entire sequence — reading
and incrementing `nextId` plus adding to `requests` — atomic with respect to other
threads calling `addRequest()`. Because `getNextId()` is only called from within
`addRequest()`, the single lock on the `RequestManager` instance protects both
shared resources.

Fix C:
Not correct. Synchronizing `getRequests()` only protects reading the list.
It does nothing to prevent race conditions during `addRequest()`, where `nextId`
is read/incremented and `requests` is mutated without synchronization.

Part 3:
No, `getNextId()` should not be public. According to Riel's heuristic that a class
should not expose implementation details that are not part of its public interface,
the ID generation mechanism is an internal concern of `RequestManager`. Making
it public allows external callers to increment the counter independently of adding
a request, which breaks encapsulation and can introduce ID gaps or misuse. It
should be private so that ID assignment is only performed through `addRequest()`.

Part 4:

Description:
Instead of using the `synchronized` keyword, we can use `java.util.concurrent.locks.ReentrantLock`
to ensure multiple threads wait until a lock is acquired before entering code
that could cause a race condition. That was the closest alternative to the `synchronized`
keyword that we covered in class.

Code Snippet:

```java
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class RequestManager {
    private int nextId = 1;
    private final List<String> requests = new ArrayList<>();
    private final ReentrantLock lock = new ReentrantLock();

    public void addRequest(String studentName) {
        lock.lock(); // Block until the lock is acquired
        try {
            int id = nextId++;
            String request = "Request-" + id + " from " + studentName;
            requests.add(request);
        } finally {
            lock.unlock(); // Always release in finally block to avoid deadlocks
        }
    }

    public List<String> getRequests() {
        lock.lock();
        try {
            // Return a defensive copy to prevent ConcurrentModificationException
            // if another thread calls addRequest while the caller iterates.
            return new ArrayList<>(requests);
        } finally {
            lock.unlock();
        }
    }
}
```
