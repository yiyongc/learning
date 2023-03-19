package repository

import (
	"context"
	"errors"
	"github.com/uptrace/bun"
	"time"
	"yiyongc.com/sbp-go-echo/pkg/models"
)

type GameRepositoryInterface interface {
	FindOneById(id int64) *models.Game
	AddGame(name string, publishedDate time.Time, inventoryCount int, publisher *models.Publisher) *models.Game
	UpdateGameInventoryCount(id int64, count int) error
	DeleteById(id int64) error
	HandleGamePurchase(id int64, quantity int) (int, error)
	FindAll() []*models.Game
	FindAllByPublisher(pid int64) []*models.Game
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
		println(err)
		return nil
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

func (r *GameRepository) UpdateGameInventoryCount(id int64, count int) error {
	g := &models.Game{
		InventoryCount: count,
	}
	ctx, cancel := context.WithTimeout(context.Background(), 30*time.Second)
	defer cancel()
	res, err := r.db.NewUpdate().
		Model(g).
		Column("inventory_count").
		Where("id = ?", id).
		Exec(ctx)
	if err != nil {
		return err
	}
	a, _ := res.RowsAffected()
	if a == 0 {
		return errors.New("update failed. id does not exist")
	}
	return nil
}

func (r *GameRepository) DeleteById(id int64) error {
	ctx, cancel := context.WithTimeout(context.Background(), 10*time.Second)
	defer cancel()
	res, err := r.db.NewDelete().
		Model((*models.Game)(nil)).
		Where("id = ?", id).
		Exec(ctx)
	if err != nil {
		return err
	}
	a, _ := res.RowsAffected()
	if a == 0 {
		return errors.New("deletion failed. id does not exist")
	}
	return nil
}

func (r *GameRepository) HandleGamePurchase(id int64, quantity int) (int, error) {
	ctx, cancel := context.WithTimeout(context.Background(), 10*time.Second)
	defer cancel()
	g := &models.Game{}
	err := r.db.NewSelect().
		Model(g).
		Where("game.id = ?", id).
		Scan(ctx)
	if err != nil {
		return 0, errors.New("game purchase failed")
	}
	if g.InventoryCount < quantity {
		return 0, errors.New("insufficient quantity available for purchase")
	}
	ctx, cancel = context.WithTimeout(context.Background(), 30*time.Second)
	defer cancel()
	cnt := g.InventoryCount - quantity
	g.InventoryCount = cnt
	res, err := r.db.NewUpdate().
		Model(g).
		Column("inventory_count").
		Where("id = ?", id).
		Exec(ctx)
	if err != nil {
		return 0, err
	}
	a, _ := res.RowsAffected()
	if a == 0 {
		return 0, errors.New("update failed. id does not exist")
	}
	return cnt, nil
}

func (r *GameRepository) FindAll() []*models.Game {
	g := &models.Game{}
	var gs []*models.Game
	ctx, cancel := context.WithTimeout(context.Background(), 30*time.Second)
	defer cancel()
	err := r.db.NewSelect().Model(g).Scan(ctx, &gs)
	if err != nil {
		return nil
	}
	return gs
}

func (r *GameRepository) FindAllByPublisher(pid int64) []*models.Game {
	ctx, cancel := context.WithTimeout(context.Background(), 30*time.Second)
	defer cancel()
	g := &models.Game{}
	var gs []*models.Game
	_ = r.db.NewSelect().
		Model(g).
		//Relation("Publisher").
		Where("publisher_id = ?", pid).
		Scan(ctx, &gs)
	return gs
}
