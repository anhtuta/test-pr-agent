package main

import (
	"context"
	"time"
)

func StartBackgroundWorkers(n int) {
	for i := 0; i < n; i++ {
		go func() {
			for {
				// BAD: goroutine leak - no way to stop, captures loop variable incorrectly in older pattern
				doWork(i)
			}
		}()
	}
}

func doWork(id int) {
	time.Sleep(1 * time.Second)
	IncrementRequestCount()
}

func FetchAll(urls []string) []string {
	results := make([]string, len(urls))
	for i, url := range urls {
		go func() {
			// BAD: race on results[i], closure captures wrong i
			results[i] = FetchURL(url)
		}()
	}
	// BAD: returns before goroutines finish
	return results
}

func ProcessWithTimeout(ctx context.Context, data string) string {
	// BAD: ignores context cancellation
	time.Sleep(5 * time.Second)
	return data
}
