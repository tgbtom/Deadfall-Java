<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Df - Home</title>
    <link
      href="https://fonts.googleapis.com/css?family=Khand"
      rel="stylesheet"
    />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/main.css">

    <script src="${pageContext.request.contextPath}/resources/js/main.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/home.js"></script>
  </head>
  <body>
    <% if (request.getSession().getAttribute("message") != null){ %>
    <div class="page-message" id="page-message">
      <%= request.getSession().getAttribute("message") %>
      <img src="${pageContext.request.contextPath}/resources/img/small-x.png" id="msg-close" alt="close message" />
    </div>
    <%} request.getSession().removeAttribute("message"); %>

    <nav class="nav-bar" id="nav-desktop">
      <button class="btn-nav left">Homepage</button>
      <button class="btn-nav right" id="login-btn">Login</button>
      <button class="btn-nav right" id="register-btn">Register</button>
    </nav>

    <nav class="nav-bar" id="nav-mobile">
      <button class="btn-nav left dropdown-btn">
        <img src="${pageContext.request.contextPath}/resources/img/hamburger-menu.png" alt="Menu" />
        <div class="dropdown-content">
          <div class="dropdown-link">Homepage</div>
          <div class="dropdown-link">Login</div>
          <div class="dropdown-link">Register</div>
        </div>
      </button>
    </nav>

    <header id="banner">
      <img src="${pageContext.request.contextPath}/resources/img/MainBanner.png" id="banner-img" alt="Deadfall Banner" />
    </header>

    <div class="container">
      <div class="row">
        <div class="col-4">
          <div class="card">
            <div class="card-top">Something to know</div>
            <div class="card-content">
              The <yellow>Vegetable Garden</yellow> will help maintain a food
              supply for your survivors, at the expense of water.
            </div>
          </div>
        </div>
        <div class="col-4">
          <div class="card">
            <div class="card-top">Global Stats</div>
            <div class="card-content">
              <div class="row bb">
                <div class="col-6 text-center text-bold">
                  Days Survived:
                </div>
                <div class="col-6 text-center">
                  0
                </div>
              </div>
              <div class="row bb">
                <div class="col-6 text-center text-bold">
                  Towns:
                </div>
                <div class="col-6 text-center">
                  0
                </div>
              </div>
              <div class="row bb">
                <div class="col-6 text-center text-bold">
                  Character Deaths:
                </div>
                <div class="col-6 text-center">
                  0
                </div>
              </div>
              <div class="row bb">
                <div class="col-6 text-center text-bold">
                  Zeds Killed:
                </div>
                <div class="col-6 text-center">
                  0
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="col-4">
          <div class="card">
            <div class="card-top" id="featured-title"></div>
            <div class="card-content">
              <div class="row">
                <div class="sub-4"><!-- spacer --></div>
                <div class="sub-4" id="featured-item">
                  <div class="card bg-faded text-center" id="featured">
                    <img
                      src=""
                      alt=""
                      title=""
                      id="preview-featured-item"
                    />
                  </div>
                </div>
                <div class="sub-4"><!-- spacer --></div>
              </div>
              <div class="row">
                <div class="col-12" id="featured-desc">
	                <div class="row">
	                  <div class="sub-6 text-right">Rarity:</div>
	                  <div class="sub-6" id="featured-rarity"></div>
	                </div>
	                <div class="row">
	                  <div class="sub-6 text-right">Mass:</div>
	                  <div class="sub-6" id="featured-mass"></div>
	                </div>
	                <div class="row">
	                  <div class="sub-6 text-right">Category:</div>
	                  <div class="sub-6" id="featured-category"></div>
	                </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div id="modal-container">
      <img src="${pageContext.request.contextPath}/resources/img/exit.png" alt="close modal" id="modal-exit" />
      <div class="modal" id="modal-login">
        <div class="col-3"><!-- Spacer --></div>
        <div class="col-6">
          <div class="card">
            <div class="card-top">Login</div>
            <div class="card-content">
              <form action="${pageContext.request.contextPath}/user/login" method="POST" class="text-center">
                <div class="input-group">
                  <input
                    type="text"
                    name="username"
                    placeholder="Username"
                    class="modal-form"
                  />
                  <label for="username">User:</label>
                </div>
                <div class="input-group">
                  <input
                    type="password"
                    name="password"
                    placeholder="password"
                    class="modal-form"
                  />
                  <label for="password">Pass:</label>
                </div>
                <div class="input-group">
                  <input type="submit" value="Login" class="modal-form" />
                </div>
              </form>
            </div>
          </div>
        </div>
        <div class="col-3"><!-- Spacer --></div>
      </div>
      <div class="modal" id="modal-register">
        <div class="col-3"><!-- Spacer --></div>
        <div class="col-6">
          <div class="card">
            <div class="card-top">Register</div>
            <div class="card-content card-form">
              <form action="${pageContext.request.contextPath}/user/create" method="POST" class="text-center">
                <div class="input-group">
                  <input
                    type="text"
                    name="username"
                    placeholder="Username"
                    class="modal-form"
                  />
                  <label for="username">User: </label>
                </div>
                <div class="input-group">
                  <input
                    type="password"
                    name="password"
                    placeholder="password"
                    class="modal-form"
                  />
                  <label for="password">Pass:</label>
                </div>
                <div class="input-group">
                  <input
                    type="password"
                    name="passwordRpt"
                    placeholder="repeat password"
                    class="modal-form"
                  />
                </div>
                <div class="input-group">
                  <input
                    type="email"
                    name="email"
                    placeholder="sample@gmail.com"
                    class="modal-form"
                  />
                  <label for="password">Email:</label>
                </div>
                <div class="input-group">
                  <input type="submit" value="Register" class="modal-form" />
                </div>
              </form>
            </div>
          </div>
        </div>
        <div class="col-3"><!-- Spacer --></div>
      </div>
      <div class="modal" id="modal-featured">
      	<div class="col-3"><!-- Spacer --></div>
      	<div class="col-6">
      		<div class="card">
      			<div class="card-top subtitle" id="modal-featured-title">Water Ration</div>
      			<div class="card-content text-center">
      				<img src="" id="modal-featured-item">
      				<p id="modal-featured-desc">Description for the item should go here</p>
      			</div>
      		</div>
      	</div>
      	<div class="col-3"><!-- Spacer --></div>
      </div>
    </div>
  </body>
</html>
