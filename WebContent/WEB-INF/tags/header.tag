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
			<div class="sub-8 head-box text-center" id="current-char-items">
			<!-- Items -->
				<c:forEach items="${ sessionScope.character.itemStacks }"  var="stack">
					<div class="item-group">
						<img src="${pageContext.request.contextPath}/resources/img/items/<c:out value="${ stack.item.name }"/>.png" id="character-item-${ stack.item.itemId}" title="<c:out value="${ stack.item.name }"/>" alt="<c:out value="${ stack.item.name }"/>" class="character-item" />
						<span class="character-item-counter" id="character-stack-${ stack.item.itemId}"><c:out value="${ stack.quantity }"/></span>
					</div>
				</c:forEach>
				<c:if test="${ sessionScope.character.itemStacks.size() < 1}">
					<div class="card-content">No Items</div>
				</c:if>
				<c:if test="${ sessionScope.character.itemStacks.size() > 0}">
					XX/16
				</c:if>
				
			</div>
			<div class="sub-4 head-box text-center">16/16 ap</div>
		</div>
	</div>