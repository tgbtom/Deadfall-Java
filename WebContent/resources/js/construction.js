function showStructureModal(structureId, name, description, costs, apCost, defence, level, maxLevel){
	var modal = document.getElementById("modal-structure");
	var title = document.getElementById("modal-structure-title");
	var desc = document.getElementById("modal-structure-desc");
	var defenceGiven = document.getElementById("modal-structure-defence");
	var modalLevel = document.getElementById("modal-structure-level");
	var modalMaxLevel = document.getElementById("modal-structure-max-level");
	var modalCosts = document.getElementById("modal-structure-costs");
	var modalApCost = document.getElementById("modal-structure-ap-cost");
	showModalContainer();
	
	var costsString = "";
	var items = costs.split(",");
	for(let i=0; i < items.length; i++){
		cost = items[i].split(":");
		item = cost[0];
		qty = cost[1];
		costsString += "<div class='citizens-item-group' title='"+ item +"'>" +
		"<img src='/Deadfall/resources/img/items/rarity/Rare.png' class='item-rarity' />" +
		"<img src='/Deadfall/resources/img/items/"+ item +".png' alt='"+ item +"' class='citizens-item' />" +
		"<span class='citizens-item-counter'> "+ qty +"</span>" +
		"</div>";
	}
	
	title.innerHTML = name;
	desc.innerHTML = description;
	defenceGiven.innerHTML = defence;
	modalLevel.innerHTML = level;
	modalMaxLevel.innerHTML = maxLevel;
	modalCosts.innerHTML = costsString;
	modalApCost.innerHTML = apCost;
	modal.style.display = "block";
}

function promptAp(currentAp){
	var amount = window.prompt("How much AP would you like to assign? Maximum: " + currentAp, 0);
	//Redirect user to a route that will process assigning the ap then return the contruction view
	window.location.href = "/Deadfall/town/construct/10/1";
}