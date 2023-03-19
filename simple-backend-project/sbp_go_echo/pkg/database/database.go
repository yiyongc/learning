package database

import (
	"database/sql"
	"github.com/uptrace/bun"
	"github.com/uptrace/bun/dialect/pgdialect"
	"github.com/uptrace/bun/driver/pgdriver"
	"github.com/uptrace/bun/extra/bundebug"
	"yiyongc.com/sbp-go-echo/pkg/config"
	"yiyongc.com/sbp-go-echo/pkg/database/repository"
)

type AppDatabase struct {
	PublisherRepository *repository.PublisherRepositoryInterface
	GameRepository      *repository.GameRepositoryInterface
}

func NewDatabase(config *config.AppConfig) *AppDatabase {
	// Open a PostgreSQL database.
	pgdb := sql.OpenDB(pgdriver.NewConnector(pgdriver.WithDSN(config.DSN)))

	// Create a Bun db on top of it.
	db := bun.NewDB(pgdb, pgdialect.New())

	// Print all queries to stdout.
	db.AddQueryHook(bundebug.NewQueryHook(bundebug.WithVerbose(true)))

	pr := repository.NewPublisherRepository(db)
	gr := repository.NewGameRepository(db)

	return &AppDatabase{
		PublisherRepository: &pr,
		GameRepository:      &gr,
	}
}
