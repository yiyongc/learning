package routers

import (
	"github.com/go-chi/chi/v5"
	"github.com/go-chi/chi/v5/middleware"
	"github.com/go-chi/render"
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

	return r
}

func publisherRouter(h *handlers.Handler) chi.Router {
	r := chi.NewRouter()
	r.Use(render.SetContentType(render.ContentTypeJSON))

	r.Get("/", h.GetAllPublishers)

	return r
}
