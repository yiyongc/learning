package models

type DataResponse struct {
	Data interface{} `json:"data"`
}

type ErrorResponse struct {
	Status       string `json:"status"`
	ErrorMessage string `json:"errorMessage"`
}
