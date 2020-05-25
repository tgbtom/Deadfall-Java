function storagePickUp(){
	var stacks = document.getElementsByClassName("storage-item");
	
	for (i = 0; i < stacks.length; i++){
		stack = stacks[i];
		stack.addEventListener("click", function(){
			var xhttp = new XMLHttpRequest();
			var itemId = this.id.split("-")[1];
			xhttp.onreadystatechange = function(){
				if(this.status == 200 && this.readyState == 4){
					var imageId = "storage-" + itemId;
					var counterId = "stack-" + itemId;
					var count = document.getElementById(counterId);
					if(count.innerText > 1){
						count.innerText -= 1;
					}
					else{
						count.style.display = "none";
						document.getElementById(imageId).style.display = "none";
					}
					document.getElementById("current-char-items").innerHTML = this.responseText;
				}
			};
			xhttp.open("POST", "../character/ajax/pickitem");
			xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded")
			xhttp.send("itemId="+ itemId);
		});
	}
}