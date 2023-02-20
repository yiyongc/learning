function Person(first, last, age, eyecolor) {
	this.firstName = first;
	this.lastName = last;
	this.age = age;
	this.eyeColor = eyecolor;
}

Person.prototype.name = function() {
	return this.firstName + " " + this.lastName;
};

function demo() {
	var p = new Person('hi', 'bye', 4, 'blue');
	var fullName = p.name();
	
	alert(fullName);
}