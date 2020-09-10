<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<!-- FOR JSTL tag Library -->
<%@ taglib prefix="c" uri = "http://java.sun.com/jsp/jstl/core" %>

<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/overview.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/outside.css" />

<script src="${pageContext.request.contextPath}/resources/js/storage.js"></script>

<title>Outside</title>
<!-- copy this file into a new JSP file to include universal header with container -->

<t:generic>
	<jsp:attribute name="mainContent">
	
	<t:intown></t:intown>
	
		<div class="subtitle">Outside</div>
		<t:header></t:header>
	
		<div class="col-4">
		
			<div class="row">
				<div class="sub-2"><!-- SPACER --></div>
				<div class="sub-8 text-center"><a href="outside/nav/up"><img src="${pageContext.request.contextPath}/resources/img/icons/upArrow.png" class="nav-arrow" id="nav-up"/></a></div>
				<div class="sub-2"><!-- SPACER --></div>
			</div>
			<div class="row">
				<div class="sub-12 text-center" style="min-width:230px;">
					<a href="outside/nav/left"><img src="${pageContext.request.contextPath}/resources/img/icons/leftArrow.png" class="nav-arrow" id="nav-left" style="margin-bottom:64px;"/></a>
					<svg width="165" height="165" >
						<c:forEach var="i" begin="0" end="120">
							<c:choose>
								<c:when test="${i * 15 >= 165}">
									<c:set var="left" value="${ i * 15 - Math.floor(i * 15 / 165) * 165 }" />
								</c:when>
								<c:otherwise>
									<c:set var="left" value="${i * 15}" />
								</c:otherwise>
							</c:choose>
							<c:set var="x" value="${left / 15 - 5}" />
							<c:set var="top" value="${ Math.floor(i/11) * 15 }" />
							<c:set var="y" value="${5 - top / 15}" />
							
							<c:set var="zone" value="${sessionScope.townDao.findZoneByCoords(sessionScope.character.town.townId , x, y)}"/>
							<c:choose>
								<c:when test="${ zone.x == 0 && zone.y == 0 }">
									<rect x="${left}" y="${top}" height="15" width="15" class="grid-brown">
								</c:when>
								<c:when test="${zone.zeds == 0}">
									<rect x="${left}" y="${top}" height="15" width="15" class="grid-light-green">
								</c:when>
								<c:when test="${zone.zeds == 1}">
									<rect x="${left}" y="${top}" height="15" width="15" class="grid-green">
								</c:when>
								<c:when test="${zone.zeds == 2 || zone.zeds == 3}">
									<rect x="${left}" y="${top}" height="15" width="15" class="grid-dark-green">
								</c:when>
								<c:when test="${zone.zeds >= 4 && zone.zeds <= 6}">
									<rect x="${left}" y="${top}" height="15" width="15" class="grid-yellow">
								</c:when>
								<c:when test="${zone.zeds >= 7 && zone.zeds <= 12}">
									<rect x="${left}" y="${top}" height="15" width="15" class="grid-orange">
								</c:when>
								<c:when test="${zone.zeds >= 13 && zone.zeds <= 25}">
									<rect x="${left}" y="${top}" height="15" width="15" class="grid-red">
								</c:when>
								<c:when test="${zone.zeds >= 26 && zone.zeds <= 40}">
									<rect x="${left}" y="${top}" height="15" width="15" class="grid-purple">
								</c:when>
								<c:otherwise>
									<rect x="${left}" y="${top}" height="15" width="15" class="grid-black">
								</c:otherwise>
							</c:choose>
									<title><c:out value="${ zone.getCharacterNames() }"/></title>
								</rect>
								
							<c:if test="${ zone.characters.size() > 0 }">
								<c:choose>
									<c:when test="${ sessionScope.character.zone.zoneId ==  zone.zoneId}">
										<rect x="${ left + 6 }" y="${ top + 6 }" width="4" height="4" class="rect-white">
											<title><c:out value="${ zone.getCharacterNames() }"/></title>
										</rect>
									</c:when>
									<c:otherwise>
										<circle cx="${ left + 8 }" cy="${ top + 8 }" r="2" class="circle-blue">
											<title><c:out value="${ zone.getCharacterNames() }"/></title>
										</circle>
									</c:otherwise>
								</c:choose>
								
							</c:if>
							
						
						</c:forEach>
					</svg>
				<a href="outside/nav/right"><img src="${pageContext.request.contextPath}/resources/img/icons/rightArrow.png" class="nav-arrow" id="nav-right" style="margin-bottom:64px;"/></a>
				</div>
			</div>
			<div class="row">
				<div class="sub-2"><!-- SPACER --></div>
				<div class="sub-8 text-center"><a href="outside/nav/down"><img src="${pageContext.request.contextPath}/resources/img/icons/downArrow.png" class="nav-arrow" id="nav-down"/></a></div>
				<div class="sub-2"><!-- SPACER --></div>			
			</div>
		
		</div>
		<div class="col-4">
			<div class="card">
				<div class="card-top">
					<c:set var="myZone" value="${sessionScope.character.zone}"/>
				
					Regular Zone <br />
					<c:out value="${myZone.x}"/>, <c:out value="${myZone.y}"/> | 
					<img src="${pageContext.request.contextPath}/resources/img/icons/lootability.png" /> <c:out value="${myZone.lootability}"/> | 
					<img src="${pageContext.request.contextPath}/resources/img/icons/zombie.png"/> <c:out value="${myZone.zeds}"/> | 
					<img src="${pageContext.request.contextPath}/resources/img/icons/blocked.png" alt="danger"/> <c:out value="${myZone.danger}"/>
				</div>
				<div class="card-body">
				<c:set var="itemStacks" value="${myZone.itemStacks}"/>
				<c:forEach items="${itemStacks}" var="stack">
					<!-- each stack from the zone floor goes here -->
						<div class="storage-item-group" id="storage-${stack.item.itemId}">
							<img src="${pageContext.request.contextPath}/resources/img/items/rarity/<c:out value="${stack.item.rarity}" />.png" class="item-rarity" />
							<img src="${pageContext.request.contextPath}/resources/img/items/<c:out value="${ stack.item.name }"/>.png" id="storage-${ stack.item.itemId}" title="<c:out value="${ stack.item.name }"/>" alt="<c:out value="${ stack.item.name }"/>" class="storage-item" />
							<span class="item-counter" id="stack-${ stack.item.itemId}"><c:out value="${ stack.quantity }"/></span>
						</div>
				</c:forEach>
				</div>
			</div>
		
		</div>
		<div class="col-4">
		
			<div class="card">
				<div class="card-top">Bulletin - Coming Soon</div>
			</div>
		
		</div>

	
	</jsp:attribute>
</t:generic>