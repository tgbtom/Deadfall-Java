<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/overview.css" />
<title>In Town</title>

<t:generic>
	<jsp:attribute name="mainContent">
		<t:intown></t:intown>
		
	<!-- Main Content goes here -->
	<div class="subtitle">Town Overview</div>
		<t:header></t:header>
	
		<div class="row">
			<div class="card">
				<div class="card-top">Town Bulletin - <c:out value="${sessionScope.character.town.name}" /></div>
			<div class="card-content">
				<c:set var="bulletins" scope="page" value="${sessionScope.character.town.getOrderedBulletins()}" />
				<c:forEach items="${bulletins}" var="item">
					<div class="row">
						<div class="sub-3 br"><fmt:formatDate pattern="MMM dd, yyyy" value = "${item.postedTime}" /> | <strong><fmt:formatDate pattern="hh:mm a" value = "${item.postedTime}" /></strong></div>
						<div class="sub-9"><c:out value="${item.content}" escapeXml="false" /></div>
					</div>
				</c:forEach>
			</div>
			</div>
		</div>

	</jsp:attribute>
</t:generic>
