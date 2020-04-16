function setListeners() {
  var addBtn = document.getElementById("addChar");

  addBtn.addEventListener("click", function () {
    var charId = document.getElementById("charOption").value;
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
      var charOptions = document.getElementById("charOptions");
      var charsSelected = document.getElementById("charsSelected");
      var sections = this.responseText.split("&");
      charOptions.innerHTML = sections[0];
      charsSelected.innerHTML = sections[1];
    };
    xhttp.open("POST", "JoinTownAjax");
    xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhttp.send("charId=" + charId);
  });
}
