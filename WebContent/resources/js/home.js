function setModalListeners() {
  var loginButton = document.getElementById("login-btn");
  var registerButton = document.getElementById("register-btn");
  var loginModal = document.getElementById("modal-login");
  var registerModal = document.getElementById("modal-register");
  var featuredButton = document.getElementById("featured-item");
  var featuredModal = document.getElementById("modal-featured");

  loginButton.addEventListener("click", function() {
    hideModals();
    loginModal.style.display = "block";
    showModalContainer();
  });

  registerButton.addEventListener("click", function() {
    hideModals();
    registerModal.style.display = "block";
    showModalContainer();
  });
  
  featuredButton.addEventListener("click", function(){
	  hideModals();
	  featuredModal.style.display = "block";
	  showModalContainer();
  });
  
  setFeaturedItem();
}

function setFeaturedItem(){
	  var featuredImagePreview = document.getElementById("preview-featured-item");
	  var featuredImage = document.getElementById("modal-featured-item");
	
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function(){
	if(this.readyState == 4 && this.status == 200){
		var response = this.responseText.split("&");
		var contextPath = response[0];
		var itemName = response[2];
		document.getElementById("modal-featured-title").innerHTML = itemName;
		document.getElementById("featured-title").innerHTML = itemName;
		document.getElementById("modal-featured-desc").innerHTML = response[3];
		document.getElementById("featured-rarity").innerHTML = response[5];
		document.getElementById("featured-rarity").style.color = colourizeRarity(response[5]);
		document.getElementById("featured-mass").innerHTML = response[4];
		document.getElementById("featured-category").innerHTML = response[6];
		
		featuredImagePreview.src = contextPath + "/resources/img/items/" + itemName + ".png";
		featuredImage.src = contextPath + "/resources/img/featured/" + itemName + ".png";
		}
	};
	xhttp.open("GET", "featured/random");
	xhttp.send();
}
