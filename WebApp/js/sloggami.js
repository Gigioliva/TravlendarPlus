$(document).ready(function(){
	var token  = localStorage.getItem("my_travlendar");
	if (token === null) {
		location.assign("index.html")
	}

});