package config

import (
	"fmt"
	"github.com/knadh/koanf/v2"
)

type AppConfig struct {
	DSN  string
	Port int
}

func NewAppConfig(k *koanf.Koanf) *AppConfig {
	c := AppConfig{}

	port := k.Int("server.port")
	c.Port = port
	c.DSN = formDsn(k)

	return &c
}

func formDsn(k *koanf.Koanf) string {
	dbHost := k.String("database.host")
	dbPort := k.String("database.port")
	dbName := k.String("database.name")
	dbUser := k.String("database.username")
	dbPass := k.String("database.password")
	dbSsl := k.Bool("database.ssl")

	if dbHost == "" || dbPort == "" || dbName == "" {
		panic("Unable to form database connection string with missing config")
	}

	var credentials string
	if dbUser != "" {
		credentials = fmt.Sprintf("%s:%s", dbUser, dbPass)
	}
	var dsn string
	if credentials == "" {
		dsn = fmt.Sprintf("postgres://%s:%s/%s", dbHost, dbPort, dbName)
	} else {
		dsn = fmt.Sprintf("postgres://%s@%s:%s/%s", credentials, dbHost, dbPort, dbName)
	}

	if dbSsl {
		dsn = fmt.Sprintf("%s?sslmode=enable", dsn)
	} else {
		dsn = fmt.Sprintf("%s?sslmode=disable", dsn)
	}
	return dsn
}
