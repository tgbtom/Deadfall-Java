<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<!-- FOR JSTL tag Library -->
<%@ taglib prefix="c" uri = "http://java.sun.com/jsp/jstl/core" %>

<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/main.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/overview.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/construction.css" />


<script src="${pageContext.request.contextPath}/resources/js/construction.js"></script>
<title>Structures</title>
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
							
							<c:forEach items="${sessionScope.unlockedDefence}" var="c">
							<c:if test='${ c.key.category.equals("Defence") }'>
								<c:set var="costString" value="t"/>
								<c:forEach items="${ c.key.costs }" var="curCost">
									<c:set var="currentCostConcat" value="${curCost.item.name}:${curCost.quantity}"/>
									<c:set var="concatenatedAddItem" value="${costString},${currentCostConcat}"/>
									<c:set var="costString" value="${ costString == 't' ?  currentCostConcat :  concatenatedAddItem  }" />
								</c:forEach>
								<c:set var="func" scope="page" value="showStructureModal(${ c.key.structureId }, \'${ c.key.name }\', \'${ c.key.description }\', \'${ costString }\', ${ c.key.apCost - c.value.ap }, ${ c.key.defence }, ${ c.value.level }, ${ c.key.levels })" />
								
								<c:choose>
									<c:when test="${ c.value.ap > 0 }">
										<tr class="clickable-structure green-row" onclick="${ func }">
									</c:when>
									<c:when test="${ sessionScope.structureDao.isStructureAffordable(sessionScope.townId, c.key.structureId) }">
										<tr class="clickable-structure yellow-row" onclick="${ func }">
									</c:when>
									<c:when test="${ c.value.level >= c.key.levels }">
										<tr class="blue-row">
									</c:when>
									<c:otherwise>
										<tr class="red-row">
									</c:otherwise>
								</c:choose>

								
									<td><c:out value="${ c.value.level }"/>/<c:out value="${ c.key.levels }"/></td>
									<td class="bb"><span title='<c:out value="${ c.key.description }"/>'><c:out value="${ c.key.name }"/></span> <img src="${pageContext.request.contextPath}/resources/img/icons/Shield.png" class="Defence Granted" /><small><c:out value="${ c.key.defence }"/></small> </td>
									<td class="bb">	
									
									<c:if test="${ c.value.ap == 0 && c.value.level < c.key.levels}">
										<c:forEach items="${ c.key.costs }" var="cost">
										<div class="citizens-item-group" title="<c:out value="${ cost.item.name }"/>">								
											<img src="${pageContext.request.contextPath}/resources/img/items/rarity/<c:out value="${ cost.item.rarity }"/>.png" class="item-rarity" />
											<img src="${pageContext.request.contextPath}/resources/img/items/<c:out value="${ cost.item.name }"/>.png" alt="<c:out value="${ cost.item.name }"/>" class="citizens-item" />	
											
												<c:set var="storageStack" value="${ sessionScope.stackZoneDao.findByZoneItem(sessionScope.storageId, cost.item.itemId) }"/>
												
												<c:choose>
													<c:when test="${ storageStack.isPresent() }">
														<c:choose>
															<c:when test="${ storageStack.get().getQuantity() >= cost.quantity }">
																<span class="citizens-item-counter text-bright-green"><c:out value="${ cost.quantity }"/></span>
															</c:when>
															<c:otherwise><span class="citizens-item-counter text-yellow"><c:out value="${ cost.quantity }"/></span></c:otherwise>
														</c:choose>
													</c:when>
													<c:otherwise>
														<span class="citizens-item-counter text-yellow"><c:out value="${ cost.quantity }"/></span>
													</c:otherwise>
												</c:choose>
												
										</div>
										</c:forEach>
									</c:if>								


									</td>
									<td class="bb">
										<c:choose>
											<c:when test="${ c.value.level < c.key.levels }">
												<c:out value="${ c.value.ap }"/>/<c:out value="${ c.key.apCost }"/><img src="${pageContext.request.contextPath}/resources/img/icons/ap.png" class="AP" />
											</c:when>
											<c:otherwise><b>Completed</b></c:otherwise>
										</c:choose>
									</td>
								</tr>
							</c:if>
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
							
							<c:forEach items="${sessionScope.unlockedDefence}" var="c">
							<c:if test='${ c.key.category.equals("Supply") }'>
								<c:set var="costString" value="t"/>
								<c:forEach items="${ c.key.costs }" var="curCost">
									<c:set var="currentCostConcat" value="${curCost.item.name}:${curCost.quantity}"/>
									<c:set var="concatenatedAddItem" value="${costString},${currentCostConcat}"/>
									<c:set var="costString" value="${ costString == 't' ?  currentCostConcat :  concatenatedAddItem  }" />
								</c:forEach>
								<c:set var="func" scope="page" value="showStructureModal(${ c.key.structureId }, \'${ c.key.name }\', \'${ c.key.description }\', \'${ costString }\', ${ c.key.apCost - c.value.ap }, ${ c.key.defence }, ${ c.value.level }, ${ c.key.levels })" />
									
									
									<c:choose>
										<c:when test="${ c.value.ap > 0 }">
											<tr class="clickable-structure green-row" onclick="${ func }">
										</c:when>
										<c:when test="${ sessionScope.structureDao.isStructureAffordable(sessionScope.townId, c.key.structureId) }">
											<tr class="clickable-structure yellow-row" onclick="${ func }">
										</c:when>
										<c:when test="${ c.value.level >= c.key.levels }">
											<tr class="blue-row">
										</c:when>
										<c:otherwise>
											<tr class="red-row">
										</c:otherwise>
									</c:choose>
									
									
									<td><c:out value="${ c.value.level }"/>/<c:out value="${ c.key.levels }"/></td>
									<td class="bb"><c:out value="${ c.key.name }"/></td>
									<td class="bb">	
									
									<c:if test="${ c.value.ap == 0 && c.value.level < c.key.levels }">
										<c:forEach items="${ c.key.costs }" var="cost">	
											<div class="citizens-item-group" title="<c:out value="${ cost.item.name }"/>">								
												<img src="${pageContext.request.contextPath}/resources/img/items/rarity/<c:out value="${ cost.item.rarity }"/>.png" class="item-rarity" />
												<img src="${pageContext.request.contextPath}/resources/img/items/<c:out value="${ cost.item.name }"/>.png" alt="<c:out value="${ cost.item.name }"/>" class="citizens-item" />	
												
												<c:set var="storageStack" value="${ sessionScope.stackZoneDao.findByZoneItem(sessionScope.storageId, cost.item.itemId) }"/>
												
												<c:choose>
													<c:when test="${ storageStack.isPresent() }">
														<c:choose>
															<c:when test="${ storageStack.get().getQuantity() >= cost.quantity }">
																<span class="citizens-item-counter text-bright-green"><c:out value="${ cost.quantity }"/></span>
															</c:when>
															<c:otherwise><span class="citizens-item-counter text-yellow"><c:out value="${ cost.quantity }"/></span></c:otherwise>
														</c:choose>
													</c:when>
													<c:otherwise>
														<span class="citizens-item-counter text-yellow"><c:out value="${ cost.quantity }"/></span>
													</c:otherwise>
												</c:choose>
											</div>
										</c:forEach>
									</c:if>

									</td>
									<td class="bb">
										<c:choose>
											<c:when test="${ c.value.level < c.key.levels }">
												<c:out value="${ c.value.ap }"/>/<c:out value="${ c.key.apCost }"/><img src="${pageContext.request.contextPath}/resources/img/icons/ap.png" class="AP" />
											</c:when>
											<c:otherwise><b>Completed</b></c:otherwise>
										</c:choose>
									</td>
								</tr>
							</c:if>
							</c:forEach>
							
						</tbody>
					</table>
			
			</div>
		</div>	
		
		<div class="card mb-3">
			<div class="card-top">
				<div class="card-title">Production</div>
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
							
							<c:forEach items="${sessionScope.unlockedDefence}" var="c">
							<c:if test='${ c.key.category.equals("Production") }'>
								<c:set var="costString" value="t"/>
								<c:forEach items="${ c.key.costs }" var="curCost">
									<c:set var="currentCostConcat" value="${curCost.item.name}:${curCost.quantity}"/>
									<c:set var="concatenatedAddItem" value="${costString},${currentCostConcat}"/>
									<c:set var="costString" value="${ costString == 't' ?  currentCostConcat :  concatenatedAddItem  }" />
								</c:forEach>
								<c:set var="func" scope="page" value="showStructureModal(${ c.key.structureId }, \'${ c.key.name }\', \'${ c.key.description }\', \'${ costString }\', ${ c.key.apCost - c.value.ap }, ${ c.key.defence }, ${ c.value.level }, ${ c.key.levels })" />
								
									<c:choose>
										<c:when test="${ c.value.ap > 0 }">
											<tr class="clickable-structure green-row" onclick="${ func }">
										</c:when>
										<c:when test="${ sessionScope.structureDao.isStructureAffordable(sessionScope.townId, c.key.structureId) }">
											<tr class="clickable-structure yellow-row" onclick="${ func }">
										</c:when>
										<c:when test="${ c.value.level >= c.key.levels }">
											<tr class="blue-row">
										</c:when>
										<c:otherwise>
											<tr class="red-row">
										</c:otherwise>
									</c:choose>
								
								
									<td><c:out value="${ c.value.level }"/>/<c:out value="${ c.key.levels }"/></td>
									<td class="bb"><c:out value="${ c.key.name }"/></td>
									<td class="bb">	
									<c:if test="${ c.value.ap == 0 && c.value.level < c.key.levels }">
										<c:forEach items="${ c.key.costs }" var="cost">
											<div class="citizens-item-group" title="<c:out value="${ cost.item.name }"/>">								
												<img src="${pageContext.request.contextPath}/resources/img/items/rarity/<c:out value="${ cost.item.rarity }"/>.png" class="item-rarity" />
												<img src="${pageContext.request.contextPath}/resources/img/items/<c:out value="${ cost.item.name }"/>.png" alt="<c:out value="${ cost.item.name }"/>" class="citizens-item" />	
												
												<c:set var="storageStack" value="${ sessionScope.stackZoneDao.findByZoneItem(sessionScope.storageId, cost.item.itemId) }"/>
												
												<c:choose>
													<c:when test="${ storageStack.isPresent() }">
														<c:choose>
															<c:when test="${ storageStack.get().getQuantity() >= cost.quantity }">
																<span class="citizens-item-counter text-bright-green"><c:out value="${ cost.quantity }"/></span>
															</c:when>
															<c:otherwise><span class="citizens-item-counter text-yellow"><c:out value="${ cost.quantity }"/></span></c:otherwise>
														</c:choose>
													</c:when>
													<c:otherwise>
														<span class="citizens-item-counter text-yellow"><c:out value="${ cost.quantity }"/></span>
													</c:otherwise>
												</c:choose>
											
											</div>
										</c:forEach>
										</c:if>

									</td>
									<td class="bb">
										<c:choose>
											<c:when test="${ c.value.level < c.key.levels }">
												<c:out value="${ c.value.ap }"/>/<c:out value="${ c.key.apCost }"/><img src="${pageContext.request.contextPath}/resources/img/icons/ap.png" class="AP" />
											</c:when>
											<c:otherwise><b>Completed</b></c:otherwise>
										</c:choose>
									</td>
								</tr>
							</c:if>
							</c:forEach>
							
						</tbody>
					</table>
			
			</div>
		</div>	
		
	
		<div id="modal-container">
			<img src="${pageContext.request.contextPath}/resources/img/exit.png" alt="close modal" id="modal-exit" onclick="hideModals()"/>
			<div class="modal" id="modal-structure">
				<div class="card">
					<div class="card-top">
						[<span id="modal-structure-level"></span>/<span id="modal-structure-max-level"></span>]
						<span id="modal-structure-title"><!-- Selected structure name goes here --></span>
						<img src="${pageContext.request.contextPath}/resources/img/icons/Shield.png" class="Defence Granted" /><span id="modal-structure-defence"></span>
					</div>
					<div class="card-content">
						<div class="row">
							<div class="sub-3">
								<img src="${pageContext.request.contextPath}/resources/img/structures/filler_building.png" class="Structure Icon" />
							</div>
							<div class="row"><div class="sub-9"><span id="modal-structure-desc"><!-- Selected structure description goes here --></span></div></div>
							<div class="row">
								<div class="sub-3"></div>
								<div class="sub-3">
								<span id="modal-structure-costs"><!-- Selected structure costs goes here --></span>
								</div>
								<div class="sub-3"><span id="modal-structure-ap-cost"><!-- Selected Structure ap cost goes here --></span><img src="${pageContext.request.contextPath}/resources/img/icons/ap.png" class="AP" /></div>
								<input type="hidden" id="modal-structure-id" value="" />
								<div class="sub-3"><button type="button" class="btn-play" onclick="promptAp(${ sessionScope.character.currentAp })">Build</button></div>
							</div>
						</div>
						
					</div>
				</div>
			</div>
		</div>
	
	</jsp:attribute>
</t:generic>