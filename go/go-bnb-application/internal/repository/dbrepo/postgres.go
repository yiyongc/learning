package dbrepo

import (
	"context"
	"log"
	"time"

	"github.com/yiyongc/booking-application/internal/models"
)

func (m *postgresDBRepo) AllUsers() bool {
	return true
}

// InsertReservation inserts a reservation into the database
func (m *postgresDBRepo) InsertReservation(res models.Reservation) (int, error) {
	// Cancel the db connection process if insertion doesn't finish in 3 seconds
	ctx, cancel := context.WithTimeout(context.Background(), 3*time.Second)
	defer cancel()

	stmt := `insert into reservations (first_name, last_name, email, phone, 
				start_date, end_date, room_id, created_at, updated_at) 
				values ($1, $2, $3, $4, $5, $6, $7, $8, $9) returning id`
	var newId int
	err := m.DB.QueryRowContext(ctx, stmt,
		res.FirstName, res.LastName, res.Email, res.Phone,
		res.StartDate, res.EndDate, res.RoomID, time.Now(), time.Now()).Scan(&newId)
	if err != nil {
		return -1, err
	}
	return newId, nil
}

// InsertRoomRestriction inserts a room restriction into the database
func (m *postgresDBRepo) InsertRoomRestriction(r models.RoomRestriction) error {
	// Cancel the db connection process if insertion doesn't finish in 3 seconds
	ctx, cancel := context.WithTimeout(context.Background(), 3*time.Second)
	defer cancel()

	stmt := `insert into room_restrictions (start_date, end_date, room_id,
				reservation_id, created_at, updated_at, restriction_id)
				values ($1, $2, $3, $4, $5, $6, $7)`

	_, err := m.DB.ExecContext(ctx, stmt,
		r.StartDate, r.EndDate, r.RoomID,
		r.ReservationID, time.Now(), time.Now(), r.RestrictionID,
	)
	if err != nil {
		return err
	}

	return nil
}

// SearchAvailabilityByDatesAndRoomId returns true if room availability exists, and false if no availability exists
func (m *postgresDBRepo) SearchAvailabilityByDatesAndRoomId(roomId int, start, end time.Time) (bool, error) {
	// Cancel the db connection process if query doesn't finish in 3 seconds
	ctx, cancel := context.WithTimeout(context.Background(), 3*time.Second)
	defer cancel()

	query := `select count(id) 
	from room_restrictions 
	where room_id = $1 and $2 < end_date and $3 > start_date`

	row := m.DB.QueryRowContext(ctx, query, roomId, start, end)
	var count int
	err := row.Scan(&count)
	if err != nil {
		log.Println(err)
		return false, err
	}

	return count == 0, nil
}

// SearchAvailableRooms returns the available rooms for the given date range that have no restrictions
func (m *postgresDBRepo) SearchAvailableRooms(start, end time.Time) ([]models.Room, error) {
	// Cancel the db connection process if query doesn't finish in 3 seconds
	ctx, cancel := context.WithTimeout(context.Background(), 3*time.Second)
	defer cancel()

	var rooms []models.Room
	query := `select r.id, r.room_name from rooms r where r.id not in
				(select rr.room_id from room_restrictions rr 
					where $1 < end_date and $2 > start_date)`

	rows, err := m.DB.QueryContext(ctx, query, start, end)
	if err != nil {
		return rooms, err
	}
	for rows.Next() {
		var room models.Room
		err := rows.Scan(&room.ID, &room.RoomName)
		if err != nil {
			return rooms, err
		}
		rooms = append(rooms, room)
	}
	if err = rows.Err(); err != nil {
		return rooms, err
	}
	return rooms, nil
}

// GetRoomById returns the room details when provided with an id
func (m *postgresDBRepo) GetRoomById(id int) (models.Room, error) {
	// Cancel the db connection process if query doesn't finish in 3 seconds
	ctx, cancel := context.WithTimeout(context.Background(), 3*time.Second)
	defer cancel()

	var room models.Room
	query := `select id, room_name, created_at, updated_at from rooms where id = $1`

	row := m.DB.QueryRowContext(ctx, query, id)
	err := row.Scan(
		&room.ID,
		&room.RoomName,
		&room.CreatedAt,
		&room.UpdatedAt,
	)
	if err != nil {
		return room, err
	}
	return room, nil
}
