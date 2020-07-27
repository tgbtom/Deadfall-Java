<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<!-- FOR JSTL tag Library -->
<%@ taglib prefix="c" uri = "http://java.sun.com/jsp/jstl/core" %>

<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/main.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/overview.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/construction.css" />


<script src="${pageContext.request.contextPath}/resources/js/construction.js"></script>
<title>Citizens</title>
<!-- copy this file into a new JSP file to include universal header with container -->

<t:generic>
	<jsp:attribute name="mainContent">
	
	<t:intown></t:intown>
	
		<div class="subtitle">Structures</div>
		<t:header></t:header>
	
		<div class="card mb-3">
			<div class="card-top">
				<div class="card-title">Defence</div>
			</div>
			<div class="card-content">
		
					<table class="full-table">
						<thead>
							<tr>
								<th>Level</th>
								<th>Structure</th>
								<th>Costs</th>
								<th>Progress</th>
							</tr>
						</thead>
						<tbody>
							
							<c:forEach items="${sessionScope.defenceStructures}" var="c">
								<tr class="clickable-structure" onclick="showStructureModal()">
									<td>0/5</td>
									<td class="bb"><c:out value="${ c.name }"/></td>
									<td class="bb">	
										<c:forEach items="${ c.costs }" var="cost">
											
											<div class="citizens-item-group" title="<c:out value="${ cost.item.name }"/>">								
											<img src="${pageContext.request.contextPath}/resources/img/items/rarity/Rare.png" class="item-rarity" />
											<img src="${pageContext.request.contextPath}/resources/img/items/<c:out value="${ cost.item.name }"/>.png" alt="<c:out value="${ cost.item.name }"/>" class="citizens-item" />	
											<span class="citizens-item-counter"><c:out value="${ cost.quantity }"/></span>
										</div>
										</c:forEach>

									</td>
									<td class="bb">
										<c:out value="${ c.apCost }"/>AP
										<button type="button" class="btn-icon">+</button>
									</td>
								</tr>
							</c:forEach>
							
						</tbody>
					</table>
			
			</div>
		</div>	
		
		<div class="card mb-3">
			<div class="card-top">
				<div class="card-title">Supply</div>
			</div>
			<div class="card-content">
				<div class="row">
					<table class="full-table">
						<thead>
							<tr>
								<th>Level</th>
								<th>Structure</th>
								<th>Costs</th>
								<th>Progress</th>
							</tr>
						</thead>
						<tbody>
							
							<c:forEach items="${sessionScope.supplyStructures}" var="c">
								<tr>
									<td>0/5</td>
									<td><c:out value="${ c.name }"/></td>
									<td>5 x O, 15 x O</td>
									<td>0/95AP</td>
								</tr>
							</c:forEach>
							
						</tbody>
					</table>
				</div>
			</div>
		</div>	
		
		<div class="card mb-3">
			<div class="card-top">
				<div class="card-title">Production</div>
			</div>
			<div class="card-content">
				<div class="row">
					<table class="full-table">
						<thead>
							<tr>
								<th>Level</th>
								<th>Structure</th>
								<th>Costs</th>
								<th>Progress</th>
							</tr>
						</thead>
						<tbody>
							
							<c:forEach items="${sessionScope.productionStructures}" var="c">
								<tr>
									<td>0/5</td>
									<td><c:out value="${ c.name }"/></td>
									<td>5 x O, 15 x O</td>
									<td>0/95AP</td>
								</tr>
							</c:forEach>
							
						</tbody>
					</table>
				</div>
			</div>
		</div>	
	
		<div id="modal-container">
			<img src="${pageContext.request.contextPath}/resources/img/exit.png" alt="close modal" id="modal-exit" onclick="hideModals()"/>
			<div class="modal" id="modal-structure">
				<div class="card">
					<div class="card-top">
						STRUCTURE
					</div>
					<div class="card-content">
						DETAILS HERE
					</div>
				</div>
			</div>
		</div>
	
	</jsp:attribute>
</t:generic>