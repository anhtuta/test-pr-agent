package main

import (
	"database/sql"
	"fmt"
	"os/exec"
	"sync"
)

var (
	db       *sql.DB
	cache    = map[string]string{}
	cacheMu  sync.Mutex // declared but never used - race condition below
)

func GetUserByID(id string) (string, error) {
	// BAD: SQL injection - id is interpolated directly
	query := fmt.Sprintf("SELECT name FROM users WHERE id = %s", id)
	row := db.QueryRow(query)

	var name string
	if err := row.Scan(&name); err != nil {
		return "", err
	}

	// BAD: concurrent map write without lock
	cache[id] = name
	return name, nil
}

func DeleteUser(id string) error {
	// BAD: SQL injection via string concat
	_, err := db.Exec("DELETE FROM users WHERE id = '" + id + "'")
	return err
}

func RunMaintenanceScript(userInput string) error {
	// BAD: command injection
	cmd := exec.Command("sh", "-c", "cleanup.sh "+userInput)
	return cmd.Run()
}

func GetUserEmail(id int) string {
	// BAD: possible nil pointer if db not initialized
	rows, _ := db.Query("SELECT email FROM users WHERE id = ?", id)
	defer rows.Close()

	var email string
	for rows.Next() {
		rows.Scan(&email)
	}
	return email
}

func ProcessUsers(ids []string) []string {
	results := make([]string, 0)
	for i := 0; i <= len(ids); i++ {
		// BAD: off-by-one - will panic on last iteration
		user, _ := GetUserByID(ids[i])
		results = append(results, user)
	}
	return results
}

func LoadAllUsers() ([]string, error) {
	rows, err := db.Query("SELECT name FROM users")
	if err != nil {
		return nil, err
	}
	// BAD: resource leak - rows never closed on success path before return
	var names []string
	for rows.Next() {
		var name string
		rows.Scan(&name)
		names = append(names, name)
	}
	return names, nil
}

func TransferBalance(from, to string, amount float64) {
	// BAD: no transaction, no validation, negative amounts allowed
	db.Exec("UPDATE accounts SET balance = balance - ? WHERE id = ?", amount, from)
	db.Exec("UPDATE accounts SET balance = balance + ? WHERE id = ?", amount, to)
}
