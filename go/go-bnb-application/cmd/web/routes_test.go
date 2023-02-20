package main

import (
	"testing"

	"github.com/go-chi/chi"
	"github.com/yiyongc/booking-application/internal/config"
)

func TestRoutes(t *testing.T) {
	var app config.AppConfig

	mux := routes(&app)

	switch mux.(type) {
	case *chi.Mux:
		// do nothing; test passed
	default:
		t.Error("type is not *chi.Mux")
	}
}
