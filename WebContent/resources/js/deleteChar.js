function confirmDelete(){
	var deleteBtn = document.getElementById("deleteChar");
	deleteBtn.addEventListener("click", function(e){
		if(! confirm("Are you sure you want to delete this character? All progress will be lost permanently.")){
			e.preventDefault();
		}
	});
}