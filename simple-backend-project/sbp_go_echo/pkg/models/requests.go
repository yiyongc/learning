package models

type CreatePublisherRequest struct {
	Name string `json:"name"`
}

type CreateGameRequest struct {
	Name           string `json:"name"`
	PublishedDate  string `json:"publishedDate"`
	InventoryCount int    `json:"inventoryCount"`
}

type UpdateGameInventoryRequest struct {
	InventoryCount int `json:"count"`
}

type GamePurchaseRequest struct {
	Id       int64 `json:"id"`
	Quantity int   `json:"quantity"`
}
