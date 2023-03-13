package main

import (
	"fmt"
	"github.com/knadh/koanf/parsers/yaml"
	"github.com/knadh/koanf/providers/env"
	"github.com/knadh/koanf/providers/file"
	"github.com/knadh/koanf/v2"
	"strings"
	"yiyongc.com/sbp-go-chi/pkg/config"
	"yiyongc.com/sbp-go-chi/pkg/database"
)

func main() {
	// Read config with Koanf
	k := loadAppConfig()

	// Create app wide config
	c := config.NewAppConfig(k)

	// Initialise database and repositories
	db := database.NewDatabase(c)

	//p := db.PublisherRepository.AddPublisher("test1")
	//
	//g := db.GameRepository.AddGame("game1", time.Now(), 123, p)

	g := db.GameRepository.FindOneById(3)

	fmt.Println(g.Id)
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
