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
			
				<c:forEach items="${sessionScope.character.town.characters}" var="c">
					<div class="row">
						<div class="sub-3"><c:out value="${c.name}"/></div>
						<div class="sub-3"><img src="${pageContext.request.contextPath}/resources/img/icons/${c.classification}.png"> <c:out value="${c.classification}"/></div>
						<div class="sub-3">Items</div>
						<div class="sub-3">Status</div>
					</div>
				</c:forEach>
			</div>
		</div>
	</jsp:attribute>
</t:generic>