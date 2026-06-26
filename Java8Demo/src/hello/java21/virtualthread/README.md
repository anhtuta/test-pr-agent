# Compare traditional threads and virtual threads in Java 21

Each class with create 1M threads, then start them. Each thread will sleep for 10 seconds to simulate work.

Why need to sleep? Because if the thread finish too quickly, the JVM will recycle the thread and we can't see the difference between traditional threads and virtual threads.

Result:

- Traditional threads: OutOfMemoryError
- Virtual threads: All threads started successfully

Ref: Copilot
