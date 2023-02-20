function populateField(valueGiven) {
	for (i = 0; i < document.getElementById("producttype").length; ++i){
	    if (document.getElementById("producttype").options[i].value == valueGiven){
	      document.getElementById("producttype").selectedIndex = i;
	    }
	}
}