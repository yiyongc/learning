package repository

import (
	"context"
	"github.com/uptrace/bun"
	"time"
	"yiyongc.com/sbp-go-chi/pkg/models"
)

type PublisherRepositoryInterface interface {
	AddPublisher(name string) *models.Publisher
}

func NewPublisherRepository(db *bun.DB) PublisherRepositoryInterface {
	r := PublisherRepository{db}
	return &r
}

type PublisherRepository struct {
	db *bun.DB
}

func (p *PublisherRepository) AddPublisher(name string) *models.Publisher {
	pub := &models.Publisher{
		Name: name,
	}
	ctx, cancel := context.WithTimeout(context.Background(), 10*time.Second)
	defer cancel()
	_, err := p.db.NewInsert().Model(pub).Exec(ctx)
	if err != nil {
		println(err)
		panic("Error in inserting publisher")
	}
	return pub
}
