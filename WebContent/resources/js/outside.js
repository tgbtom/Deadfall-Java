function loot(){
	
	var lootability = document.getElementById("zoneLootability").innerHTML;
	var danger = document.getElementById("zoneDanger").innerHTML;
	var confirm = true;
	if(lootability <= 0){
		if(danger > 0){
			confirm = window.confirm("You are about to search a depleted zone, are you sure you would like to continue? Note: The zone is in danger, " +
					"there is a " + (danger * 10) + "% chance you will get injured while searching! Try killing zeds to make the zone safe");
		}else{
			confirm = window.confirm("You are about to search a depleted zone, are you sure you would like to continue?");
		}
	} else if(danger > 0){
		confirm = window.confirm("The zone is in danger, " +
				"there is a " + (danger * 10) + "% chance you will get injured while searching! Try killing zeds, or bringing more survivors to make the zone safe");
	}
	if(confirm){
		var xhttp = new XMLHttpRequest();
		
		xhttp.onreadystatechange = function(){
			if(this.readyState == 4 && this.status == 200){
				location.reload();
			}
		};
		
		xhttp.open("POST", "../character/loot");
		xhttp.send();
	}
}