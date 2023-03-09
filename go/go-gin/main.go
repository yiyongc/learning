package main

import (
	"fmt"
	"github.com/gin-gonic/gin"
	"net/http"
)

func main() {
	// Declare gin engine
	r := gin.Default()

	// Declare routes
	r.GET("/ping", handlePing)
	r.GET("/user/:id/*action", handleParam)
	r.GET("/name", handleQuery)
	r.POST("/post", handleBody)

	// Run gin
	err := r.Run() // use r.Run(":3000") for hardcoded port. Defaults to 8080
	if err != nil {
		return
	}
}

func handlePing(c *gin.Context) {
	c.JSON(http.StatusOK, gin.H{
		"message": "pong",
		"status":  "OK",
	})
}

func handleParam(c *gin.Context) {
	println(c.FullPath())
	id := c.Param("id")
	action := c.Param("action") // This is optional because of *action instead of :action
	message := fmt.Sprintf("Fetched id is %s, action is %s", id, action)
	c.JSON(http.StatusOK, gin.H{
		"message": message,
		"status":  "OK",
	})
}

func handleQuery(c *gin.Context) {
	fn := c.DefaultQuery("firstname", "Guest")
	ln := c.Query("lastname")
	message := fmt.Sprintf("Welcome! Your name is %s %s", fn, ln)
	c.JSON(http.StatusOK, gin.H{
		"message": message,
		"status":  "OK",
	})
}

type MyModel struct {
	Foo string `json:"foo"`
	Bar string `json:"bar"`
}

func handleBody(c *gin.Context) {
	var jsonBody MyModel
	// Using should bind, err should be handled
	if err := c.ShouldBindJSON(&jsonBody); err != nil {
		print(err)
		c.JSON(http.StatusInternalServerError, gin.H{
			"message": "Unable to bind json",
		})
	}
	// This throws error immediately default to 400
	//c.BindJSON(&jsonBody)

}
