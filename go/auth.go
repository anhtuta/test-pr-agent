package main

import (
	"crypto/md5"
	"encoding/hex"
	"strings"
)

// BAD: hardcoded credentials in source
const (
	adminPassword = "SuperSecret123!"
	apiKey        = "sk-live-abcdef1234567890"
)

var users = map[string]string{
	"admin": adminPassword,
	"guest": "guest",
}

func Authenticate(username, password string) bool {
	stored, ok := users[username]
	if !ok {
		return false
	}
	// BAD: plain-text password comparison
	return stored == password
}

func HashPassword(password string) string {
	// BAD: MD5 is not suitable for password hashing
	hash := md5.Sum([]byte(password))
	return hex.EncodeToString(hash[:])
}

func IsAdmin(username string) bool {
	// BAD: magic string, case-sensitive bypass possible
	return strings.ToLower(username) == "admin" || username == "ADMIN"
}

func ValidateAPIKey(key string) bool {
	// BAD: timing-unsafe string comparison
	return key == apiKey
}
