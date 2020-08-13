<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<!-- FOR JSTL tag Library -->
<%@ taglib prefix="c" uri = "http://java.sun.com/jsp/jstl/core" %>

<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/overview.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/outside.css" />
<title>Outside</title>
<!-- copy this file into a new JSP file to include universal header with container -->

<t:generic>
	<jsp:attribute name="mainContent">
	
	<t:intown></t:intown>
	
		<div class="subtitle">Outside</div>
		<t:header></t:header>
	
		<div class="col-4">
		
			<div class="row">
				<div class="sub-2">left</div>
				<div class="sub-8 text-center">mid</div>
				<div class="sub-2">right</div>
			</div>
			<div class="row">
				<div class="sub-2">left</div>
				<div class="sub-8 text-center">
				
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
								<c:when test="${zone.zeds > 0 && zone.zeds <= 2}">
									<rect x="${left}" y="${top}" height="15" width="15" class="grid-green">
										<title><c:out value="${x}, ${y}"/></title>
									</rect>
								</c:when>
							</c:choose>
							
						
						</c:forEach>
					</svg>
				
				</div>
				<div class="sub-2">right</div>
			</div>
			<div class="row">
				<div class="sub-2">left</div>
				<div class="sub-8 text-center">mid</div>
				<div class="sub-2">right</div>			
			</div>
		
		</div>
		<div class="col-4">
		
			items
		
		</div>
		<div class="col-4">
		
			bulletin
		
		</div>

	
	</jsp:attribute>
</t:generic>