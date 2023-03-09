package main

import (
	"fmt"
	"github.com/labstack/echo/v4"
	"github.com/labstack/echo/v4/middleware"
	"net/http"
)

type Response struct {
	Status  string `json:"status"`
	Message string `json:"message"`
}

func main() {
	// Echo instance
	e := echo.New()

	e.Use(middleware.Logger())
	e.Use(middleware.Recover())

	e.GET("/ping", handlePing)
	e.GET("/user/:id/:action", handleParam)
	e.GET("/user/:id/*", handleParam)
	e.GET("/name", handleQuery)
	e.POST("/post", handleBody)

	e.Logger.Fatal(e.Start(":8080"))
}

func handlePing(c echo.Context) error {
	var r Response
	r.Status = "OK"
	r.Message = "pong"
	return c.JSON(http.StatusOK, r)
}

func handleParam(c echo.Context) error {
	var r Response
	r.Status = "OK"
	id := c.Param("id")
	action := c.Param("action")
	println(c.Path())
	r.Message = fmt.Sprintf("Received query for id %s and action %s", id, action)
	return c.JSON(http.StatusOK, r)
}

func handleQuery(c echo.Context) error {
	var r Response
	r.Status = "OK"
	fn := c.QueryParam("firstname")
	ln := c.QueryParam("lastname")
	if fn == "" {
		fn = "Guest"
	}
	r.Message = fmt.Sprintf("Welcome! Your name is %s %s", fn, ln)
	return c.JSON(http.StatusOK, r)
}

type MyModel struct {
	Foo string `json:"foo"`
	Bar string `json:"bar"`
}

func handleBody(c echo.Context) error {
	var jsonBody MyModel
	var r Response
	if err := c.Bind(&jsonBody); err != nil {
		print(err)
		r.Status = "Internal Server Error"
		r.Message = "Binding error"
		return c.JSON(http.StatusInternalServerError, r)
	}
	r.Status = "OK"
	r.Message = "All's good"
	return c.JSON(http.StatusOK, r)
}
