window.onload = function() {
  if (window.innerWidth <= 768) {
    document.getElementById("nav-mobile").style.display = "block";
  } else {
    document.getElementById("nav-desktop").style.display = "block";
  }

  window.addEventListener("resize", function() {
    if (window.innerWidth <= 768) {
      document.getElementById("nav-desktop").style.display = "none";
      document.getElementById("nav-mobile").style.display = "block";
    } else {
      document.getElementById("nav-desktop").style.display = "block";
      document.getElementById("nav-mobile").style.display = "none";
    }
  });

  if (typeof formJs == "function"){
	  formJs();
  }
  
  if (typeof setListeners == "function"){
	  setListeners();
  }
  
  if (typeof setModalListeners == "function") {
    setModalListeners();
    exitModalListener();
  }
  
  var pageMessage = document.getElementById("page-message");
  var msgClose = document.getElementById("msg-close");
  
  if(msgClose != null){
	  msgClose.addEventListener("click", function(){
		  pageMessage.style.display = "none";
	  });
  }
};

function exitModalListener() {
  var exitButton = document.getElementById("modal-exit");
  exitButton.addEventListener("click", function() {
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
