package driver

import (
	"database/sql"
	"log"
	"time"

	_ "github.com/jackc/pgconn"
	_ "github.com/jackc/pgx/v4"
	_ "github.com/jackc/pgx/v4/stdlib"
)

// DB holds the database connection pool
type DB struct {
	SQL *sql.DB
}

var dbConn = &DB{}

const maxOpenDbConn = 10
const maxIdleDbConn = 5
const maxDbLifetime = 5 * time.Minute

func ConnectSQL(dsn string) (*DB, error) {
	d, err := newDatabase(dsn)
	if err != nil {
		panic(err)
	}

	d.SetMaxOpenConns(maxOpenDbConn)
	d.SetMaxIdleConns(maxIdleDbConn)
	d.SetConnMaxLifetime(maxDbLifetime)

	dbConn.SQL = d

	err = pingDB(d)
	if err != nil {
		return nil, err
	}

	return dbConn, nil
}

func newDatabase(dsn string) (*sql.DB, error) {
	db, err := sql.Open("pgx", dsn)
	if err != nil {
		log.Println("Failed to open connection to database!")
		return nil, err
	}

	err = pingDB(db)
	if err != nil {
		return nil, err
	}

	log.Println("Database connected!")
	return db, nil
}

func pingDB(d *sql.DB) error {
	if err := d.Ping(); err != nil {
		log.Println("Failed to ping connection to database")
		return err
	}
	return nil
}
