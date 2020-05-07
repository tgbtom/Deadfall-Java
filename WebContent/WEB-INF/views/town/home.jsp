<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/overview.css" />
<title>In Town</title>

<t:generic>
	<jsp:attribute name="mainContent">
		<t:intown></t:intown>
		
	<!-- Main Content goes here -->
		<div class="row">
			<div class="card">
				<div class="card-top">Bulletin</div>
			</div>
		</div>

	</jsp:attribute>
</t:generic>
