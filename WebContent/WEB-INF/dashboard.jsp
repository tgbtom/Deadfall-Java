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
    <script src="js/dashboard.js"></script>
  </head>
  <body>
      <% if (request.getSession().getAttribute("message") != null){ %>
    <div class="page-message" id="page-message">
      <%= request.getSession().getAttribute("message") %>
      <img src="img/small-x.png" id="msg-close" alt="close message" />
    </div>
    <% request.getSession().removeAttribute("message");} %>
    
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
        <div class="col-8">
          <div class="card dash-char">
            <div class="card-top">Characters</div>
            <div class="card-content">
            
            <%@ page import="com.novaclangaming.model.Character" %>
            <%@ page import="java.util.List" %>
            <%
            List<Character> characters = (List<Character>) request.getSession().getAttribute("characters");
            request.getSession().removeAttribute("characters");
            
            for(Character c : characters){
            	%>
				<div class="row bb">
				<form action="PlayCharacter" method="POST">
					<input type="hidden" name="char-id" value="<%= c.getCharId() %>">
					<div class="sub-5 text-bold"><%= c.getName() %> [0]</div>
					<div class="sub-5"><%= c.getClassification() %></div>
					<div class="sub-2">
						<input type="submit" value="Play" class="btn-play">
					</div>
				</form>
				</div>
            	<% } %>
              
            </div>
          </div>
          <div class="row mt-6">
            <div class="card dash-char">
              <div class="card-top">Create Character</div>
              <div class="card-content card-form">
                <form action="Create?type=character" method="POST">
                  <div class="input-group">
                    <label for="char-name">Name: </label>
                    <input
                      type="text"
                      name="char-name"
                      placeholder="Character Name"
                      class="char-form"
                      required
                    />
                  </div>
                  <div class="input-group">
                    <label for="char-class">Class: </label>
                    <Select name="char-class" class="char-form" id="char-class">
                      <option value="Survivor" selected>Survivor</option>
                      <option value="Builder">Builder</option>
                      <option value="Looter">Looter</option>
                      <option value="Runner">Runner</option>
                    </Select>
                  </div>
                  <div class="card inner-card">
                    <div class="card-content">
                      <span id="class-desc">
                        The survivor class is well rounded, having a maximum AP
                        of 16 rather than 12. The survivor does not specialize
                        in anything.
                      </span>
                    </div>
                  </div>
                  <div class="input-group">
                    <input type="submit" value="Create" class="char-form" />
                  </div>
                </form>
              </div>
            </div>
          </div>
        </div>
        <div class="col-4">
          <div class="card dash-bulletin">
            <div class="card-top">User Bulletin</div>
            <div class="card-content">
              <div class="row bb">
                <div class="sub-3">09/13 2019</div>
                <div class="sub-9 bl">
                  This is a test bulletin and it is medium in length
                </div>
              </div>
              <div class="row bb">
                <div class="sub-3">09/10 2019</div>
                <div class="sub-9 bl">
                  Short
                </div>
              </div>
              <div class="row bb">
                <div class="sub-3">09/05 2019</div>
                <div class="sub-9 bl">
                  This is a test bulletin and it is LONG in length. Alot to
                  think about here regarding <yellow>yellow</yellow>
                </div>
              </div>
              <div class="row bb">
                <div class="sub-3">09/13 2019</div>
                <div class="sub-9 bl">
                  This is a test bulletin and it is medium in length
                </div>
              </div>
              <div class="row bb">
                <div class="sub-3">09/10 2019</div>
                <div class="sub-9 bl">
                  Short
                </div>
              </div>
              <div class="row bb">
                <div class="sub-3">09/05 2019</div>
                <div class="sub-9 bl">
                  This is a test bulletin and it is LONG in length. Alot to
                  think about here regarding <yellow>yellow</yellow>
                </div>
              </div>
              <div class="row bb">
                <div class="sub-3">09/13 2019</div>
                <div class="sub-9 bl">
                  This is a test bulletin and it is medium in length
                </div>
              </div>
              <div class="row bb">
                <div class="sub-3">09/10 2019</div>
                <div class="sub-9 bl">
                  Short
                </div>
              </div>
              <div class="row bb">
                <div class="sub-3">09/05 2019</div>
                <div class="sub-9 bl">
                  This is a test bulletin and it is LONG in length. Alot to
                  think about here regarding <yellow>yellow</yellow>
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
