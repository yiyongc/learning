package routers

import (
	"github.com/go-chi/chi/v5"
	"github.com/go-chi/chi/v5/middleware"
	"yiyongc.com/sbp-go-chi/pkg/handlers"
)

func NewRouter(h *handlers.Handler) chi.Router {
	r := chi.NewRouter()

	// Setup middlewares
	r.Use(middleware.RequestID)
	r.Use(middleware.Logger)
	r.Use(middleware.Recoverer)
	r.Use(middleware.URLFormat)
	r.Use(middleware.SetHeader("Content-Type", "application/json"))

	// Setup routes
	r.Mount("/publishers", publisherRouter(h))
	r.Mount("/games", gameRouter(h))

	return r
}

func publisherRouter(h *handlers.Handler) chi.Router {
	r := chi.NewRouter()

	r.Get("/", h.GetAllPublishers)
	r.Get("/{id}", h.GetPublisherById)
	r.Post("/", h.CreatePublisher)
	r.Delete("/{id}", h.DeletePublisherById)

	r.Post("/{id}/games", h.CreateGame)

	return r
}

func gameRouter(h *handlers.Handler) chi.Router {
	r := chi.NewRouter()

	r.Put("/{id}/inventory", h.UpdateGameInventory)
	r.Delete("/{id}", h.DeleteGameById)
	r.Post("/purchase", h.PurchaseGame)
	r.Get("/search", h.GameSearch)

	return r
}
