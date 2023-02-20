function validateLogin() {
	
	var flag = true;
	
	var user = document.getElementById("username").value;
	var pass = document.getElementById("password").value;
	
	if (user.trim() == "") {
		flag = false;
		document.getElementById("userErr").innerHTML = "* Please enter a valid username!";
	} else {
		document.getElementById("userErr").innerHTML = "";
	}
	
	if (pass.trim() == "") {
		flag = false;
		document.getElementById("passErr").innerHTML = "* Please enter a valid password!";
	} else {
		document.getElementById("passErr").innerHTML = "";
	}
			
	return flag;
}

var productFlag = true;

function validateProductAdd() {
	productFlag = true;
	

	validateProductName();
	validateDate();
	validateQuantity();
	validatePrice();
	
	return productFlag;
}


function validateProductName() {
	var productName = document.getElementById("productName").value;

	if (productName.trim() == "") {

		productFlag = false;
		document.getElementById("productErr").innerHTML = "* Please enter a valid product name!";
	} else {
		document.getElementById("productErr").innerHTML = "";
	}
}

function validateDate() {
	var dateInput = document.getElementById("expirydate").value;
	
	if (dateInput == "") {
		productFlag = false;
		document.getElementById("dateErr").innerHTML = "* Please select a date!";
	}
	else {
		var dateParts = dateInput.split("-");
		var year = parseInt(dateParts[0]);
		var mth = parseInt(dateParts[1]) - 1;
		var day = parseInt(dateParts[2]);

		var currDate = new Date();

		if (year < currDate.getFullYear() ||
			(year == currDate.getFullYear() && mth < currDate.getMonth()) ||
			(year == currDate.getFullYear() && mth == currDate.getMonth() && day <= currDate.getDate())) {
			productFlag = false;
			document.getElementById("dateErr").innerHTML = "* Please select a later date!";
		} else {
			document.getElementById("dateErr").innerHTML = "";
		}
	}
}

function validateQuantity() {
	var qty = document.getElementById("quantity").value;
	
	qty = Number(qty);

	if (qty <= 0) {

		productFlag = false;
		document.getElementById("quantityErr").innerHTML = "* Quantity should be greater than zero!";
	} else {
		document.getElementById("quantityErr").innerHTML = "";
	}
}

function validatePrice() {
	var price = document.getElementById("price").value;
	price = Number(price);
	
	var minLimit = 50;
	var maxLimit = 1500;

	if (price < minLimit || price > maxLimit) {
		productFlag = false;
		document.getElementById("priceErr").innerHTML = "* Please enter a valid price!";
	} else {
		document.getElementById("priceErr").innerHTML = "";
	}
}