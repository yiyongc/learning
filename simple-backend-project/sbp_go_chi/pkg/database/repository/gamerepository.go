package repository

import (
	"context"
	"github.com/uptrace/bun"
	"time"
	"yiyongc.com/sbp-go-chi/pkg/models"
)

type GameRepositoryInterface interface {
	FindOneById(id int64) *models.Game
	AddGame(name string, publishedDate time.Time, inventoryCount int, publisher *models.Publisher) *models.Game
}

func NewGameRepository(db *bun.DB) GameRepositoryInterface {
	r := GameRepository{db}
	return &r
}

type GameRepository struct {
	db *bun.DB
}

func (r *GameRepository) FindOneById(id int64) *models.Game {
	ctx, cancel := context.WithTimeout(context.Background(), 10*time.Second)
	defer cancel()
	g := &models.Game{}
	err := r.db.NewSelect().
		Model(g).
		Relation("Publisher").
		Where("game.id = ?", id).
		Scan(ctx)
	if err != nil {
		panic(err)
	}
	return g
}

func (r *GameRepository) AddGame(name string, publishedDate time.Time, inventoryCount int, publisher *models.Publisher) *models.Game {
	g := &models.Game{
		Name:           name,
		PublishedDate:  publishedDate,
		InventoryCount: inventoryCount,
		PublisherId:    publisher.Id,
	}
	ctx, cancel := context.WithTimeout(context.Background(), 10*time.Second)
	defer cancel()
	_, err := r.db.NewInsert().Model(g).Exec(ctx)
	if err != nil {
		println(err)
		panic("Error in inserting publisher")
	}
	return g
}
