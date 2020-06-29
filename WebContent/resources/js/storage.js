function storagePickUp(){
	var stacks = document.getElementsByClassName("storage-item-group");
	
	for (i = 0; i < stacks.length; i++){
		stack = stacks[i];
		stack.addEventListener("click", function(){
			var xhttp = new XMLHttpRequest();
			console.log(this.id.split("-")[1]);
			var itemId = this.id.split("-")[1];
			xhttp.onreadystatechange = function(){
				if(this.status == 200 && this.readyState == 4){
					var imageId = "storage-" + itemId;
					var counterId = "stack-" + itemId;
					var count = document.getElementById(counterId);
					if(this.responseText !== "fail"){
						if(count.innerText > 1){
							count.innerText -= 1;
						}
						else{
							count.style.display = "none";
							document.getElementById(imageId).style.display = "none";
						}
						
						document.getElementById("current-char-items").innerHTML = this.responseText;
						inventoryPopups();
					}
				}
			};
			xhttp.open("POST", "../character/ajax/pickitem");
			xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded")
			xhttp.send("itemId="+ itemId);
		});
	}
}