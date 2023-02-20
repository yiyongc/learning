package main

import (
	"encoding/gob"
	"log"
	"net/http"
	"os"
	"time"

	"github.com/alexedwards/scs/v2"
	"github.com/yiyongc/booking-application/internal/config"
	"github.com/yiyongc/booking-application/internal/driver"
	"github.com/yiyongc/booking-application/internal/handlers"
	"github.com/yiyongc/booking-application/internal/helpers"
	"github.com/yiyongc/booking-application/internal/models"
	"github.com/yiyongc/booking-application/internal/render"
)

const portNumber = ":8080"

var app config.AppConfig
var session *scs.SessionManager
var infoLog *log.Logger
var errorLog *log.Logger

func main() {
	db, err := run()
	if err != nil {
		log.Fatal(err)
	}
	defer db.SQL.Close()

	log.Printf("Starting application on port %s\n", portNumber)

	srv := &http.Server{
		Addr:    portNumber,
		Handler: routes(&app),
	}

	err = srv.ListenAndServe()
	log.Fatal(err)
}

func run() (*driver.DB, error) {
	// Config application to allow storing of object type in Session
	gob.Register(models.Reservation{})
	gob.Register(models.User{})
	gob.Register(models.Room{})
	gob.Register(models.Restriction{})

	// Change this to true when in production
	app.InProduction = false

	// Configure app level loggers
	app.InfoLog = log.New(os.Stdout, "INFO\t", log.Ldate|log.Ltime)
	app.ErrorLog = log.New(os.Stdout, "ERROR\t", log.Ldate|log.Ltime|log.Lshortfile)

	session = scs.New()
	session.Lifetime = 24 * time.Hour
	session.Cookie.Persist = true
	session.Cookie.SameSite = http.SameSiteLaxMode
	session.Cookie.Secure = app.InProduction

	app.Session = session

	// Connect to database
	log.Println("Connecting to database....")
	db, err := driver.ConnectSQL("host=localhost port=5432 dbname=bookings user=postgres password=admin")
	if err != nil {
		log.Fatal("Cannot connect to database! Dying...")
	}

	tc, err := render.CreateTemplateCache()
	if err != nil {
		return nil, err
	}

	app.TemplateCache = tc
	app.UseCache = app.InProduction

	// Pass app config to render
	render.NewRenderer(&app)

	// Create a new repo with app config and pass it to handlers
	repo := handlers.NewRepo(&app, db)
	handlers.NewHandlers(repo)

	// Create new helpers with app config
	helpers.NewHelpers(&app)

	return db, nil
}
