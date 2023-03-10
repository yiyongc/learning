package main

import (
	"bytes"
	"encoding/json"
	"fmt"
	"github.com/go-chi/chi/v5"
	"github.com/go-chi/render"
	"net/http"

	"github.com/go-chi/chi/v5/middleware"
)

type Response struct {
	Status  string      `json:"status"`
	Message interface{} `json:"message"`
}

func main() {
	r := chi.NewRouter()

	// Setup middlewares
	r.Use(middleware.Logger)

	r.Get("/ping", handlePing)
	r.Get("/user/{id}/{action}", handleParam)
	r.Get("/user/{id}/*", handleParam)
	r.Get("/name", handleQuery)
	r.Post("/post", handleBody)

	err := http.ListenAndServe(":8080", r)
	if err != nil {
		return
	}
}

func responseToBytes(r Response) []byte {
	rBytes := new(bytes.Buffer)
	err := json.NewEncoder(rBytes).Encode(r)
	if err != nil {
		return []byte{}
	}
	return rBytes.Bytes()
}

func handlePing(w http.ResponseWriter, r *http.Request) {
	var res Response
	res.Status = "200"
	res.Message = "Pong"
	_, err := w.Write(responseToBytes(res))
	if err != nil {
		return
	}
}

func handleParam(w http.ResponseWriter, r *http.Request) {
	var res Response
	res.Status = "200"
	id := chi.URLParam(r, "id")
	action := chi.URLParam(r, "action")
	println(r.URL.Path)
	res.Message = fmt.Sprintf("Received query for id %s and action %s", id, action)
	_, err := w.Write(responseToBytes(res))
	if err != nil {
		return
	}
}

func handleQuery(w http.ResponseWriter, r *http.Request) {
	var res Response
	res.Status = "OK"
	q := r.URL.Query()
	fn := q.Get("firstname")
	ln := q.Get("lastname")
	if fn == "" {
		fn = "Guest"
	}
	res.Message = fmt.Sprintf("Welcome! Your name is %s %s", fn, ln)
	_, err := w.Write(responseToBytes(res))
	if err != nil {
		return
	}
}

type MyModel struct {
	Foo string `json:"foo"`
	Bar string `json:"bar"`
}

func handleBody(w http.ResponseWriter, r *http.Request) {
	var m MyModel
	var res Response
	err := json.NewDecoder(r.Body).Decode(&m)
	if err != nil {
		w.Header().Set("Content-Type", "application/json")
		w.WriteHeader(500)
		res.Status = "500"
		res.Message = "Failed to bind object"
		_, _ = w.Write(responseToBytes(res))
		return
	}
	res.Status = "200"
	res.Message = m
	render.JSON(w, r, res)
}
