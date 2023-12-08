package service

import (
	"errors"
	"fmt"
	"github.com/jinzhu/copier"
	"sync"
	"yiyongc.com/go-grpc-learn/pb"
)

var ErrAlreadyExists = errors.New("record already exists")

type LaptopStore interface {
	Save(laptop *pb.Laptop) error
	Find(id string) (*pb.Laptop, error)
}

type InMemoryLaptopStore struct {
	mutex sync.RWMutex
	data  map[string]*pb.Laptop
}

func NewInMemoryLaptopStore() *InMemoryLaptopStore {
	return &InMemoryLaptopStore{
		mutex: sync.RWMutex{},
		data:  make(map[string]*pb.Laptop),
	}
}

func (s *InMemoryLaptopStore) Save(laptop *pb.Laptop) error {
	s.mutex.Lock()
	defer s.mutex.Unlock()
	if s.data[laptop.Id] != nil {
		return ErrAlreadyExists
	}
	cp := &pb.Laptop{}
	err := copier.Copy(cp, laptop)
	if err != nil {
		return fmt.Errorf("cannot copy laptop data: %w", err)
	}
	s.data[cp.Id] = cp
	return nil
}

func (s *InMemoryLaptopStore) Find(id string) (*pb.Laptop, error) {
	s.mutex.RLock()
	defer s.mutex.RUnlock()

	laptop := s.data[id]
	if laptop == nil {
		return nil, nil
	}

	cp := &pb.Laptop{}
	err := copier.Copy(cp, laptop)
	if err != nil {
		return nil, fmt.Errorf("cannot copy laptop data: %w", err)
	}

	return cp, nil
}
