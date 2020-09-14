function loot(){
	var xhttp = new XMLHttpRequest();
	
	xhttp.onreadystatechange = function(){
		if(this.readyState == 4 && this.status == 200){
			console.log(this.responseText);
			location.reload();
		}
	};
	
	xhttp.open("POST", "../character/loot");
	xhttp.send();
}