package handlers

import (
	"bytes"
	"encoding/json"
	"github.com/go-chi/chi/v5"
	"net/http"
	"strconv"
	"yiyongc.com/sbp-go-chi/pkg/models"
	"yiyongc.com/sbp-go-chi/pkg/service"
)

type Handler struct {
	PublisherService *service.PublisherService
}

func NewHandler(ps *service.PublisherService) Handler {
	return Handler{
		PublisherService: ps,
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
