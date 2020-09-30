<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<!-- FOR JSTL tag Library -->
<%@ taglib prefix="c" uri = "http://java.sun.com/jsp/jstl/core" %>

<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/overview.css" />
<title>Citizens</title>
<!-- copy this file into a new JSP file to include universal header with container -->

<t:generic>
	<jsp:attribute name="mainContent">
	
	<t:intown></t:intown>
	
		<div class="subtitle">Citizens</div>
		<t:header></t:header>
	
		<div class="card">
			<div class="card-top">
				<div class="row text-left">
					<div class="sub-3">Name</div>
					<div class="sub-3">Class</div>
					<div class="sub-3">Items</div>
					<div class="sub-3">Status</div>
				</div>
			</div>
			<div class="card-content">
			
				<c:set var="deadStatus" scope="session" value="${ sessionScope.charDao.findStatusByName('Dead') }"/>
			
				<c:forEach items="${sessionScope.character.town.characters}" var="c">
					<c:choose>
						<c:when test="${ sessionScope.charDao.findCharacterStatus(c, deadStatus) != null }">
							<div class="row deadCharacter">
						</c:when>
						<c:when test="${ c.user.id == sessionScope.user.id && c.charId == sessionScope.character.charId}">
							<div class="row activeCharacter">
						</c:when>
						<c:when test="${ c.user.id == sessionScope.user.id}">
							<div class="row myCharacter" onclick="changeCharacter(<c:out value="${ c.charId }"/>, 'none')">
						</c:when>
						<c:otherwise>
							<div class="row">
						</c:otherwise>
					</c:choose>

						<div class="sub-3"><c:out value="${c.name} [${c.zone.x},${c.zone.y}]"/></div>
						<div class="sub-3"><img src="${pageContext.request.contextPath}/resources/img/icons/${c.classification}.png"> <c:out value="${c.classification}"/></div>
						<div class="sub-3">
							<!-- Items here -->
							<c:forEach items="${c.itemStacks}" var="stack">
								<div class="citizens-item-group" title="<c:out value="${ stack.item.name }"/>">
									<img src="${pageContext.request.contextPath}/resources/img/items/rarity/<c:out value="${stack.item.rarity}" />.png" class="item-rarity" />
									<img src="${pageContext.request.contextPath}/resources/img/items/<c:out value="${stack.item.name}"/>.png" alt="<c:out value="${ stack.item.name }"/>" class="citizens-item" />					
									<span class="citizens-item-counter"><c:out value="${ stack.quantity }"/></span>
								</div>
							</c:forEach>
						</div>
						<div class="sub-3">
							<c:forEach items="${c.status}" var="charStatus">
								<div class="citizens-item-group" title="<c:out value="${ stack.item.name }"/>">
									<img src="${pageContext.request.contextPath}/resources/img/status/<c:out value="${charStatus.status.getName()}" />.png" title="<c:out value="${charStatus.status.getName()}" />"/>
								</div>
							</c:forEach>
						</div>
					</div>
				</c:forEach>
			</div>
		</div>
	</jsp:attribute>
</t:generic>