<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<!-- FOR JSTL tag Library -->
<%@ taglib prefix="c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/overview.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/outside.css" />

<script src="${pageContext.request.contextPath}/resources/js/storage.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/outside.js"></script>

<title>Outside</title>
<!-- copy this file into a new JSP file to include universal header with container -->

<t:generic>
	<jsp:attribute name="mainContent">
	
	<t:intown></t:intown>
	
		<div class="subtitle">Outside</div>
		<t:header></t:header>
		<div class="row">
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
							<c:forEach items="${ sessionScope.character.town.zones }" var="zone">
								<c:set var="left" value="${ (15 * zone.x) + 75 }" />
								<c:set var="top" value="${ (-15 * zone.y) + 75 }" />
								
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
			<div class="col-7">
				<div class="card">
					<div class="card-top">
						<c:set var="myZone" value="${sessionScope.character.zone}"/>
					
						Regular Zone <br />
						<c:out value="${myZone.x}"/>, <c:out value="${myZone.y}"/> | 
						<img src="${pageContext.request.contextPath}/resources/img/icons/lootability.png" /> <span id="zoneLootability"><c:out value="${myZone.lootability}"/></span> | 
						<img src="${pageContext.request.contextPath}/resources/img/icons/zombie.png"/> <c:out value="${myZone.zeds}"/> | 
						<img src="${pageContext.request.contextPath}/resources/img/icons/blocked.png" alt="danger"/> <span id="zoneDanger"><c:out value="${ Integer.max(myZone.danger, 0)}"/></span>
					</div>
					<div class="card-body" style="display: inline-block">
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
					<hr>
					<div class="card-body text-center">
						<c:choose>
							<c:when test="${ myZone.lootability > 0 }">
								<button class="btn btn-join" onclick="loot()">Search Zone (<c:out value="${myZone.lootability}"/>)</button>
							</c:when>
							<c:otherwise>
								<button class="btn btn-delete" onclick="loot()">Search Zone (<c:out value="${myZone.lootability}"/>)</button>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
			
			</div>
		</div>
		<div class="row">
			<div class="col-1"></div>
			<div class="col-10">
			
				<div class="card">
					<div class="card-top">Zone History [<c:out value="${ myZone.x }, ${ myZone.y }"/>]</div>
					<div class="card-content zone-bulletins">
						<c:forEach items="${ myZone.getOrderedBulletins() }" var="bul">
							<div class="row bb">
								<div class="sub-3 br"><fmt:formatDate pattern="MMM dd" value = "${bul.postedTime}" /> | <strong><fmt:formatDate pattern="hh:mm a" value = "${bul.postedTime}" /></strong></div>
								<div class="sub-9"><c:out value="${ bul.content }" escapeXml="false"/></div>
							</div>
						</c:forEach>
					</div>
				</div>
			
			</div>
			<div class="col-1"></div>
		</div>

	
	</jsp:attribute>
</t:generic>