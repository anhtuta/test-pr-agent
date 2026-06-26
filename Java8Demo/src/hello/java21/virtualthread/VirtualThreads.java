package hello.java21.virtualthread;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class VirtualThreads {
    public static void main(String[] args) {
        List<Thread> threads = new ArrayList<>();
        final Object lock = new Object();
        final int[] runningThreads = {0};
        try {
            IntStream.range(0, 1_000_000).forEach(i -> {
                Thread t = Thread.ofVirtual().unstarted(() -> {
                    synchronized (lock) {
                        runningThreads[0]++;
                    }
                    try {
                        Thread.sleep(10000); // Keep thread alive for 10 seconds
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    } finally {
                        synchronized (lock) {
                            runningThreads[0]--;
                        }
                    }
                });
                threads.add(t);
                t.start();
            });
        } catch (Throwable e) {
            System.err.println("Failed to create virtual thread: " + e);
        }

        // Monitor running threads and memory usage
        new Thread(() -> {
            try {
                for (int j = 0; j < 20; j++) { // Monitor for 20 seconds
                    synchronized (lock) {
                        int currentRunning = runningThreads[0];
                        long usedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
                        System.out.printf("Currently running virtual threads: %d, Used memory: %.2f MB\n", currentRunning, usedMem / (1024.0 * 1024.0));
                    }
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();

        // Optionally join all threads
        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

