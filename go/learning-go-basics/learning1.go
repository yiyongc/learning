package main

import (
	"fmt"
	"math"
	"math/cmplx"
	"math/rand"
	"reflect"
	"runtime"
	"time"
)

// Package variables declaration
var (
	MyBool  bool       = false
	MaxInt  uint64     = 1<<64 - 1
	MyFloat float64    = 6.969
	zNumber complex128 = cmplx.Sqrt(-5 + 12i)
	MyByte  byte       = byte('A') // alias for uint8
	MyRune  rune       = '\a'      // alias for int32, represents a Unicode code point
)

func main() {
	// This is the main function similar to java's public static void main(String[] args)
	fmt.Println("---- Running HelloWorld ----")
	helloWorld()
	fmt.Println("\n---- Running learningPrimitivesAndDeclarations ----")
	learningPrimitivesAndDeclarations()
	fmt.Println("\n---- Running learningReturnTypes ----")
	learningReturnTypes(7, 2, 0.3)
	fmt.Println("\n---- Running multipleReturns ----")
	fullName, age := multipleReturns("John", "Doe", 32)
	fmt.Println("His name is", fullName, "and he is", age, "years old")
	fmt.Println("\n---- Running namedReturns ----")
	price, quantity := namedReturns("green tea")
	fmt.Println("The price of green tea is $", price, "and there amount left is", quantity)
	price, quantity = namedReturns("red tea")
	fmt.Println("The price of red tea is $", price, "and there amount left is", quantity)
	fmt.Println("\n---- Running printPackageVariablesAndTypes ----")
	printPackageVariablesAndTypes()
	fmt.Println("\n---- Running playingWithConstants ----")
	playingWithConstants()
	fmt.Println("\n---- Running playingWithIfsAndLoops ----")
	playingWithIfsAndLoops()
	fmt.Println("\n---- Running playingWithIfsAndLoops ----")
	playingWithDefer()
}

func helloWorld() {
	fmt.Println("Hello World!")
	fmt.Println("Current time is ", time.Now())
	fmt.Println("Limit 10 :: Current Rand: ", rand.Intn(10))
	fmt.Println(math.Pi) // This is exported (starts with a capital letter), similar to private/public variables
}

func learningPrimitivesAndDeclarations() {
	// Integers
	var i int // default value for an int is 0
	j := 2    // Short hand for declaring variables with type inferred
	var k = 5 // Another way of declaring it

	fmt.Println("i:", i, "j:", j, "k:", k)

	// Boolean
	isInvalid := true
	var isValid bool // default zero value is false

	fmt.Println("isInvalid:", isInvalid, "isValid:", isValid)

	// Arrays
	a := [5]string{"Hello", "this", "is", "weird"} // declaring 1 less value

	fmt.Println(a[0])
	fmt.Println(a[1])
	fmt.Println(a[2])
	fmt.Println(a[3])
	fmt.Println(a[4]) // Default zero value is "", does not print null unlike java

	// Maps / Dict
	simpleMap := make(map[string]int)
	simpleMap["key1"] = 3
	simpleMap["key2"] = 6
	simpleMap["key3"] = 9
	fmt.Println(simpleMap["key1"])
	fmt.Println(simpleMap["key2"])
	fmt.Println(simpleMap["key3"])
	fmt.Println(simpleMap["key4"]) // This does not exist but returns default value for int which is 0
}

// This is the same as x int, y int, z float32
func learningReturnTypes(x, y int, z float32) float32 {
	// This function adds the numbers and divides by z
	fmt.Println(reflect.TypeOf(x), reflect.TypeOf(y)) // Checking types
	a := float32(x)
	b := float32(y)
	fmt.Println(reflect.TypeOf(a), reflect.TypeOf(b))
	return (a + b) / z
}

func multipleReturns(firstName, lastName string, age int) (string, int) {
	return firstName + " " + lastName, age
}

// Avoid this named return values function
func namedReturns(itemToPurchase string) (price, quantity int) {
	if itemToPurchase == "green tea" {
		price = 2 // No need to redeclare variables as they have been named in the return signature
		quantity = 10
	} else {
		price = 1
		quantity = 100
	}
	return // This is a naked return, it returns based on the declared named return values
}

func printPackageVariablesAndTypes() {
	fmt.Println("MyBool:", MyBool, "Type:", reflect.TypeOf(MyBool))
	fmt.Println("MaxInt:", MaxInt, "Type:", reflect.TypeOf(MaxInt))
	fmt.Println("MyFloat:", MyFloat, "Type:", reflect.TypeOf(MyFloat))
	fmt.Println("zNumber:", zNumber, "Type:", reflect.TypeOf(zNumber))
	fmt.Println("MyByte:", MyByte, "Type:", reflect.TypeOf(MyByte))
	fmt.Println("MyRune:", MyRune, "Type:", reflect.TypeOf(MyRune))
}

func playingWithConstants() {
	const bae = "true"
	//bae = "no" // Not allowed as it is a const
	fmt.Println("Bae?", bae)

	const Big = 1 << 69     // Binary shift left 69 positions ;)
	const Small = Big >> 68 // Binary shift right 68 positions
	fmt.Println(numericConstantsInt(Small))
	fmt.Println(numericConstantsFloat(Small))
	//fmt.Println(numericConstantsInt(Big)) // Not allowed as it is overflows int size
	fmt.Println(numericConstantsFloat(Big))
}

func numericConstantsInt(x int) int {
	fmt.Println("x: ", x)
	return x*2 + 5
}

func numericConstantsFloat(x float64) float64 {
	fmt.Println("x: ", x)
	return x * 0.5
}

func playingWithIfsAndLoops() {
	sum := 0
	for i := 0; i < 10; i++ {
		sum += i
	}
	fmt.Println("Sum:", sum)

	// while loop
	total := 0
	for {
		total += 5
		if total > 25 {
			break
		}
	}
	fmt.Println("Total:", total)

	// for loop with loop condition
	for total > 0 {
		total -= 3
	}
	fmt.Println("Total:", total)

	// if condition with scoped local variable
	if v := 33; v < total {
		fmt.Println("V is less: ", v, "<", total)
	} else {
		fmt.Println("V is more: ", v, ">", total)
	}
	// v = 6 // local variable no longer accessible

	// Switch case
	myVar := "apples"
	switch myVar {
	case "apples":
		fmt.Println("You like apples") // Notice that you do not require a break statement unlike java
	default:
		fmt.Println("You don't like apples")
	}
	switch os := runtime.GOOS; os { // Declare local scoped variable within switch
	case "darwin":
		fmt.Println("OS X.")
	case "linux":
		fmt.Println("Linux.")
	default:
		// freebsd, openbsd,
		// plan9, windows...
		fmt.Printf("%s.\n", os)
	}
	// os = "no OS" // No longer accessible
}

func playingWithDefer() {
	stackingDefers()
	// This acts similar to a finally block in java, can be used for clean up, closing resources etc.
	defer fmt.Println("work?")
	fmt.Printf("How ")
	fmt.Printf("does ")
	fmt.Printf("defer ")
}

func stackingDefers() {
	// Stacking defers
	fmt.Println("counting")
	for i := 0; i < 10; i++ {
		defer fmt.Println(i)
	}
	fmt.Println("done")
}
