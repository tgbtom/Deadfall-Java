<%@tag description="Generic Authenticated Page" pageEncoding="UTF-8" %>
<%@attribute name="mainContent" fragment="true" %>
<html>
<head>

    <meta name="viewport" content="width=device-width, initial-scale=1.0" />

    <link
      href="https://fonts.googleapis.com/css?family=Khand"
      rel="stylesheet"
    />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/main.css" />

    <script src="${pageContext.request.contextPath}/resources/js/main.js"></script>

</head>
<body>

      <% if (request.getSession().getAttribute("message") != null){ %>
    <div class="page-message" id="page-message">
      <%= request.getSession().getAttribute("message") %>
      <img src="${pageContext.request.contextPath}/resources/img/small-x.png" id="msg-close" alt="close message" />
    </div>
    <%} request.getSession().removeAttribute("message"); %>

	<nav class="nav-bar" id="nav-desktop">
	  <a href="${pageContext.request.contextPath}/dashboard">
	  	<button class="btn-nav left" id="login-btn">Dashboard</button>
	  </a>
	  <a href="${pageContext.request.contextPath}/logout">
	  	<button class="btn-nav right" id="login-btn">Logout</button>
	  </a>
	  <a href="${pageContext.request.contextPath}/user/profile">
	  	<button class="btn-nav right" id="login-btn">Profile</button>
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
    	<jsp:invoke fragment="mainContent"></jsp:invoke>
    </div>
    
</body>
</html>