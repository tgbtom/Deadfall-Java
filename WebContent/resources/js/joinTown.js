function setListeners() {
  var addBtn = document.getElementById("addChar");

  addBtn.addEventListener("click", function () {
    var charId = document.getElementById("charOption").value;
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
      if (this.status == 200 && this.readyState == 4){
          var charOptions = document.getElementById("charOptions");
          var charsSelected = document.getElementById("charsSelected");
          var availableTowns = document.getElementById("availableTowns");
          var sections = this.responseText.split("&");
          if (sections[0].search("<option") == -1) {
            addBtn.setAttribute("disabled", "disabled");
          }
          charOptions.innerHTML = sections[0];
          charsSelected.innerHTML = sections[1];
          availableTowns.innerHTML = sections[2];
          setupJoinButtons();
      }
    };
    xhttp.open("POST", "JoinTownAjax/add");
    xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhttp.send("charId=" + charId);
  });
  
  setupJoinButtons();
}

function setupJoinButtons(){
	var joinButtons = document.getElementsByClassName("join-town");
	for (var i = 0; i < joinButtons.length; i++) {
		var button = joinButtons[i];
		button.addEventListener("click", function(){
			var xhttp = new XMLHttpRequest();
			xhttp.onreadystatechange = function(){
				if (this.status == 200 && this.readyState == 4){
					console.log(this.responseText);
					if(this.responseText.includes("fail")){
						window.location.href= "../dashboard";
						console.log("redirect");
					}
				}
			}
			xhttp.open("POST", "ajax/town/join");
			xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
			xhttp.send("townId=" + this.id);
		});
	}
}

function unselectChar(charId){
	var addBtn = document.getElementById("addChar");
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
      if (this.status == 200 && this.readyState == 4){
          var charOptions = document.getElementById("charOptions");
          var charsSelected = document.getElementById("charsSelected");
          var availableTowns = document.getElementById("availableTowns");
          var sections = this.responseText.split("&");
          if (sections[0].search("<option") != -1) {
            addBtn.removeAttribute("disabled");
          }
          charOptions.innerHTML = sections[0];
          charsSelected.innerHTML = sections[1];
          availableTowns.innerHTML = sections[2];
          setupJoinButtons();
      }
    };
    xhttp.open("POST", "JoinTownAjax/remove");
    xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhttp.send("charId=" + charId);
}
