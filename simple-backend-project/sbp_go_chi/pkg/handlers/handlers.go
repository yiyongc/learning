package handlers

import (
	"bytes"
	"encoding/json"
	"net/http"
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

func (h Handler) GetAllPublishers(w http.ResponseWriter, r *http.Request) {
	p := h.PublisherService.GetPublishers()
	res := models.DataResponse{
		Data: p,
	}
	_, err := w.Write(responseToBytes(res))
	if err != nil {
		e := models.ErrorResponse{
			Status:       "500",
			ErrorMessage: err.Error(),
		}
		_, _ = w.Write(responseToBytes(e))
		return
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
