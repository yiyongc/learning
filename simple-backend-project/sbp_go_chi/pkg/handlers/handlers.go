package handlers

import (
	"bytes"
	"encoding/json"
	"github.com/go-chi/chi/v5"
	"net/http"
	"strconv"
	"time"
	"yiyongc.com/sbp-go-chi/pkg/models"
	"yiyongc.com/sbp-go-chi/pkg/service"
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

func (h Handler) CreatePublisher(w http.ResponseWriter, r *http.Request) {
	var body models.CreatePublisherRequest
	err := json.NewDecoder(r.Body).Decode(&body)
	if err != nil || body.Name == "" {
		w.WriteHeader(400)
		e := models.ErrorResponse{
			Status:       "400",
			ErrorMessage: "Invalid request. Unable to create publisher",
		}
		writeResponse(&w, e)
		return
	}
	p := h.PublisherService.CreatePublisher(body.Name)
	if p == nil {
		w.WriteHeader(409)
		e := models.ErrorResponse{
			Status:       "409",
			ErrorMessage: "Duplicate publisher found. Please check inputs",
		}
		writeResponse(&w, e)
		return
	}
	writeResponse(&w, p)
}

func (h Handler) GetAllPublishers(w http.ResponseWriter, r *http.Request) {
	p := h.PublisherService.GetPublishers()
	res := models.DataResponse{
		Data: p,
	}
	writeResponse(&w, res)
}

func (h Handler) GetPublisherById(w http.ResponseWriter, r *http.Request) {
	id := chi.URLParam(r, "id")
	if id != "" {
		i, err := strconv.ParseInt(id, 10, 64)
		if err == nil {
			p := h.PublisherService.GetPublisherById(i)
			if p != nil {
				writeResponse(&w, p)
				return
			}
		}
	}
	// Not found
	w.WriteHeader(404)
	e := models.ErrorResponse{
		Status:       "404",
		ErrorMessage: "Publisher with given id is not found.",
	}
	writeResponse(&w, e)
}

func (h Handler) DeletePublisherById(w http.ResponseWriter, r *http.Request) {
	id := chi.URLParam(r, "id")
	if id != "" {
		i, err := strconv.ParseInt(id, 10, 64)
		if err == nil {
			err = h.PublisherService.DeletePublisherById(i)
			if err == nil {
				w.WriteHeader(204)
				return
			}
		}
	}
	w.WriteHeader(400)
	e := models.ErrorResponse{
		Status:       "400",
		ErrorMessage: "Failed to delete publisher with given id.",
	}
	writeResponse(&w, e)
}

func (h Handler) CreateGame(w http.ResponseWriter, r *http.Request) {
	pid := chi.URLParam(r, "id")
	if pid == "" {
		w.WriteHeader(400)
		e := models.ErrorResponse{
			Status:       "400",
			ErrorMessage: "Invalid publisher id received. Unable to create game",
		}
		writeResponse(&w, e)
		return
	}
	pi, err := strconv.ParseInt(pid, 10, 64)
	if err != nil {
		w.WriteHeader(400)
		e := models.ErrorResponse{
			Status:       "400",
			ErrorMessage: "Invalid publisher id received. Unable to create game",
		}
		writeResponse(&w, e)
		return
	}

	var body models.CreateGameRequest
	err = json.NewDecoder(r.Body).Decode(&body)
	if err != nil {
		w.WriteHeader(500)
		e := models.ErrorResponse{
			Status:       "500",
			ErrorMessage: "Unable to parse request.",
		}
		writeResponse(&w, e)
		return
	}

	// Request validation
	if body.Name == "" {
		w.WriteHeader(400)
		e := models.ErrorResponse{
			Status:       "400",
			ErrorMessage: "Missing name of game",
		}
		writeResponse(&w, e)
		return
	}
	date, err := time.Parse("2006-01-02", body.PublishedDate)
	if err != nil {
		w.WriteHeader(400)
		e := models.ErrorResponse{
			Status:       "400",
			ErrorMessage: "Invalid date provided",
		}
		writeResponse(&w, e)
		return
	}

	p := h.PublisherService.GetPublisherById(pi)
	if p == nil {
		e := models.ErrorResponse{
			Status:       "400",
			ErrorMessage: "Publisher provided not found. Invalid request",
		}
		writeResponse(&w, e)
		return
	}
	g := h.GameService.CreateGame(p, body.Name, date, body.InventoryCount)
	writeResponse(&w, g)
}

func (h Handler) UpdateGameInventory(w http.ResponseWriter, r *http.Request) {
	gid := chi.URLParam(r, "id")
	if gid == "" {
		w.WriteHeader(400)
		e := models.ErrorResponse{
			Status:       "400",
			ErrorMessage: "Invalid game id received.",
		}
		writeResponse(&w, e)
		return
	}
	gi, err := strconv.ParseInt(gid, 10, 64)
	if err != nil {
		w.WriteHeader(400)
		e := models.ErrorResponse{
			Status:       "400",
			ErrorMessage: "Invalid game id received.",
		}
		writeResponse(&w, e)
		return
	}

	var body models.UpdateGameInventoryRequest
	err = json.NewDecoder(r.Body).Decode(&body)
	if err != nil {
		w.WriteHeader(500)
		e := models.ErrorResponse{
			Status:       "500",
			ErrorMessage: "Unable to parse request.",
		}
		writeResponse(&w, e)
		return
	}

	if body.InventoryCount < 0 {
		w.WriteHeader(500)
		e := models.ErrorResponse{
			Status:       "500",
			ErrorMessage: "Invalid inventory count provided",
		}
		writeResponse(&w, e)
		return
	}

	err = h.GameService.UpdateGameInventoryCount(gi, body.InventoryCount)
	if err != nil {
		e := models.ErrorResponse{
			Status:       "400",
			ErrorMessage: "Game not found. Invalid request",
		}
		writeResponse(&w, e)
		return
	}
	w.WriteHeader(200)
	res := models.DataResponse{
		Data: map[string]string{
			"update": "success",
		},
	}
	writeResponse(&w, res)
}

func (h Handler) DeleteGameById(w http.ResponseWriter, r *http.Request) {
	gid := chi.URLParam(r, "id")
	if gid == "" {
		w.WriteHeader(400)
		e := models.ErrorResponse{
			Status:       "400",
			ErrorMessage: "Invalid game id received.",
		}
		writeResponse(&w, e)
		return
	}
	gi, err := strconv.ParseInt(gid, 10, 64)
	if err != nil {
		w.WriteHeader(400)
		e := models.ErrorResponse{
			Status:       "400",
			ErrorMessage: "Invalid game id received.",
		}
		writeResponse(&w, e)
		return
	}
	err = h.GameService.DeleteGameById(gi)
	if err != nil {
		w.WriteHeader(400)
		e := models.ErrorResponse{
			Status:       "400",
			ErrorMessage: "Failed to delete game with given id.",
		}
		writeResponse(&w, e)
		return
	}
	w.WriteHeader(204)
}

func (h Handler) PurchaseGame(w http.ResponseWriter, r *http.Request) {
	var body models.GamePurchaseRequest
	err := json.NewDecoder(r.Body).Decode(&body)
	if err != nil {
		w.WriteHeader(500)
		e := models.ErrorResponse{
			Status:       "500",
			ErrorMessage: "Unable to parse request.",
		}
		writeResponse(&w, e)
		return
	}
	if body.Quantity <= 0 {
		w.WriteHeader(400)
		e := models.ErrorResponse{
			Status:       "400",
			ErrorMessage: "Invalid request. Unable to purchase 0 or less games",
		}
		writeResponse(&w, e)
		return
	}
	cnt, err := h.GameService.HandleGamePurchase(body.Id, body.Quantity)
	if err != nil {
		w.WriteHeader(400)
		e := models.ErrorResponse{
			Status:       "400",
			ErrorMessage: "Failed to purchase game. Please check request",
		}
		writeResponse(&w, e)
		return
	}

	res := models.DataResponse{
		Data: map[string]interface{}{
			"purchase":       "success",
			"remainingStock": cnt,
		},
	}
	writeResponse(&w, res)
}

func (h Handler) GameSearch(w http.ResponseWriter, r *http.Request) {
	q := r.URL.Query()
	pn := q.Get("publisher")
	var res models.DataResponse
	if pn == "" {
		res = models.DataResponse{
			Data: h.GameService.GetAllGames(),
		}
		writeResponse(&w, res)
		return
	}
	p := h.PublisherService.GetPublisherByName(pn)
	if p == nil {
		res = models.DataResponse{
			Data: []*models.Game{},
		}
		writeResponse(&w, res)
		return
	}
	pid := (*p).Id
	g := h.GameService.FindAllByPublisher(pid)
	if g == nil {
		g = []*models.Game{}
	}
	res = models.DataResponse{
		Data: g,
	}
	writeResponse(&w, res)
}

func writeResponse(w *http.ResponseWriter, i interface{}) {
	_, err := (*w).Write(responseToBytes(i))
	if err != nil {
		e := models.ErrorResponse{
			Status:       "500",
			ErrorMessage: err.Error(),
		}
		_, _ = (*w).Write(responseToBytes(e))
	}
}

func responseToBytes(r interface{}) []byte {
	rBytes := new(bytes.Buffer)
	err := json.NewEncoder(rBytes).Encode(r)
	if err != nil {
		return []byte{}
	}
	return rBytes.Bytes()
}
