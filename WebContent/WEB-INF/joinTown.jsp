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
    <link rel="stylesheet" href="css/main.css" />

    <script src="js/main.js"></script>
    <script src="js/home.js"></script>
  </head>
  <body>
    <nav class="nav-bar" id="nav-desktop">
	  <a href="Navigate?loc=dashboard">
	  	<button class="btn-nav left" id="login-btn">Dashboard</button>
	  </a>
	  <a href="Navigate?loc=logout">
	  	<button class="btn-nav right" id="login-btn">Logout</button>
	  </a>
    </nav>

    <nav class="nav-bar" id="nav-mobile">
      <button class="btn-nav left dropdown-btn">
        <img src="img/hamburger-menu.png" alt="Menu" />
        <div class="dropdown-content">
          <div class="dropdown-link">Homepage</div>
          <div class="dropdown-link">Logout</div>
        </div>
      </button>
    </nav>

    <header id="banner">
      <img src="img/MainBanner.png" id="banner-img" alt="Deadfall Banner" />
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
              int charId = Integer.parseInt(request.getParameter("char-id"));
              User user = (User) request.getSession().getAttribute("user");
              JPACharacterDao charDao = new JPACharacterDao();
              List<Character> characters = charDao.findByUserId(user.getId());
			  List<Integer> charIdIn = new ArrayList<Integer>();
			  List<Integer> charIdOut = new ArrayList<Integer>();
			  charIdIn.add(charId);
			  for (Character c : characters){
				  if(! charIdIn.contains(c.getCharId())){
					  charIdOut.add(c.getCharId());
				  } 
			  }
              %>
            
              <div class="row bb">
                <div class="col-8">
                  <select>
                  <%
                  for(int curId : charIdOut){
                	  Character c = charDao.findById(curId);
                	  %>
                	  <option value="<%= c.getName() %>"><%= c.getName() %></option>
                	  <%
                  }
                  %>
                  </select>
                </div>
                <div class="col-4"><button class="btn-play">Add</button></div>
              </div>
              
              <%
              for(int curId : charIdIn){
            	  Character c = charDao.findById(curId);
            	  %>
            	  	<div class="row">
	                	<div class="sub-7 text-bold"><%= c.getName() %> []</div>
	                	<div class="sub-5"><%= c.getClassification() %></div>
                	</div>
            	  <%
              }
              %>
             
            </div>
          </div>
        </div>
        <div class="col-8">
          <div class="card">
            <div class="card-top">Towns</div>
            <div class="card-content">
              <div class="row bb">
                <div class="sub-5 text-bold">Town of Winterfell</div>
                <div class="sub-5">[5/10]</div>
                <div class="sub-2">
                  <button class="btn-play">Join</button>
                </div>
              </div>
              <div class="row bb">
                <div class="sub-5 text-bold">King's Landing</div>
                <div class="sub-5">[0/10]</div>
                <div class="sub-2">
                  <button class="btn-play">Join</button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div id="modal-container">
      <img src="img/exit.png" alt="close modal" id="modal-exit" />
    </div>
  </body>
</html>