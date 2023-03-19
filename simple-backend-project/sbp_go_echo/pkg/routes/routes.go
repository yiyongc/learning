package routes

import (
	"github.com/labstack/echo/v4"
	"github.com/labstack/echo/v4/middleware"
	"yiyongc.com/sbp-go-echo/pkg/handlers"
)

func NewRouter(h *handlers.Handler) *echo.Echo {
	e := echo.New()

	e.Pre(middleware.RemoveTrailingSlash())

	e.Use(middleware.Logger())
	e.Use(middleware.Recover())

	setupPublishersRouter(e, h)
	setupGamesRouter(e, h)

	return e
}

func setupPublishersRouter(e *echo.Echo, h *handlers.Handler) {
	g := e.Group("/publishers")

	g.GET("", h.GetAllPublishers)
	g.GET("/:id", h.GetPublisherById)
	g.POST("", h.CreatePublisher)
	g.DELETE("/:id", h.DeletePublisherById)

	g.POST("/:id/games", h.CreateGame)
}

func setupGamesRouter(e *echo.Echo, h *handlers.Handler) {
	g := e.Group("/games")

	g.PUT("/:id/inventory", h.UpdateGameInventory)
	g.DELETE("/:id", h.DeleteGameById)
	g.POST("/purchase", h.PurchaseGame)
	g.GET("/search", h.GameSearch)
}
