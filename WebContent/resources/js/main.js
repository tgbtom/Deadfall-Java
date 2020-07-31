window.onload = function () {
  if (window.innerWidth <= 768) {
    document.getElementById("nav-mobile").style.display = "block";
  } else {
    document.getElementById("nav-desktop").style.display = "block";
  }

  inventoryPopups();

  window.addEventListener("click", function(e){
	  if(e.target.className != "character-item-group" && e.target.className != "character-item" && e.target.className != "character-item-counter"){
		  hideAllItemPopups();
	  }
  });
  
  window.addEventListener("resize", function () {
	  hideAllItemPopups();
    if (window.innerWidth <= 768) {
      document.getElementById("nav-desktop").style.display = "none";
      document.getElementById("nav-mobile").style.display = "block";
    } else {
      document.getElementById("nav-desktop").style.display = "block";
      document.getElementById("nav-mobile").style.display = "none";
    }
  });

  if (typeof formJs == "function") {
    formJs();
  }

  if (typeof setListeners == "function") {
    setListeners();
  }

  if (typeof confirmDelete == "function") {
    confirmDelete();
  }

  if (typeof setModalListeners == "function") {
    setModalListeners();
    exitModalListener();
  }

  if (typeof storagePickUp == "function") {
    storagePickUp();
  }

  var pageMessage = document.getElementById("page-message");
  var msgClose = document.getElementById("msg-close");

  if (msgClose != null) {
    msgClose.addEventListener("click", function () {
      pageMessage.style.display = "none";
    });
  }
};

function exitModalListener() {
  var exitButton = document.getElementById("modal-exit");
  exitButton.addEventListener("click", function () {
    hideModals();
  });
}

function hideModals() {
  var modals = document.getElementsByClassName("modal");
  for (let i = 0; i < modals.length; i++) {
    modals[i].style.display = "none";
  }
  var modalContainer = document.getElementById("modal-container");
  modalContainer.style.display = "none";
}

function showModalContainer() {
  var modalContainer = document.getElementById("modal-container");
  modalContainer.style.display = "block";
}

function inventoryPopups() {
  var itemStacks = document.getElementsByClassName("character-item-group");
  for (let i = 0; i < itemStacks.length; i++) {
    var stack = itemStacks[i];
    let node = document.createElement("span");
    node.id = "item-popup-" + stack.id.split("-")[2];
    node.className += "popup-text";

    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function(){
    	if(this.readyState == 4 && this.status == 200){
    		let properties = this.responseText.split(";");
    		let name = properties[0].split(":")[1];
    		let rarity = properties[1].split(":")[1];
    		let mass = properties[2].split(":")[1];
    		let category = properties[3].split(":")[1];
    		let id = properties[4].split(":")[1];
    		let functions = properties[5].split(":")[1].split(",");
    		node.innerHTML = "<b>" + name + "</b> <span id='popup-rarity-"+ id +"'>"+ rarity +"</span> <br>Mass:"+ mass +" "+ category + "<br>";
    		
    		for(let k=0; k < functions.length; k++){
    			node.innerHTML += "<button class='btn btn-act' onclick='itemAction(`"+ functions[k] +"`, "+ id +")'>" + functions[k] + "</button><br>";
    		}

    	    stack.appendChild(node);
    	    document.getElementById("popup-rarity-" + id).style.color = colourizeRarity(rarity);
    	}
    };
    
    stack.addEventListener("click", function(){
    	let popup = document.getElementById("item-popup-" + this.id.split("-")[2]);
    	if(popup.style.display == "block"){
    		popup.style.display = "none";
    	}
    	
    	else{
    		hideAllItemPopups();
    		popup.style.display = "block";
    		popup.style.left = this.offsetLeft - 49;
    		popup.style.top = (this.offsetTop + (-1 * popup.clientHeight -8));
    	}
    });
    
    xhttp.open("POST", "/Deadfall/item/ajax/details");
    xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhttp.send("itemId=" + stack.id.split("-")[2]);

  }
}

function itemAction(action, itemId){
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function(){
		if(this.readyState == 4 && this.status == 200){
			//Refresh page when action completes
			location.reload();
		}
	};
	xhttp.open("POST", "../item/action/" + action);
	xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhttp.send("itemId=" + itemId);
}

function hideAllItemPopups(){
	var popups = document.getElementsByClassName("popup-text");
	for(let i = 0; i < popups.length; i++){
		popups[i].style.display = "none";
	}
}

function colourizeRarity(rarity) {
  switch (rarity) {
    case "Common":
      return "#848484";
    case "UnCommon":
      return "#00cc66";
    case "Rare":
      return "#0088f7";
    case "Epic":
      return "#be00ff";
    case "Legendary":
      return "#ff9700";
    case "Scrap":
      return "#5f5d5d";
    default:
      break;
  }
}
