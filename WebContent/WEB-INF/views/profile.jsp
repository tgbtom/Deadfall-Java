<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri = "http://java.sun.com/jsp/jstl/core" %>

<t:generic>
	<jsp:attribute name="mainContent">
		<c:set var="user" scope="page" value="${ sessionScope.user }" />
		
		<div class="subtitle">Profile - <c:out value="${ user.username }" /></div>
		
		<div class="row">
			<div class="card col-6">
				<div class="card-top">Information</div>
				<div class="card-content">
					<div class="row">
						<div class="sub-5 text-right"><strong>Email:</strong></div>
						<div class="sub-7"><c:out value="${ user.email }" /></div>
					</div>
					<div class="row">
						<div class="sub-5 text-right"><strong>Characters:</strong></div>
						<div class="sub-7"><c:out value="${ user.characters.size() }" />/20</div>
					</div>
				</div>
			</div>
			<div class="card col-6">
				<div class="card-top">Skills & Achievements</div>
				<div class="card-content">
					<div class="row">
						Unlocked Skills will go here
					</div>
					<div class="row">
						Achievements will go here
					</div>
				</div>
			</div>
		</div>
		
	</jsp:attribute>
</t:generic>