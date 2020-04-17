<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Dashboard</title>
    <link
      href="https://fonts.googleapis.com/css?family=Khand"
      rel="stylesheet"
    />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/main.css" />

    <script src="${pageContext.request.contextPath}/resources/js/main.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/deleteChar.js"></script>
  </head>
  <body>
      <% if (request.getSession().getAttribute("message") != null){ %>
    <div class="page-message" id="page-message">
      <%= request.getSession().getAttribute("message") %>
      <img src="${pageContext.request.contextPath}/resources/img/small-x.png" id="msg-close" alt="close message" />
    </div>
    <%} request.getSession().removeAttribute("message"); %>
    
    <nav class="nav-bar" id="nav-desktop">
	  <a href="../dashboard">
	  	<button class="btn-nav left" id="login-btn">Dashboard</button>
	  </a>
	  <a href="${pageContext.request.contextPath}/logout">
	  	<button class="btn-nav right" id="login-btn">Logout</button>
	  </a>
    </nav>

    <nav class="nav-bar" id="nav-mobile">
      <button class="btn-nav left dropdown-btn">
        <img src="${pageContext.request.contextPath}/resources/img/hamburger-menu.png" alt="Menu" />
        <div class="dropdown-content">
          <div class="dropdown-link">Homepage</div>
          <div class="dropdown-link">Logout</div>
        </div>
      </button>
    </nav>

    <header id="banner">
      <img src="${pageContext.request.contextPath}/resources/img/MainBanner.png" id="banner-img" alt="Deadfall Banner" />
    </header>

    <div class="container">
    <h2 class="subtitle">Character Overview</h2>
      <div class="row">
        <div class="col-6">
			<div class="card dash-char">
              <div class="card-top">Lifetime Stats</div>
				<div class="row bb">
					<div class="sub-6 text-right text-bold br">Zeds Killed: </div>
					<div class="sub-6 text-center">${charToView.getTotalKills()}</div>
				</div>
				<div class="row bb">
					<div class="sub-6 text-right text-bold br">Deaths: </div>
					<div class="sub-6 text-center">${charToView.getDeaths()}</div>
				</div>
				<div class="row bb">
					<div class="sub-6 text-right text-bold br">Structure Contributions: </div>
					<div class="sub-6 text-center">${charToView.getTotalStructureCont()}</div>
				</div>
				<div class="row bb">
					<div class="sub-6 text-right text-bold br">Distance Travelled: </div>
					<div class="sub-6 text-center">${charToView.getTotalDistanceTravelled()}</div>
				</div>
				<div class="row bb">
					<div class="sub-6 text-right text-bold br">Times Looted: </div>
					<div class="sub-6 text-center">${charToView.getTotalLoots()}</div>
				</div>
				<div class="row bb">
					<div class="sub-6 text-right text-bold br">Successful Camps: </div>
					<div class="sub-6 text-center">${charToView.getTotalCamps()}</div>
				</div>
            </div>
        </div>
        <div class="col-6">
			<div class="card dash-char">
              <div class="card-top">Current Town</div>
              <%@ page import="com.novaclangaming.model.Town" %>
              <%@ page import="com.novaclangaming.model.Character" %>
              <%
              	Character c = (Character) request.getAttribute("charToView");
              
              	if(c.getTown() == null){
              		%>
              		<div class="text-center text-bold">Character is not in a town!</div>
              		<form action="delete" method="POST">
              			<input type="hidden" name="charId" value="<%=c.getCharId()%>">
						<input type="submit" class="btn-delete" id="deleteChar" value="Delete Character">
					</form>
              		<%
              	}
              	else{
              		%>
              		${charToView.getTown().getName()}
              		<%
              	}
              %>
				
            </div>
        </div>
      </div>
    </div>

    <div id="modal-container">
      <img src="${pageContext.request.contextPath}/resources/img/exit.png" alt="close modal" id="modal-exit" />
    </div>
  </body>
</html>