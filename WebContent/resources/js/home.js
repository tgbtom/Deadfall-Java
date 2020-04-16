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
