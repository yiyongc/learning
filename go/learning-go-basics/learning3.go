package main

import (
	"fmt"
	"regexp"
	"strings"
)

func main() {
	fmt.Println("---- Running learningMap ----")
	learningMap()
	fmt.Println("\n---- Running mapInitialization ----")
	mapInitialization()
	fmt.Println("\n---- Running mutatingMaps ----")
	mutatingMaps()
	fmt.Println("\n---- Running mapsExercise ----")
	mapsExercise()
	fmt.Println("\n---- Running passingFunctions ----")
	var result = passingFunctions(add, subtract)
	fmt.Println("Result:", result)
	fmt.Println("\n---- Running closureFunctions ----")
	closureFunctions()
	fmt.Println("\n---- Running closurePracticeFibonacci ----")
	closurePracticeFibonacci()
}

type PosVertex struct {
	Lat, Long float64
}

func learningMap() {
	var myMap map[string]PosVertex // Create a map of key with type string and value PosVertex
	fmt.Println("myMap default zero value:", myMap)
	if myMap == nil {
		fmt.Println("myMap is nil")
	}

	// PosD cannot be added because myMap is nil
	//myMap["PosD"] = PosVertex{}

	myMap = make(map[string]PosVertex) // Initialize map
	fmt.Println("myMap:", myMap)
	if myMap != nil {
		fmt.Println("myMap is not nil after make")
	}

	myMap["posA"] = PosVertex{30.3030, 44.44444} // set key value pair
	fmt.Println("myMap:", myMap)
}

func mapInitialization() {
	// Create map with values, make is not required?
	var m = map[string]PosVertex{
		"PosB": {31.3131, 66.7788},
		"PosC": {44.1212, 12.1234},
	}
	fmt.Println("m:", m)
	// PosD can be added
	m["PosD"] = PosVertex{}
	fmt.Println("m:", m)
}

func mutatingMaps() {
	// Initialize a map with values
	var m = map[string]string{
		"foo": "exists",
		"bar": "also exists",
		"baz": "exists as well",
	}
	fmt.Println("m:", m)

	// Adding element
	m["boo"] = "my bad"
	fmt.Println("m:", m)

	// Removing element
	delete(m, "boo")
	delete(m, "boos") // what if it does not exist?
	fmt.Println("m:", m)

	// Checking if element exists
	elem, exists := m["boo"]
	if exists {
		fmt.Println("Element exists:", elem)
	} else {
		fmt.Println("Element does not exist:", elem)
	}
	elem, exists = m["baz"]
	if exists {
		fmt.Println("Element exists:", elem)
	} else {
		fmt.Println("Element does not exist:", elem)
	}
}

/**
 * Maps exercise, create a function WordCount that returns a map of the counts of each word in a given string
 */
func mapsExercise() {
	s := "This is the string that I want you to count. Is there a problem with   it? What is tough about this?"
	fmt.Println("This is the word count for the sentence:", WordCount(s))
}

func WordCount(s string) map[string]int {
	// Replace non-word characters (excluding whitespace) and change it to lower case
	var regex = regexp.MustCompile("[^\\w ]")
	s = regex.ReplaceAllString(s, "")
	s = strings.ToLower(s)
	fmt.Println("Cleaned sentence before word count:", s)

	// Similar to java's string.split
	words := strings.Fields(s)
	wordCount := make(map[string]int)

	for _, word := range words {
		_, exist := wordCount[word]
		if exist {
			wordCount[word]++
		} else {
			wordCount[word] = 1
		}
	}
	return wordCount
}

func add(x, y int) int {
	return x + y
}

func subtract(x, y int) int {
	return x - y
}

func passingFunctions(fn func(int, int) int, fn2 func(int, int) int) int {
	return 3*fn(5, 6) - fn2(6, 2)
}

func closureFunctions() {
	closure1, closure2 := returnsClosure(), returnsClosure()
	for i := 1; i <= 5; i++ {
		fmt.Println("Multiply iteration ::", i, ":: closure 1 ::", closure1(i), ":: closure 2 ::", closure2(i*2))
	}
}

func returnsClosure() func(x int) int {
	multiplyResult := 5
	// Notice EACH closure returned has its own "multiplyResult" variable
	// Calling the SAME closure will modify its own multiplyResult value
	return func(x int) int {
		multiplyResult *= x
		return multiplyResult
	}
}

func fibonacci() func() int {
	prev := 0
	curr := 0

	return func() int {
		if prev == 0 && curr == 0 {
			curr = 1
			return 1
		} else if prev == 0 && curr == 1 {
			prev = 1
			curr = 1
			return 1
		} else {
			temp := curr
			curr += prev
			prev = temp
			return curr
		}
	}
}

func closurePracticeFibonacci() {
	f := fibonacci()
	for i := 0; i < 10; i++ {
		fmt.Println("Fibonacci sequence value:", f())
	}
}
