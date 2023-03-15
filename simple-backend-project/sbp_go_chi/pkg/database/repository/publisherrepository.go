package repository

import (
	"context"
	"errors"
	"github.com/uptrace/bun"
	"strings"
	"time"
	"yiyongc.com/sbp-go-chi/pkg/models"
)

type PublisherRepositoryInterface interface {
	AddPublisher(name string) *models.Publisher
	GetAllPublishers() []*models.Publisher
	FindOneById(id int64) *models.Publisher
	DeleteById(id int64) error
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
		if strings.Contains(err.Error(), "duplicate key value") {
			println("ERROR: Duplicate publisher creation attempted")
			return nil
		} else {
			println(err)
			panic("Error in inserting publisher")
		}
	}
	return pub
}

func (p *PublisherRepository) GetAllPublishers() []*models.Publisher {
	var pubs []*models.Publisher
	ctx, cancel := context.WithTimeout(context.Background(), 30*time.Second)
	defer cancel()
	err := p.db.NewSelect().Model(&pubs).Scan(ctx)
	if err != nil {
		println(err)
		panic("Error in fetching publishers")
	}
	return pubs
}

func (p *PublisherRepository) FindOneById(id int64) *models.Publisher {
	pub := &models.Publisher{}
	ctx, cancel := context.WithTimeout(context.Background(), 10*time.Second)
	defer cancel()
	err := p.db.NewSelect().Model(pub).Where("id = ?", id).Scan(ctx)
	if err != nil {
		if strings.Contains(err.Error(), "no rows in result set") {
			return nil
		} else {
			println(err)
			panic("Error in fetching publisher by id")
		}
	}
	return pub
}

func (p *PublisherRepository) DeleteById(id int64) error {
	ctx, cancel := context.WithTimeout(context.Background(), 10*time.Second)
	defer cancel()
	res, err := p.db.NewDelete().Model((*models.Publisher)(nil)).Where("id = ?", id).Exec(ctx)
	if err != nil {
		return err
	}
	a, _ := res.RowsAffected()
	if a == 0 {
		return errors.New("deletion failed. id does not exist")
	}
	return nil
}
