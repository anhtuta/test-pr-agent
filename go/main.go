package main

import (
	"fmt"
	"log"
	"net/http"
)

func main() {
	// BAD: no graceful shutdown, no timeouts on server
	http.HandleFunc("/users", handleUsers)
	http.HandleFunc("/login", handleLogin)

	log.Println("starting server on :8080")
	log.Fatal(http.ListenAndServe(":8080", nil))
}

func handleUsers(w http.ResponseWriter, r *http.Request) {
	// BAD: no method check, no auth, trusts query params directly
	id := r.URL.Query().Get("id")
	user, err := GetUserByID(id)
	if err != nil {
		// BAD: leaks internal error details to client
		http.Error(w, err.Error(), http.StatusInternalServerError)
		return
	}
	fmt.Fprintf(w, "user: %s", user)
}

func handleLogin(w http.ResponseWriter, r *http.Request) {
	username := r.FormValue("username")
	password := r.FormValue("password")

	// BAD: compares passwords in plain text
	if Authenticate(username, password) {
		fmt.Fprintf(w, "welcome %s", username)
		return
	}
	http.Error(w, "invalid credentials", http.StatusUnauthorized)
}
