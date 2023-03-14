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

func (s PublisherService) GetPublishers() []*models.Publisher {
	return (*s.pubRepo).GetAllPublishers()
}
