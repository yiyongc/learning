var myCallBackExample = {
			myFirstFunction : function (param1, param2, callback) {
				if(arguments.length == 3) {
					alert("3 args");
					callback();
				}
			},
			mySecondFunction : function() {
				myCallBackExample.myFirstFunction(
						true, false, function() {
					alert("this is a callback");
				})	
			}
}

function demo() {
	myCallBackExample.mySecondFunction();
}

function method1(var1, var2, callback) {
	alert(var1 + var2);
	
	if (callback)
		callback(var1, var2);
	
	return 'lame string';
}

function demo2() {
	var fn = method1(10, 13, function(x,y) { alert(x*y); } );
	
	alert(fn);
}