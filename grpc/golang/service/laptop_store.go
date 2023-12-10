package service

import (
	"context"
	"errors"
	"fmt"
	"github.com/jinzhu/copier"
	"log"
	"sync"
	"yiyongc.com/go-grpc-learn/pb"
)

var ErrAlreadyExists = errors.New("record already exists")

type LaptopStore interface {
	Save(laptop *pb.Laptop) error
	Find(id string) (*pb.Laptop, error)
	Search(ctx context.Context, filter *pb.Filter, found func(laptop *pb.Laptop) error) error
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
	other, err := deepCopy(laptop)
	if err != nil {
		return err
	}
	s.data[other.Id] = other
	return nil
}

func (s *InMemoryLaptopStore) Find(id string) (*pb.Laptop, error) {
	s.mutex.RLock()
	defer s.mutex.RUnlock()

	laptop := s.data[id]
	if laptop == nil {
		return nil, nil
	}

	return deepCopy(laptop)
}

func (s *InMemoryLaptopStore) Search(ctx context.Context, filter *pb.Filter, found func(laptop *pb.Laptop) error) error {
	s.mutex.RLock()
	defer s.mutex.RUnlock()

	for _, laptop := range s.data {
		// simulate heavy processing
		//time.Sleep(time.Second)
		log.Print("checking laptop id: ", laptop.GetId())

		if errors.Is(ctx.Err(), context.Canceled) || errors.Is(ctx.Err(), context.DeadlineExceeded) {
			log.Print("context is canceled")
			return errors.New("context is canceled")
		}

		if isQualified(filter, laptop) {
			other, err := deepCopy(laptop)
			if err != nil {
				return err
			}
			// Trigger callback function if found
			err = found(other)
			if err != nil {
				return err
			}
		}
	}
	return nil
}

func isQualified(filter *pb.Filter, laptop *pb.Laptop) bool {
	if laptop.GetPriceUsd() > filter.GetMaxPriceUsd() {
		return false
	}
	if laptop.GetCpu().GetNumberCores() < filter.GetMinCpuCores() {
		return false
	}
	if laptop.GetCpu().GetMinGhz() < filter.GetMinCpuGhz() {
		return false
	}
	if toBit(laptop.GetRam()) < toBit(filter.GetMinRam()) {
		return false
	}
	return true
}

func toBit(memory *pb.Memory) uint64 {
	value := memory.GetValue()
	switch memory.GetUnit() {
	case pb.Memory_BIT:
		return value
	case pb.Memory_BYTE:
		return value << 3 // 8 = 2^3
	case pb.Memory_KILOBYTE:
		return value << 13 // 1024 * 8 = 2^10 * 2^3 = 2^13
	case pb.Memory_MEGABYTE:
		return value << 23
	case pb.Memory_GIGABYTE:
		return value << 33
	case pb.Memory_TERABYTE:
		return value << 43
	default:
		return 0
	}
}

func deepCopy(laptop *pb.Laptop) (*pb.Laptop, error) {
	other := &pb.Laptop{}
	err := copier.Copy(other, laptop)
	if err != nil {
		return nil, fmt.Errorf("cannot copy laptop data: %w", err)
	}
	return other, nil
}
