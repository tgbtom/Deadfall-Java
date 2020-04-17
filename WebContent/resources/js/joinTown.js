function setListeners() {
  var addBtn = document.getElementById("addChar");

  addBtn.addEventListener("click", function () {
    var charId = document.getElementById("charOption").value;
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
      if (this.status == 200 && this.readyState == 4){
          var charOptions = document.getElementById("charOptions");
          var charsSelected = document.getElementById("charsSelected");
          var sections = this.responseText.split("&");
          if (sections[0].search("<option") == -1) {
            addBtn.setAttribute("disabled", "disabled");
          }
          charOptions.innerHTML = sections[0];
          charsSelected.innerHTML = sections[1];
      }
    };
    xhttp.open("POST", "JoinTownAjax");
    xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhttp.send("charId=" + charId);
  });
}
