function compute() {
	//If it is in a form with attribute name = formAdd
	var num1 = parseInt(formAdd.num1.value);
	var num2 = parseInt(formAdd.num2.value);
	var sum = ((x,y)=>x+y)(num1,num2);
	
	formAdd.sum.value = sum;
	
}

function compute2() {
	var num1 = parseInt(document.getElementById("num1").value);
	var num2 = parseInt(document.getElementById("num2").value);
	
	var sum = ((x,y)=>x+y)(num1,num2);
	
	document.getElementById("sum").value = sum;
}

