<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<!-- FOR JSTL tag Library -->
<%@ taglib prefix="c" uri = "http://java.sun.com/jsp/jstl/core" %>

<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/overview.css" />

<script src="${pageContext.request.contextPath}/resources/js/storage.js"></script>
<title>Citizens</title>
<!-- copy this file into a new JSP file to include universal header with container -->

<t:generic>
	<jsp:attribute name="mainContent">
	
	<t:intown></t:intown>
	
		<div class="subtitle">Storage</div>
		<t:header></t:header>
		
		<div class="row">
			<div class="card col-6">
				<div class="card-top">Resources</div>
					<c:forEach items="${ sessionScope.resources }"  var="stack">
						<img src="${pageContext.request.contextPath}/resources/img/items/<c:out value="${ stack.item.name }"/>.png" id="storage-${ stack.item.itemId}" title="<c:out value="${ stack.item.name }"/>" alt="<c:out value="${ stack.item.name }"/>" class="storage-item" />
						<span class="item-counter" id="stack-${ stack.item.itemId}"><c:out value="${ stack.quantity }"/></span>
					</c:forEach>
					<c:if test="${ sessionScope.resources.size() < 1}">
						<div class="card-content">Nothing was found</div>
					</c:if>
			</div>
			<div class="card col-6">
				<div class="card-top">Consumables</div>
				<div class="card-content">
					<c:forEach items="${ sessionScope.consumables }"  var="stack">
						<img src="${pageContext.request.contextPath}/resources/img/items/<c:out value="${ stack.item.name }"/>.png" id="storage-${ stack.item.itemId}" title="<c:out value="${ stack.item.name }"/>" alt="<c:out value="${ stack.item.name }"/>" class="storage-item" />
						<span class="item-counter" id="stack-${ stack.item.itemId}"><c:out value="${ stack.quantity }"/></span>
					</c:forEach>
					<c:if test="${ sessionScope.consumables.size() < 1}">
						<div class="card-content">Nothing was found</div>
					</c:if>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="card col-6">
				<div class="card-top">Weapons</div>
				<div class="card-content">
					<c:forEach items="${ sessionScope.weapons }"  var="stack">
						<img src="${pageContext.request.contextPath}/resources/img/items/<c:out value="${ stack.item.name }"/>.png" id="storage-${ stack.item.itemId}" title="<c:out value="${ stack.item.name }"/>" alt="<c:out value="${ stack.item.name }"/>" class="storage-item" />
						<span class="item-counter" id="stack-${ stack.item.itemId}"><c:out value="${ stack.quantity }"/></span>
					</c:forEach>
					<c:if test="${ sessionScope.weapons.size() < 1}">
						<div class="card-content">Nothing was found</div>
					</c:if>
				</div>
			</div>
			<div class="card col-6">
				<div class="card-top">Ammo</div>
				<div class="card-content">
					<c:forEach items="${ sessionScope.ammo }"  var="stack">
						<img src="${pageContext.request.contextPath}/resources/img/items/<c:out value="${ stack.item.name }"/>.png" id="storage-${ stack.item.itemId}" title="<c:out value="${ stack.item.name }"/>" alt="<c:out value="${ stack.item.name }"/>" class="storage-item" />
						<span class="item-counter" id="stack-${ stack.item.itemId}"><c:out value="${ stack.quantity }"/></span>
					</c:forEach>
					<c:if test="${ sessionScope.ammo.size() < 1}">
						<div class="card-content">Nothing was found</div>
					</c:if>
				</div>
			</div>
		</div>
	
	</jsp:attribute>
</t:generic>