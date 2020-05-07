<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri = "http://java.sun.com/jsp/jstl/core" %>

<t:generic>
	<jsp:attribute name="mainContent">
		<c:set var="user" scope="page" value="${ sessionScope.user }" />
		
		<div class="row">
			<div class="subtitle"><c:out value="${ user.username }" /></div>
		</div>
	
		<div class="row">
			<div class="sub-6">Email:</div>
			<div class="sub-6"><c:out value="${ user.email }" /></div>
		</div>
		<div class="row">
			<div class="sub-6">Characters:</div>
			<div class="sub-6"><c:out value="${ user.characters.size() }" />/20</div>
		</div>
		
		<div class="row">
			Unlocked Skills will go here
		</div>
		<div class="row">
			Achievements will go here
		</div>
	</jsp:attribute>
</t:generic>