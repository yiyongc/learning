# Simple Backend Project using Go and Chi

This is a project connecting to postgresql and generating routes via the chi router library.

The basic design of this backend project is written in the base README file in the root directory.

# Getting Started

Prepare database by running migrations using golang-migrate

```
migrate -path migrations -database postgres://<username>:<password>@localhost:5432/<database_name>?sslmode=disable up
```


Run application

```
go run cmd/sbp-go-chi/sbp-go-chi.go
```

# Packages Used

- go-chi
- golang-migrate
- bun
- bun/driver/pgdriver
- bun/dialect/pgdialect
- bun/extra/bundebug
- koanf
- koanf/providers/file
- koanf/providers/env
- koanf/parsers/yaml