package models

import "time"

type Publisher struct {
	Id   int64 `bun:",pk,autoincrement"`
	Name string
}

type Game struct {
	Id             int64 `bun:",pk,autoincrement"`
	Name           string
	PublishedDate  time.Time
	InventoryCount int
	PublisherId    int64
	Publisher      *Publisher `bun:"rel:belongs-to,join:publisher_id=id"`
}
