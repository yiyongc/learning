package service

import (
	"yiyongc.com/sbp-go-chi/pkg/database/repository"
	"yiyongc.com/sbp-go-chi/pkg/models"
)

type PublisherService struct {
	pubRepo *repository.PublisherRepositoryInterface
}

func NewPublisherService(r *repository.PublisherRepositoryInterface) PublisherService {
	p := PublisherService{
		pubRepo: r,
	}
	return p
}

func (s PublisherService) CreatePublisher(name string) *models.Publisher {
	return (*s.pubRepo).AddPublisher(name)
}

func (s PublisherService) GetPublishers() []*models.Publisher {
	return (*s.pubRepo).GetAllPublishers()
}

func (s PublisherService) GetPublisherById(id int64) *models.Publisher {
	return (*s.pubRepo).FindOneById(id)
}

func (s PublisherService) DeletePublisherById(id int64) error {
	return (*s.pubRepo).DeleteById(id)
}

func (s PublisherService) GetPublisherByName(name string) *models.Publisher {
	return (*s.pubRepo).FindOneByName(name)
}
