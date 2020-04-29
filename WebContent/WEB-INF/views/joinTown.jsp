<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Join Town</title>
    <link
      href="https://fonts.googleapis.com/css?family=Khand"
      rel="stylesheet"
    />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/main.css" />

    <script src="${pageContext.request.contextPath}/resources/js/main.js"></script>  
    <script src="${pageContext.request.contextPath}/resources/js/joinTown.js"></script>   
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
	  <a href="../logout">
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
      <div class="row">
        <div class="col-4">
          <div class="card">
            <div class="card-top">Join With...</div>
            <div class="card-content">
            
              <%@ page import="com.novaclangaming.model.Character" %>
              <%@ page import="com.novaclangaming.model.User" %>
              <%@ page import="com.novaclangaming.dao.JPACharacterDao" %>
              <%@ page import="java.util.ArrayList" %>
              <%@ page import="java.util.List" %>
              <%
              User user = (User) request.getSession().getAttribute("user");
              Character character = (Character) request.getSession().getAttribute("character");
              int charId = character.getCharId();
              JPACharacterDao charDao = new JPACharacterDao();
              List<Character> characters = charDao.findByUserId(user.getId());
              
			  List<Integer> charIdIn = new ArrayList<Integer>();
			  List<Integer> charIdOut = new ArrayList<Integer>();
			  
			  charIdIn.add(charId);
			  request.getSession().setAttribute("character-ids", charIdIn);

			  for (Character c : characters){
				  if(! charIdIn.contains(c.getCharId()) && c.getTown() == null){
					  charIdOut.add(c.getCharId());
				  } 
			  }
              %>
            
              <div class="row bb">
                <div class="col-8">
                <span id="charOptions">
                  <select id="charOption">
	                  <%
	                  for(int curId : charIdOut){
	                	  Character c = charDao.findById(curId);
	                	  %>
	                	  <option value="<%= c.getCharId() %>"><%= c.getName() %></option>
	                	  <% }%>
                  </select></span>
                </div>
                
            <%  if(charIdOut.size() == 0){ %>
                	<div class="col-4"><button class="btn-play" id="addChar" disabled>Add</button></div>
                	
            <%  }
                else{ %>
                	<div class="col-4"><button class="btn-play" id="addChar">Add</button></div>      	
            <%  } %>
            
              </div>
              
              <span id="charsSelected">
              <%
              for(int curId : charIdIn){
            	  Character c = charDao.findById(curId);
            	  %>
            	  	<div class="row">
	                	<div class="sub-7 text-bold"><%= c.getName() %> []</div>
	                	<div class="sub-4"><%= c.getClassification() %></div>
	                	<div class="sub-1"><img src="${pageContext.request.contextPath}/resources/
	                	img/small-x-red.png" alt="remove" onclick="unselectChar(<%=c.getCharId()%>)" class="msg-close"></div>
                	</div>
            	  <%
              }
              %>
              </span>
             
            </div>
          </div>
        </div>
        <div class="col-8">
          <div class="card">
            <div class="card-top">Towns</div>
            <div class="card-content">
            
            <%@ page import="com.novaclangaming.model.Town" %>
            <%@ page import="com.novaclangaming.dao.JPATownDao" %>
            <span id="availableTowns">
	            
	        <%  JPATownDao townDao = new JPATownDao();
	            List<Town> towns = townDao.findAllOpenTowns();
	            for (Town t: towns){
	            	if(t.getTownSize() - t.getCharacters().size() >= charIdIn.size()){ %>
	            	<div class="row bb">
		                <div class="sub-5 text-bold"><%= t.getName() %></div>
		                <div class="sub-5">[ <%= t.getCharacters().size() %> / <%= t.getTownSize() %>]</div>
		                <div class="sub-2">
		                  <button class="btn-play join-town" id="<%= t.getTownId() %>">Join</button>
		                </div>
	              	</div>
	            	<%
	            	}
	            }
	            %>
            </span>
            </div>
          </div>
          <div class="row mt-6">
      		<div class="card">
      			<div class="card-top"> Create new Town </div>
      			<div class="card-content">
      			<form action="../town/create" method="POST">
      				<div class="input-group">
      					<label for="townName">Town Name: </label>
      					<input type="text" name="townName" class="char-form" required>
      				</div>
      				<div class="input-group">
      					<label for="townSize">Population: </label>
      					<select name="townSize" class="char-form">
      						<option value="10">Band of Survivors (10)</option>
      					</select>
      				</div>
      				<div class="input-group">
      					<label for="mapSize">Map Size: </label>
      					<select name="mapSize" class="char-form">
      						<option value="11">Regular (11 x 11)</option>
      					</select>
      				</div>
      				<div class="input-group">
      					<label for="townMode">Game Mode: </label>
      					<select name="townMode" class="char-form">
      						<option value="Regular">Regular</option>
      					</select>
      				</div>
      				<div class="input-group">
      					<input type="submit" value="Create Town" class="char-form">
      				</div>
      			</form>
      			</div>
      		</div>
      	  </div>
        </div>
      </div>
    </div>

    <div id="modal-container">
      <img src="${pageContext.request.contextPath}/resources/img/exit.png" alt="close modal" id="modal-exit" />
    </div>
  </body>
</html>