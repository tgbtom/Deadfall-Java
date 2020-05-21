<%@tag description="Header for in-town. Displays info" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri = "http://java.sun.com/jsp/jstl/core" %>

	<div class="row">
		<div class="col-6"> 
			<div class="sub-5 head-box text-center"><strong>User:</strong> <c:out value="${sessionScope.user.username}" /></div>
			<div class="sub-7 head-box text-center"><c:out value="${sessionScope.character.town.name}" /></div>
		</div>
		
		<div class="col-6">
			<div class="sub-9 head-box text-center"><strong>Char: </strong><c:out value="${sessionScope.character.name}" /></div>
			<div class="sub-3 head-box text-center">0, 0 </div>
		</div>
		<div class="col-6">
			<div class="sub-6 head-box text-center"> 296 <img src="${pageContext.request.contextPath}/resources/img/icons/Sword.png"> | <img src="${pageContext.request.contextPath}/resources/img/icons/Shield.png"> 375</div>
			<div class="sub-6 head-box text-center"><strong>Status:</strong></div>
		</div>
		<div class="col-6">
			<div class="sub-8 head-box text-center"><strong>Items: </strong></div>
			<div class="sub-4 head-box text-center">16/16 ap</div>
		</div>
	</div>