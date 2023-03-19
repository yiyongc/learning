package models

import "time"

type Publisher struct {
	Id          int64     `bun:",pk,autoincrement" json:"id"`
	Name        string    `json:"name"`
	CreatedDate time.Time `json:"createdDate" bun:",nullzero,notnull,default:current_timestamp"`
}

type Game struct {
	Id             int64      `bun:",pk,autoincrement" json:"id"`
	Name           string     `json:"name"`
	PublishedDate  time.Time  `json:"publishedDate"`
	InventoryCount int        `json:"inventoryCount"`
	CreatedDate    time.Time  `json:"createdDate" bun:",nullzero,notnull,default:current_timestamp"`
	PublisherId    int64      `json:"-"`
	Publisher      *Publisher `bun:"rel:belongs-to,join:publisher_id=id" json:"-"`
}
