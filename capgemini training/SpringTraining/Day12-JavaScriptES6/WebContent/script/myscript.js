function test() {
	alert("Hello world");
};

function letDemo() {
	let age=25;
	if(new Date().getFullYear() == 2017) {
		age = 32;
		console.log(age);
	}
	console.log(age);
};

/*function letDemo2() {
	let myage=39;
	let myname='Capgemini';
	let myage=20;
};*/


function letDemo3() {
	let mybrother='Paul';
	if(true) {
		let mybrother = 'Jason';
		console.log(mybrother);
	}
	console.log(mybrother);
};

function letDemo4() {
	for (var i = 0; i < 5; i++) {
		setTimeout(function() {
			console.log(i)
		}, i * 100);
	}
}

function letDemo5() {
	for (var i = 0; i < 5 ; i++) {
		(function(x) {
			setTimeout(function() {
				console.log(x)
			}, i * 100)
		})(i);
	}
}

function letDemo6() {
	for (let i = 0; i < 5; i++) {
		setTimeout(function() {
			console.log(i)
		}, i * 100);
	}
}