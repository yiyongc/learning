package main

import (
	"fmt"
	"github.com/knadh/koanf/parsers/yaml"
	"github.com/knadh/koanf/providers/env"
	"github.com/knadh/koanf/providers/file"
	"github.com/knadh/koanf/v2"
	"net/http"
	"strings"
	"yiyongc.com/sbp-go-chi/pkg/config"
	"yiyongc.com/sbp-go-chi/pkg/database"
	"yiyongc.com/sbp-go-chi/pkg/handlers"
	"yiyongc.com/sbp-go-chi/pkg/routers"
	"yiyongc.com/sbp-go-chi/pkg/service"
)

func main() {
	// Read config with Koanf
	k := loadAppConfig()

	// Create app wide config
	c := config.NewAppConfig(k)

	// Initialise database and repositories
	db := database.NewDatabase(c)

	// Create services required for application
	ps := service.NewPublisherService(db.PublisherRepository)

	// Initialise routes and handler functions
	h := handlers.NewHandler(&ps)
	r := routers.NewRouter(&h)

	println(fmt.Sprintf("Serving application on port %v", c.Port))
	err := http.ListenAndServe(fmt.Sprintf(":%v", c.Port), r)
	if err != nil {
		return
	}
}

func loadAppConfig() *koanf.Koanf {
	k := koanf.New(".")
	if err := k.Load(file.Provider("configs/app-config.yaml"), yaml.Parser()); err != nil {
		fmt.Println("ERROR: Failed to load config file", err)
	}
	// Use env variables to override loaded config if any
	// Prefix used is SBP_GO_CHI_
	// e.g. SBP_GO_CHI_DATABASE_HOST overrides database.host in config file
	p := "SBP_GO_CHI_"
	err := k.Load(env.Provider(p, ".", func(s string) string {
		return strings.Replace(strings.ToLower(
			strings.TrimPrefix(s, p)), "_", ".", -1)
	}), nil)
	if err != nil {
		fmt.Println("ERROR: Failed to read environment variables for config", err)
	}
	return k
}
