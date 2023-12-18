package service

import "sync"

type RatingStore interface {
	Add(laptopId string, score float64) (*Rating, error)
}

type Rating struct {
	Count uint32
	Sum   float64
}

type InMemoryRatingStore struct {
	mutex  sync.RWMutex
	rating map[string]*Rating
}

func NewInMemoryRatingStore() *InMemoryRatingStore {
	return &InMemoryRatingStore{
		rating: make(map[string]*Rating),
	}
}

func (s *InMemoryRatingStore) Add(laptopId string, score float64) (*Rating, error) {
	s.mutex.Lock()
	defer s.mutex.Unlock()

	rating := s.rating[laptopId]
	if rating == nil {
		rating = &Rating{
			Count: 1,
			Sum:   score,
		}
	} else {
		rating.Count++
		rating.Sum += score
	}

	s.rating[laptopId] = rating
	return rating, nil
}
