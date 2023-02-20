function letExample() {
	let links = document.getElementsByTagName('a');
	for(let i = 0; i < links.length; i++) {
		links[i].onclick = function() {
			alert('You clicked on link ' + (i+1));
		}
	}
}

/*function test() {
	console.log(dog);	//reference error
	let dog = 'spotty';
}*/

function test2() {
	console.log(dog); 	//returns undefined
	var dog= 'spotty';	//var dog is auto-hoisted to the top
	console.log(dog);	//returns spotty
}

function test3() {
	const mydog = 'spotty';
		  mydog = 'fluffy';  //error: assignment to constant

	//const mybrother;		 //error: Missing initializer in const	
}

function test4() {
	const myobject =  { name:'George', age:39 };
	//myobject = { name:'Ken', age:39 } //error: trying to change const object
	myobject.age = 40; 					//Ok: const object properties are mutable
	
	const myarray = [];
	myarray[0] = 'Football'; //Ok
}