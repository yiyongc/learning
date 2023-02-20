package main

import (
	"fmt"
	"math"
	"math/rand"
	"strings"
)

func main() {
	fmt.Println("---- Running learningPointers ----")
	learningPointers()
	fmt.Println("\n---- Running pointerTourMethod ----")
	pointerTourMethod()
	fmt.Println("\n---- Running learningStructs ----")
	learningStructs(5, 7)
	fmt.Println("\n---- Running practicingSlices ----")
	practicingSlices()
	fmt.Println("\n---- Running practicingSlices2 ----")
	practicingSlices2()
	fmt.Println("\n---- Running practicingSlices3 ----")
	practicingSlices3()
	fmt.Println("\n---- Running slicesTourExample ----")
	slicesTourExample()
	fmt.Println("\n---- Running learningRange ----")
	learningRange()
}

func learningPointers() {
	i := 3306
	fmt.Println("Pointer address", &i)
	fmt.Println("Pointer value", i)
	pointerMethod(i)
	fmt.Println("Value of i after calling method", i)
	pointerMethod2(&i)
	fmt.Println("Value of i after calling method 2", i)
}

func pointerMethod(i int) {
	i += 10
	fmt.Println("Pointer of i in method", &i)
	fmt.Println("Value of i in method", i)
}

func pointerMethod2(i *int) {
	*i += 20
	fmt.Println("Pointer of i in method", i)
	fmt.Println("Value of i in method", *i)
}

func pointerTourMethod() {
	i, j := 42, 2701

	p := &i         // point to i
	fmt.Println(*p) // read i through the pointer
	*p = 21         // set i through the pointer
	fmt.Println(i)  // see the new value of i

	p = &j         // point to j
	*p = *p / 37   // divide j through the pointer
	fmt.Println(j) // see the new value of j
}

type Vertex struct {
	X int
	Y int
}

func learningStructs(x, y int) {
	fmt.Println("Creating struct with values", x, y)
	created := Vertex{x, y}
	origin := Vertex{X: 0, Y: 0}
	fmt.Println("created:", created, "origin:", origin)
	distance := math.Sqrt(float64((created.X-origin.X)^2) + float64((created.Y-origin.Y)^2))
	fmt.Println("Distance between points:", distance)
}

func practicingSlices() {
	// This is an array with fixed size
	menuItems := [5]string{"burger", "nuggets", "cheese sticks", "hot wings", "pizza"}
	fmt.Println("menu items:", menuItems)

	// This is a slice
	var userOrder []string
	userOrder = append(userOrder, menuItems[rand.Intn(5)],
		menuItems[rand.Intn(5)], menuItems[rand.Intn(5)]) // slice contains original values + appended items
	fmt.Println("user order:", userOrder)

	promotionalItems := menuItems[0:2]
	fmt.Println("promotional items:", promotionalItems)
}

func practicingSlices2() {
	// init a slice of length
	s := []int{2, 4, 6, 8, 10}
	fmt.Printf("len=%d cap=%d %v %p\n", len(s), cap(s), s, &s)

	// Slice to 0 sized
	s = s[:0]
	fmt.Printf("len=%d cap=%d %v %p\n", len(s), cap(s), s, &s)

	// Extend to max
	s = s[:5]
	fmt.Printf("len=%d cap=%d %v %p\n", len(s), cap(s), s, &s)

	// Over capacity by appending new value
	s = append(s, 5)
	fmt.Printf("len=%d cap=%d %v %p\n", len(s), cap(s), s, &s)
}

func practicingSlices3() {
	// Declare slice variable
	var s []string
	fmt.Println(s, len(s), cap(s))
	// Slice zero value is nil, similar to null in java
	if (s == nil) {
		fmt.Println("s is nil")
	}

	// Create slices using make
	f := make([]int, 5) // creates a slice of length 5, slice refers to a zeroed array created by make
	b := make([]int, 3, 5) // creates a slice of length 3, capacity 5
	fmt.Printf("f :: len=%d cap=%d %v %p\n", len(f), cap(f), f, &f)
	fmt.Printf("b :: len=%d cap=%d %v %p\n", len(b), cap(b), b, &b)
	b = append(b, 7, 7, 7)
	fmt.Printf("b :: len=%d cap=%d %v %p\n", len(b), cap(b), b, &b)
}

func slicesTourExample() {
	fmt.Println("2D slices example, a slice can contain any type, in this case it's another slice")
	// Create a tic-tac-toe board.
	board := [][]string{
		{"_", "_", "_"},
		{"_", "_", "_"},
		{"_", "_", "_"},
	}

	// The players take turns.
	board[0][0] = "X"
	board[2][2] = "O"
	board[1][2] = "X"
	board[1][0] = "O"
	board[0][2] = "X"

	for i := 0; i < len(board); i++ {
		fmt.Printf("%s\n", strings.Join(board[i], " "))
	}
}

func learningRange() {
	var baz = []int{1, 2, 3, 4, 5}
	// foreach, using range returns 2 values, and index and a copy of the element at the index
	for index, value := range baz {
		value++ // only increases value of the copy of element, not the actual element
		fmt.Printf("Index in baz: %d, Value in baz: %d\n", index, value)
	}
	fmt.Println(baz)

	for index := range baz {
		fmt.Println("baz index:", index)
	}

	for _, value := range baz {
		//fmt.Println("baz index:", _) // not allowed
		fmt.Println("baz value:", value)
	}
}