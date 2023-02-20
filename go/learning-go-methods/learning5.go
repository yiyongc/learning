package main

import (
	"fmt"
	"time"
	"math"
)

func main() {
	fmt.Println("---- Running LearningInterfaceValues ----")
	LearningInterfaceValues()
	fmt.Println("\n---- Running LearningEmptyInterface ----")
	LearningEmptyInterface()
	fmt.Println("\n---- Running LearningInterfaceTypeAssertions ----")
	LearningInterfaceTypeAssertions()
	fmt.Println("\n---- Running LearningTypeSwitch ----")
	LearningTypeSwitch()
	fmt.Println("\n---- Running LearningStringerInterface ----")
	LearningStringerInterface()
	fmt.Println("\n---- Running LearningErrorInterface ----")
	LearningErrorInterface()
}

type MyInterface interface {
	MyMethod()
}

type Type1 struct {
	MyString string
}

func (type1 *Type1) MyMethod() {
	if type1 == nil {
		fmt.Println("<nil>")
		return
	}
	fmt.Println(type1.MyString)
}

type MyFloat1 float64

func (f MyFloat1) MyMethod() {
	fmt.Println(f)
}

func describe(i MyInterface) {
	fmt.Printf("(%v, %T)\n", i, i)
}

func LearningInterfaceValues() {
	var i MyInterface
	
	// This causes a runtime error as the interface value is nil
	// Hence the compiler does not know which concrete implementation of MyMethod to run
	describe(i)
	// i.MyMethod();

	// Even though value of type1 is nil, 
	// the nil receiver passed to MyMethod would be gracefully handled instead of nullpointer exception
	var type1 *Type1
	i = type1
	describe(i)
	i.MyMethod()

	i = &Type1{ "Hello world" }
	describe(i)
	i.MyMethod()

	i = MyFloat1(1.23467)
	describe(i)
	i.MyMethod()
}

// This declares an empty interface, i.e. any argument will suffice for this method
func describeEmptyInterface(i interface{}) {
	fmt.Printf("(%v, %T)\n", i, i)
}

func LearningEmptyInterface() {
	var i interface{}
	describeEmptyInterface(i)

	i = 42
	describeEmptyInterface(i)

	i = "hello"
	describeEmptyInterface(i)
}

func LearningInterfaceTypeAssertions() {
	// Declare empty interface var
	var i interface{} = "hello I am a string"

	s := i.(string) // Assert that i is a string
	fmt.Println(s)

	s, ok := i.(string) // use variable ok to check if assertion passes
	fmt.Println(s, ok)

	f, ok := i.(float64) // By asserting with an 'ok' variable
	fmt.Println(f, ok) // f would be the zero value of float64 if i is not of type float64

	// assert that i is a float, but it is not
	// This causes 'panic' within the code
	// f = i.(float64) 
	// fmt.Println(f)
}

func doSomethingUsingTypeSwitch(i interface{}) {
	// Using type switch, the variable v will be of the specific type in each of the case
	switch v := i.(type) {
	case int:
		fmt.Printf("Twice %v is %v\n", v, v*2)
	case string:
		fmt.Printf("%q is %v bytes long\n", v, len(v))
	default:
		fmt.Printf("I don't know about type %T!\n", v)
	}
}

func LearningTypeSwitch() {
	doSomethingUsingTypeSwitch(21)
	doSomethingUsingTypeSwitch("byte")
	doSomethingUsingTypeSwitch(false)
}

type Person1 struct {
	Name string
	Age int
}

// By creating a String method for Person1, it implements the 'Stringer' interface which allows for fmt to print values
// Similar to the toString method in java
func (p Person1) String() string {
	return fmt.Sprintf("%v (%v years)", p.Name, p.Age)
}

type IPAddr [4]byte

func (ip IPAddr) String() string {
	return fmt.Sprintf("%v.%v.%v.%v", ip[0], ip[1], ip[2], ip[3])
}

func LearningStringerInterface() {
	p1 := Person1{"Apple Bottom Jeans", 22}
	p2 := Person1{"Boots with Fur", 33}
	fmt.Println(p1, p2)

	hosts := map[string]IPAddr{
		"loopback":  IPAddr{127, 0, 0, 1},
		"googleDNS": IPAddr{8, 8, 8, 8},
	}
	for name, ip := range hosts {
		fmt.Printf("%v: %v\n", name, ip)
	}
}

type MyError struct {
	When time.Time
	What string
}

// Implement Error() method for error interface
func (e *MyError) Error() string {
	return fmt.Sprintf("MyError encountered at %v, %s", e.When, e.What)
}

func functionThatThrowsError() error {
	return &MyError{
		time.Now(),
		"It didn't work yo",
	}
}

type ErrNegativeSqrt float64

func (e ErrNegativeSqrt) Error() string {
	return fmt.Sprint("cannot Sqrt negative number: ", float64(e))
}

func Sqrt(x float64) (float64, error) {
	if x < 0 {
		return 0, ErrNegativeSqrt(x)
	}
	return math.Sqrt(x), nil
}

func LearningErrorInterface() {
	if err := functionThatThrowsError(); err != nil {
		fmt.Println(err)
	}

	ans, err := Sqrt(9)
	if err != nil {
		fmt.Println(err)
	} else {
		fmt.Println("Sqrt answer:", ans)
	}
	ans, err = Sqrt(-9)
	if err != nil {
		fmt.Println(err)
	} else {
		fmt.Println("Sqrt answer:", ans)
	}
}