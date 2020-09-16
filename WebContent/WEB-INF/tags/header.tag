<%@tag description="Header for in-town. Displays info" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri = "http://java.sun.com/jsp/jstl/core" %>

	<div class="row">
		<div class="col-6"> 
			<div class="sub-5 head-box text-center"><strong>User:</strong> <c:out value="${sessionScope.user.username}" /></div>
			<div class="sub-7 head-box text-center"><c:out value="${sessionScope.character.town.name}" /></div>
		</div>
		
		<div class="col-6">
			<div class="sub-9 head-box text-center">
				<img src="${pageContext.request.contextPath}/resources/img/icons/leftArrow.png" class="header-arrow" title="Previous Character" onclick="changeCharacter(-1, 'Previous')">
				<span><c:out value="${sessionScope.character.name}" /></span>
				<img src="${pageContext.request.contextPath}/resources/img/icons/rightArrow.png" class="header-arrow" title="Next Character" onclick="changeCharacter(-1, 'Next')">
			</div>
			
			<div class="sub-3 head-box text-center"><c:out value="${sessionScope.character.zone.x}" />, <c:out value="${sessionScope.character.zone.y}" /> </div>
		</div>
		<div class="col-6">
			<div class="sub-6 head-box text-center"> <c:out value="${sessionScope.character.town.hordeSize}" /> <img src="${pageContext.request.contextPath}/resources/img/icons/Sword.png"> | <img src="${pageContext.request.contextPath}/resources/img/icons/Shield.png"> <c:out value="${sessionScope.character.town.defence}" /></div>
			<div class="sub-6 head-box text-center">
				
 				<c:forEach items="${sessionScope.character.status}" var="charStatus">
					<img src="${pageContext.request.contextPath}/resources/img/status/<c:out value="${charStatus.status.getName()}" />.png" title="<c:out value="${charStatus.status.getName()}" />"/>
				</c:forEach>
				<c:if test="${ sessionScope.character.status.size() < 1}">
					<div class="card-content">No Status</div>
				</c:if>
				
				<br> 
				
				<c:choose>
					<c:when test='${ sessionScope.character.hasStatusByName("Not Done") }'>
						<a href="../character/endday"><button class="btn btn-play">End Day []</button></a>
					</c:when>
					<c:otherwise>
						<button class="btn btn-delete">End Day</button>
					</c:otherwise>
				</c:choose>
				
			</div>
		</div>
		<div class="col-6">
			<div class="sub-8 head-box text-center" id="current-char-items">
			<!-- Items -->
				<c:forEach items="${ sessionScope.character.itemStacks }"  var="stack">
					<div class="character-item-group" id="character-item-${ stack.item.itemId}" title="<c:out value="${ stack.item.name }"/>">
						<img src="${pageContext.request.contextPath}/resources/img/items/rarity/<c:out value="${stack.item.rarity}" />.png" class="item-rarity" />
						<img src="${pageContext.request.contextPath}/resources/img/items/<c:out value="${stack.item.name}"/>.png" alt="<c:out value="${ stack.item.name }"/>" class="character-item" />					
						<span class="character-item-counter" id="character-stack-${ stack.item.itemId}"><c:out value="${ stack.quantity }"/></span>
					</div>
				</c:forEach>
				<c:if test="${ sessionScope.character.itemStacks.size() < 1}">
					<div class="card-content">No Items</div>
				</c:if>
				<c:if test="${ sessionScope.character.itemStacks.size() > 0}">
					<c:choose>
					
					<c:when test="${ 20 - sessionScope.character.getCapacity() >= 18 }">
					<span class="text-red">
					</c:when>
					
					<c:when test="${ 20 - sessionScope.character.getCapacity() >= 14 }">
					<span class="text-yellow">
					</c:when>
					
					<c:otherwise>
					<span class="text-green">
					</c:otherwise>
					
					</c:choose>
					<c:out value="${20 - sessionScope.character.getCapacity()}" />/20</span>
				</c:if>
				
			</div>
			<div class="sub-4 head-box text-center"><c:out value="${sessionScope.character.currentAp}" />/<c:out value="${sessionScope.character.maxAp}" /> ap</div>
		</div>
	</div>