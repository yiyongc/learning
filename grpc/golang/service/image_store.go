package service

import (
	"bytes"
	"fmt"
	"github.com/google/uuid"
	"log"
	"os"
	"sync"
)

type ImageStore interface {
	Save(laptopId string, imageType string, imageData bytes.Buffer) (string, error)
}

type DiskImageStore struct {
	mutex       sync.Mutex
	imageFolder string
	images      map[string]*ImageInfo
}

type ImageInfo struct {
	LaptopID string
	Type     string
	Path     string
}

func NewDiskImageStore(imageFolder string) *DiskImageStore {
	return &DiskImageStore{
		imageFolder: imageFolder,
		images:      make(map[string]*ImageInfo),
	}
}

func (s *DiskImageStore) Save(
	laptopID string,
	imageType string,
	imageData bytes.Buffer,
) (string, error) {
	imageId, err := uuid.NewRandom()
	if err != nil {
		return "", fmt.Errorf("cannot generate image id: %w", err)
	}

	imagePath := fmt.Sprintf("%s/%s%s", s.imageFolder, imageId, imageType)

	file, err := os.Create(imagePath)
	if err != nil {
		return "", fmt.Errorf("cannot create image file: %w", err)
	}
	defer func(file *os.File) {
		err := file.Close()
		if err != nil {
			log.Print("failed to close image file after saving", err)
		}
	}(file)

	_, err = imageData.WriteTo(file)
	if err != nil {
		return "", fmt.Errorf("cannot write image to file: %w", err)
	}

	s.mutex.Lock()
	defer s.mutex.Unlock()

	s.images[imageId.String()] = &ImageInfo{
		LaptopID: laptopID,
		Type:     imageType,
		Path:     imagePath,
	}

	return imageId.String(), nil
}
