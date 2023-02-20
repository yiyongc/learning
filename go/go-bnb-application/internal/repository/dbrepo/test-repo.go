package dbrepo

import (
	"errors"
	"time"

	"github.com/yiyongc/booking-application/internal/models"
)

func (m *testDBRepo) AllUsers() bool {
	return true
}

// InsertReservation inserts a reservation into the database
func (m *testDBRepo) InsertReservation(res models.Reservation) (int, error) {
	// if room id is 2, then fail; otherwise pass
	if res.RoomID == 2 {
		return 0, errors.New("some error")
	}
	return 1, nil
}

// InsertRoomRestriction inserts a room restriction into the database
func (m *testDBRepo) InsertRoomRestriction(r models.RoomRestriction) error {
	// if room id is 1000, then fail; otherwise pass
	if r.RoomID == 1000 {
		return errors.New("some error")
	}
	return nil
}

// SearchAvailabilityByDatesAndRoomId returns true if room availability exists, and false if no availability exists
func (m *testDBRepo) SearchAvailabilityByDatesAndRoomId(roomId int, start, end time.Time) (bool, error) {
	// if room id is 1000, then error; otherwise pass
	if roomId == 1000 {
		return false, errors.New("some error")
	}
	return true, nil
}

// SearchAvailableRooms returns the available rooms for the given date range that have no restrictions
func (m *testDBRepo) SearchAvailableRooms(start, end time.Time) ([]models.Room, error) {
	var rooms []models.Room
	// If start date is before current time then error; otherwise pass
	if start.Before(time.Now()) {
		return rooms, errors.New("some error")
	}
	// If start date is within current date to 48 hours after; return no rooms
	if start.Before(time.Now().Add(48 * time.Hour)) {
		return rooms, nil
	}
	// Start date is after current date + 48 hours, show an available room
	rooms = append(rooms, models.Room{
		ID:       1,
		RoomName: "General's Quarters",
	})
	return rooms, nil
}

// GetRoomById returns the room details when provided with an id
func (m *testDBRepo) GetRoomById(id int) (models.Room, error) {
	var room models.Room
	if id > 2 {
		return room, errors.New("some error")
	}
	return room, nil
}
