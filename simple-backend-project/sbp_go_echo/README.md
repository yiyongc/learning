# Simple Backend Project using Go and Echo

This is a project connecting to postgresql and generating routes via the echo router library.

The basic design of this backend project is written in the base README file in the root directory.

# Getting Started

Prepare database by running migrations using golang-migrate

```
migrate -path migrations -database postgres://<username>:<password>@localhost:5432/<database_name>?sslmode=disable up
```


Run application

```
go run cmd/sbp-go-echo/sbp-go-echo.go
```

# Packages Used

- echo
- golang-migrate
- bun
- bun/driver/pgdriver
- bun/dialect/pgdialect
- bun/extra/bundebug
- koanf
- koanf/providers/file
- koanf/providers/env
- koanf/parsers/yaml