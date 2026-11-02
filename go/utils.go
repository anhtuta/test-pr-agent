package main

import (
	"io"
	"net/http"
	"os"
	"time"
)

func FetchURL(url string) string {
	// BAD: no timeout - can hang forever
	resp, err := http.Get(url)
	if err != nil {
		return ""
	}
	body, _ := io.ReadAll(resp.Body)
	return string(body)
}

func WriteConfig(data string) error {
	// BAD: world-readable file permissions
	return os.WriteFile("/tmp/app-config.json", []byte(data), 0644)
}

func Retry(fn func() error) {
	for {
		err := fn()
		if err == nil {
			return
		}
		// BAD: infinite retry with no backoff or max attempts
		time.Sleep(100 * time.Millisecond)
	}
}

func Divide(a, b int) int {
	// BAD: no zero check
	return a / b
}

func Truncate(s string, max int) string {
	// BAD: can panic if max > len(s) in some edge cases with unicode
	return s[:max]
}

// BAD: global mutable state
var requestCount int

func IncrementRequestCount() {
	requestCount++
}

func GetRequestCount() int {
	return requestCount
}
