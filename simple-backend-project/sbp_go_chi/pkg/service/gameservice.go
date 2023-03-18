package service

import (
	"time"
	"yiyongc.com/sbp-go-chi/pkg/database/repository"
	"yiyongc.com/sbp-go-chi/pkg/models"
)

type GameService struct {
	gameRepo *repository.GameRepositoryInterface
}

func NewGameService(r *repository.GameRepositoryInterface) GameService {
	g := GameService{
		gameRepo: r,
	}
	return g
}

func (s GameService) CreateGame(p *models.Publisher, name string, publishedDate time.Time, count int) interface{} {
	return (*s.gameRepo).AddGame(name, publishedDate, count, p)
}

func (s GameService) UpdateGameInventoryCount(id int64, count int) error {
	return (*s.gameRepo).UpdateGameInventoryCount(id, count)
}

func (s GameService) DeleteGameById(id int64) error {
	return (*s.gameRepo).DeleteById(id)
}

func (s GameService) HandleGamePurchase(id int64, quantity int) (int, error) {
	return (*s.gameRepo).HandleGamePurchase(id, quantity)
}

func (s GameService) GetAllGames() []*models.Game {
	return (*s.gameRepo).FindAll()
}

func (s GameService) FindAllByPublisher(pid int64) []*models.Game {
	return (*s.gameRepo).FindAllByPublisher(pid)
}
