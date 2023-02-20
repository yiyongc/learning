package repository

import (
	"time"

	"github.com/yiyongc/booking-application/internal/models"
)

type DatabaseRepo interface {
	AllUsers() bool

	InsertReservation(res models.Reservation) (int, error)

	InsertRoomRestriction(r models.RoomRestriction) error

	SearchAvailabilityByDatesAndRoomId(roomId int, start, end time.Time) (bool, error)

	SearchAvailableRooms(start, end time.Time) ([]models.Room, error)

	GetRoomById(id int) (models.Room, error)
}
