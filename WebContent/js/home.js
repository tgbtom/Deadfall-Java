function setModalListeners() {
  var loginButton = document.getElementById("login-btn");
  var registerButton = document.getElementById("register-btn");
  var loginModal = document.getElementById("modal-login");
  var registerModal = document.getElementById("modal-register");

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
}

function formJs() {
	var classSelect = document.getElementById("char-class");
	var classDesc = document.getElementById("class-desc");
	classSelect.addEventListener("change", function(){
		switch (this.value) {
		case "Survivor":
			classDesc.innerHTML = "The survivor class is well rounded, having a maximum AP " +
					"of 16 rather than 12. The survivor does not specialize in anything.";
			break;
		case "Builder":
			classDesc.innerHTML = "The builder class specializes in contributing to construction sites. " +
					"Contributes 2x AP and gains 2x XP for constructions.";
			break;
		case "Looter":
			classDesc.innerHTML = "The looter class specializes in looting outside of the settlement. " +
					"Looters have a chance to retain their AP when looting, and have a chance of looting non-scrap " +
					"items even from depleted zones. Looters gain extra XP when looting.";
			break;
		case "Runner":
			classDesc.innerHTML = "The runner class specializes in exploring the map. The runner is able to travel through" +
					" more dangerous zones and gains extra XP from moving outside of town.";
			break;
		default:
			break;
		}
	})
}
