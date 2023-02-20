package main

import (
	"fmt"
	"image"
	"image/color"
	"io"
	"os"
	"strings"
)

func main() {
	fmt.Println("---- Running learningIOReader ----")
	learningIOReader()
	fmt.Println("\n---- Running exerciseRot13Reader ----")
	exerciseRot13Reader()
	fmt.Println("\n---- Running learningImageInterface ----")
	learningImageInterface()
}

func learningIOReader() {
	// Strings reader implements the Reader interface which has the method Read
	reader := strings.NewReader("Hello, I am YY")

	// Create a byte slice for the reader to read into
	byteSlice := make([]byte, 8)
	for {
		n, err := reader.Read(byteSlice)
		// n is the number of bytes written to the byte slice
		fmt.Printf("n = %v err = %v byteSlice = %v\n", n, err, byteSlice)
		fmt.Printf("byteSlice[:n] = %q\n", byteSlice[:n])
		// When the io reader reaches the end of file, the reader will register an error io.EOF
		if err == io.EOF {
			break
		}
	}
}

// MyReader - This reader implementation is for exercise: readers
type MyReader struct{}

func (myReader MyReader) Read(byteSlice []byte) (int, error) {
	byteSlice[0] = 'A'
	return 1, nil
}

type rot13Reader struct {
	r io.Reader
}

func (rot *rot13Reader) Read(byteSlice []byte) (int, error) {
	// First read the io.Reader string into the byteSlice
	n, err := rot.r.Read(byteSlice)

	// Iterate through each character and do rotation-13 cipher
	for i, character := range byteSlice {
		if character >= 'A' && character <= 'M' || character >= 'a' && character <= 'm' {
			byteSlice[i] += 13
		} else if character >= 'N' && character <= 'Z' || character >= 'n' && character <= 'z' {
			byteSlice[i] -= 13
		}
	}

	return n, err
}

func exerciseRot13Reader() {
	s := strings.NewReader("Lbh penpxrq gur pbqr!")
	r := rot13Reader{s}
	io.Copy(os.Stdout, &r)
	fmt.Println()
}

func learningImageInterface() {
	// Using the image interface, we can create a new RGBA image with a Rectangle
	m := image.NewRGBA(image.Rect(0, 0, 100, 100))
	fmt.Println(m.Bounds)          // The bounds of the image should follow the given rectangle
	fmt.Println(m.At(0, 0).RGBA()) // Prints the RGBA value at the specific point in the image
}

// MyImage - Type created for exercise: images
type MyImage struct {
	Width, Height int
}

// ColorModel - To implement image interface
func (myImage MyImage) ColorModel() color.Model {
	return color.RGBAModel
}

// Bounds - To implement image interface
func (myImage MyImage) Bounds() image.Rectangle {
	return image.Rect(0, 0, myImage.Width, myImage.Height)
}

// At - To implement image interface
func (myImage MyImage) At(x, y int) color.Color {
	return color.RGBA{
		uint8((x + y) / 2),
		uint8(x),
		uint8(y),
		uint8(255)}
}
