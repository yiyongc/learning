package handlers

import (
	"github.com/labstack/echo/v4"
	"net/http"
	"strconv"
	"time"
	"yiyongc.com/sbp-go-echo/pkg/models"
	"yiyongc.com/sbp-go-echo/pkg/service"
)

type Handler struct {
	PublisherService *service.PublisherService
	GameService      *service.GameService
}

func NewHandler(ps *service.PublisherService, gs *service.GameService) Handler {
	return Handler{
		PublisherService: ps,
		GameService:      gs,
	}
}

func (h Handler) CreatePublisher(c echo.Context) error {
	var body models.CreatePublisherRequest
	if err := c.Bind(&body); err != nil {
		print(err)
		e := models.ErrorResponse{
			Status:       "500",
			ErrorMessage: "Binding error",
		}
		return c.JSON(http.StatusInternalServerError, e)
	}
	if body.Name == "" {
		e := models.ErrorResponse{
			Status:       "400",
			ErrorMessage: "Invalid request. Unable to create publisher",
		}
		return c.JSON(http.StatusBadRequest, e)
	}
	p := h.PublisherService.CreatePublisher(body.Name)
	if p == nil {
		e := models.ErrorResponse{
			Status:       "409",
			ErrorMessage: "Duplicate publisher found. Please check inputs",
		}
		return c.JSON(http.StatusConflict, e)
	}
	return c.JSON(http.StatusCreated, p)
}

func (h Handler) GetAllPublishers(c echo.Context) error {
	p := h.PublisherService.GetPublishers()
	res := models.DataResponse{
		Data: p,
	}
	return c.JSON(http.StatusOK, res)
}

func (h Handler) GetPublisherById(c echo.Context) error {
	id := c.Param("id")
	if id != "" {
		i, err := strconv.ParseInt(id, 10, 64)
		if err == nil {
			p := h.PublisherService.GetPublisherById(i)
			if p != nil {
				return c.JSON(http.StatusOK, p)
			}
		}
	}
	// Not found
	e := models.ErrorResponse{
		Status:       "404",
		ErrorMessage: "Publisher with given id is not found.",
	}
	return c.JSON(http.StatusNotFound, e)
}

func (h Handler) DeletePublisherById(c echo.Context) error {
	id := c.Param("id")
	if id != "" {
		i, err := strconv.ParseInt(id, 10, 64)
		if err == nil {
			err = h.PublisherService.DeletePublisherById(i)
			if err == nil {
				return c.JSON(http.StatusNoContent, nil)
			}
		}
	}
	e := models.ErrorResponse{
		Status:       "400",
		ErrorMessage: "Failed to delete publisher with given id.",
	}
	return c.JSON(http.StatusBadRequest, e)
}

func (h Handler) CreateGame(c echo.Context) error {
	pid := c.Param("id")
	if pid == "" {
		e := models.ErrorResponse{
			Status:       "400",
			ErrorMessage: "Invalid publisher id received. Unable to create game",
		}
		return c.JSON(http.StatusBadRequest, e)
	}
	pi, err := strconv.ParseInt(pid, 10, 64)
	if err != nil {
		e := models.ErrorResponse{
			Status:       "400",
			ErrorMessage: "Invalid publisher id received. Unable to create game",
		}
		return c.JSON(http.StatusBadRequest, e)
	}

	var body models.CreateGameRequest
	if err = c.Bind(&body); err != nil {
		print(err)
		e := models.ErrorResponse{
			Status:       "500",
			ErrorMessage: "Binding error",
		}
		return c.JSON(http.StatusInternalServerError, e)
	}

	// Request validation
	if body.Name == "" {
		e := models.ErrorResponse{
			Status:       "400",
			ErrorMessage: "Missing name of game",
		}
		return c.JSON(http.StatusBadRequest, e)
	}
	date, err := time.Parse("2006-01-02", body.PublishedDate)
	if err != nil {
		e := models.ErrorResponse{
			Status:       "400",
			ErrorMessage: "Invalid date provided",
		}
		return c.JSON(http.StatusBadRequest, e)
	}

	p := h.PublisherService.GetPublisherById(pi)
	if p == nil {
		e := models.ErrorResponse{
			Status:       "400",
			ErrorMessage: "Publisher provided not found. Invalid request",
		}
		return c.JSON(http.StatusBadRequest, e)
	}
	g := h.GameService.CreateGame(p, body.Name, date, body.InventoryCount)
	return c.JSON(http.StatusCreated, g)
}

func (h Handler) UpdateGameInventory(c echo.Context) error {
	gid := c.Param("id")
	if gid == "" {
		e := models.ErrorResponse{
			Status:       "400",
			ErrorMessage: "Invalid game id received.",
		}
		return c.JSON(http.StatusBadRequest, e)
	}
	gi, err := strconv.ParseInt(gid, 10, 64)
	if err != nil {
		e := models.ErrorResponse{
			Status:       "400",
			ErrorMessage: "Invalid game id received.",
		}
		return c.JSON(http.StatusBadRequest, e)
	}

	var body models.UpdateGameInventoryRequest
	if err = c.Bind(&body); err != nil {
		print(err)
		e := models.ErrorResponse{
			Status:       "500",
			ErrorMessage: "Binding error. Unable to parse request",
		}
		return c.JSON(http.StatusInternalServerError, e)
	}

	if body.InventoryCount < 0 {
		e := models.ErrorResponse{
			Status:       "400",
			ErrorMessage: "Invalid inventory count provided",
		}
		return c.JSON(http.StatusBadRequest, e)
	}

	err = h.GameService.UpdateGameInventoryCount(gi, body.InventoryCount)
	if err != nil {
		e := models.ErrorResponse{
			Status:       "400",
			ErrorMessage: "Game not found. Invalid request",
		}
		return c.JSON(http.StatusBadRequest, e)
	}
	res := models.DataResponse{
		Data: map[string]string{
			"update": "success",
		},
	}
	return c.JSON(http.StatusOK, res)
}

func (h Handler) DeleteGameById(c echo.Context) error {
	gid := c.Param("id")
	if gid == "" {
		e := models.ErrorResponse{
			Status:       "400",
			ErrorMessage: "Invalid game id received.",
		}
		return c.JSON(http.StatusBadRequest, e)
	}
	gi, err := strconv.ParseInt(gid, 10, 64)
	if err != nil {
		e := models.ErrorResponse{
			Status:       "400",
			ErrorMessage: "Invalid game id received.",
		}
		return c.JSON(http.StatusBadRequest, e)
	}
	err = h.GameService.DeleteGameById(gi)
	if err != nil {
		e := models.ErrorResponse{
			Status:       "400",
			ErrorMessage: "Failed to delete game with given id.",
		}
		return c.JSON(http.StatusBadRequest, e)
	}
	return c.JSON(http.StatusNoContent, nil)
}

func (h Handler) PurchaseGame(c echo.Context) error {
	var body models.GamePurchaseRequest
	if err := c.Bind(&body); err != nil {
		print(err)
		e := models.ErrorResponse{
			Status:       "500",
			ErrorMessage: "Binding error. Unable to parse request",
		}
		return c.JSON(http.StatusInternalServerError, e)
	}

	if body.Quantity <= 0 {
		e := models.ErrorResponse{
			Status:       "400",
			ErrorMessage: "Invalid request. Unable to purchase 0 or less games",
		}
		return c.JSON(http.StatusBadRequest, e)
	}
	cnt, err := h.GameService.HandleGamePurchase(body.Id, body.Quantity)
	if err != nil {
		e := models.ErrorResponse{
			Status:       "400",
			ErrorMessage: "Failed to purchase game. Please check request",
		}
		return c.JSON(http.StatusBadRequest, e)

	}

	res := models.DataResponse{
		Data: map[string]interface{}{
			"purchase":       "success",
			"remainingStock": cnt,
		},
	}
	return c.JSON(http.StatusOK, res)
}

func (h Handler) GameSearch(c echo.Context) error {
	pn := c.QueryParam("publisher")
	var res models.DataResponse
	if pn == "" {
		res = models.DataResponse{
			Data: h.GameService.GetAllGames(),
		}
		return c.JSON(http.StatusOK, res)
	}
	p := h.PublisherService.GetPublisherByName(pn)
	if p == nil {
		res = models.DataResponse{
			Data: []*models.Game{},
		}
		return c.JSON(http.StatusOK, res)
	}
	pid := (*p).Id
	g := h.GameService.FindAllByPublisher(pid)
	if g == nil {
		g = []*models.Game{}
	}
	res = models.DataResponse{
		Data: g,
	}
	return c.JSON(http.StatusOK, res)
}
