package main

import (
	"fmt"
	"math"
)

func main() {
	fmt.Println("---- Running learningTypeMethods ----")
	learningTypeMethods()
	fmt.Println("\n---- Running testMethodOnNonStruct ----")
	testMethodOnNonStruct()
	fmt.Println("\n---- Running testPointerReceiverMethods ----")
	testPointerReceiverMethods()
	fmt.Println("\n---- Running testPointerIndirection ----")
	testPointerIndirection()
	fmt.Println("\n---- Running learningInterface ----")
	learningInterface()
	//fmt.Println("\n---- Running learningInterface ----")
	//learningInterface()
}

type Vertex struct {
	X, Y float64
}

// This is a method of the type Vertex
// As Go does not have classes, this type method has the receiver as "Vertex v"
func (v Vertex) Abs() float64 {
	return math.Sqrt(v.X * v.X + v.Y * v.Y)
}

func learningTypeMethods() {
	v := Vertex{3, 4}
	fmt.Println(v.Abs())
}

type MyFloat float64

func (f MyFloat) isFloatPositive() bool {
	if f < 0 {
		return false
	} else {
		return true
	}
}

func testMethodOnNonStruct() {
	f := MyFloat(4.55)
	fmt.Println(f.isFloatPositive())
}

func (v *Vertex) Scale(f float64) {
	v.X *= f
	v.Y *= f
}

// If pointer func is non-pointer receiver, the value will not be scaled
//func (v Vertex) Scale(f float64) {
//	v.X *= f
//	v.Y *= f
//}

func testPointerReceiverMethods() {
	v := Vertex{1, 1}
	v.Scale(1.23)
	fmt.Println("Scaled v", v)
}

func (v *Vertex) conveniencePointerMethod() {
	fmt.Println("Convenience Pointer Method:", v.X, v.Y)
}

func (v Vertex) pointerIndirectionMethod() {
	fmt.Println("Pointer Indirection:", v.X, v.Y)
}

func testPointerIndirection() {
	v := Vertex{3, 3}
	p := &v
	v.conveniencePointerMethod() // This works because go interprets it as (&v).conveniencePointerMethod
	p.conveniencePointerMethod() // This works of course

	// The reverse is true for pointer indirection
	v.pointerIndirectionMethod()
	p.pointerIndirectionMethod()
}

// Interface that has set of method signatures
type Abser interface {
	Abs() float64
}

type A float64

func (a A) Abs() float64 {
	if a < 0 {
		return float64(-a)
	}
	return float64(a)
}

type B struct {
	C, D float64
}

func (b *B) Abs() float64 {
	return math.Sqrt(b.C * b.C + b.D * b.D)
}

func learningInterface() {
	var myInterface Abser // declare variable with interface type
	a := A(12.34)
	b := B{4, 5}
	myInterface = a
	fmt.Println(myInterface.Abs()) // This works
	myInterface = &b
	fmt.Println(myInterface.Abs()) // This works
	//myInterface = b // This fails as B does not have a method Abs that has a B receiver (receiver is actually *B)

	// Can also assign directly as interfaces are implemented implicitly
	var c Abser = A(154.23)
	fmt.Println(c.Abs())
}
