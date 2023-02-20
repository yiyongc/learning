function getImage(url) {
	return new Promise(function(resolve, reject) {
		var img = new Image();
		img.onload = function() {
			resolve(url);
		}
		img.onerror = function() {
			reject(url);
		}
		img.src = url
	})
}


function testPromise() {
	getImage('chawanmushi.jpg').then(
			function(successurl) {
				document.getElementById('cnt').innerHTML = '<img src="'+successurl+'"/>';
			},
			function(errorurl) {
				console.log('Error loading ' + errorurl);
			}
	)
}


function testPromise2() {
	getImage('../chawanmushi.jpg').then(
			function(successurl) {
				document.getElementById('cnt').innerHTML = '<img src="' + successurl + '"/>';
			}
	).catch(function(errorurl) {
		console.log('Error loading ' + errorurl);
	})
}


function displayImages(images) {
	var doggyplayground = document.getElementById('doggyplayground');
	
	var targetimage = images.shift() //images is an array, shift will 
									 //process the doggy images one at at time
	
	if (targetimage) {
		getImage(targetimage).then(
				function(successurl) {
					var dog = document.createElement('img');
					dog.setAttribute('src', successurl);
					doggyplayground.appendChild(dog);
					displayImages(images);		//Recursive call to function to load other images
				}
		).catch(function(errorurl) {
			console.log('Error loading ' + errorurl);
			displayImages(images);
		})
	}
}


function demo1() {
	var doggies = ['dogimages/dog1.jpg', 'dogimages/dog2.jpg', 'dogimages/dog3.jpg', 'dogimages/dog4.png'];
	displayImages(doggies);
}