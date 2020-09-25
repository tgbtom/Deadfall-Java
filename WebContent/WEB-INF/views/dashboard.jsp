<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<script src="${pageContext.request.contextPath}/resources/js/dashboard.js"></script>
<title>Dashboard</title>

<t:generic>
	<jsp:attribute name="mainContent">
	
	  <div class="row">
        <div class="col-8">
          <div class="card dash-char">
            <div class="card-top">Characters</div>
            <div class="card-content">
            
            <%@ page import="com.novaclangaming.model.User" %>
            <%@ page import="com.novaclangaming.model.Character" %>
            <%@ page import="com.novaclangaming.dao.JPACharacterDao" %>
            <%@ page import="java.util.List" %>
            
			<c:set var="user" scope="page" value = "${sessionScope.user.username}"/>
			<c:set var="charDao" scope="page" value = "${JPACharacterDao()}"/>
			
			<c:forEach items="${charDao.findByUserId(sessionScope.user.id)}" var="c">
            	<c:set var="btnText" scope="page" value = "${c.getTown() == null ? \"Join\" : \"Play\"}"/>
            	<c:set var="btnClass" scope="page" value = "${c.getTown() == null ? \"btn-join\" : c.hasStatusByName(\"Dead\") ? \"btn-delete\" : \"btn-play\"}"/>

            	<div class="row bb">
				<form action="${pageContext.request.contextPath}/character/check" method="POST">
					<input type="hidden" name="charId" value='<c:out value="${ c.charId }" />'>
					<div class="sub-5 text-bold"><a class="charLink" href='${pageContext.request.contextPath}/character/<c:out value="${ c.charId }" />'><c:out value="${ c.name }" /> [0]</a></div>
					<div class="sub-5"><c:out value="${ c.classification }" /></div>
					<div class="sub-2">
						<input type="submit" value='<c:out value="${btnText}" />' class='<c:out value="${btnClass}" />'>
					</div>
				</form>
				</div>

			</c:forEach>
              
            </div>
          </div>
          <div class="row mt-6">
            <div class="card dash-char">
              <div class="card-top">Create Character</div>
              <div class="card-content card-form">
                <form action="${pageContext.request.contextPath}/character/create" method="POST">
                  <div class="input-group">
                    <label for="char-name">Name: </label>
                    <input
                      type="text"
                      name="charName"
                      placeholder="Character Name"
                      class="char-form"
                      required
                    />
                  </div>
                  <div class="input-group">
                    <label for="char-class">Class: </label>
                    <Select name="charClass" class="char-form" id="char-class">
                      <option value="Survivor" selected>Survivor</option>
                      <option value="Builder">Builder</option>
                      <option value="Looter">Looter</option>
                      <option value="Runner">Runner</option>
                    </Select>
                  </div>
                  <div class="card inner-card">
                    <div class="card-content">
                      <span id="class-desc">
                        The survivor class is well rounded, having a maximum AP
                        of 16 rather than 12. The survivor does not specialize
                        in anything.
                      </span>
                    </div>
                  </div>
                  <div class="input-group">
                    <input type="submit" value="Create" class="char-form" />
                  </div>
                </form>
              </div>
            </div>
          </div>
        </div>
        <div class="col-4">
          <div class="card dash-bulletin">
            <div class="card-top">User Bulletin</div>
            <div class="card-content">
            
            
            <c:forEach items="${ sessionScope.bulletins }" var="item">

            	<div class="row bb">
            		<%-- <div class="sub-3"><c:out value="${item.getPostedTime()}" /></div> --%>
            		<div class="sub-3 br"><fmt:formatDate pattern="MMM dd, yyyy" value="${item.getPostedTime()}" /></div>
            		
            		<div class="sub-9">
            			<c:out value="${item.getContent()}"/>
            		</div>
            	</div>
            </c:forEach>
            </div>
          </div>
        </div>
      </div>
	
	</jsp:attribute>
</t:generic>

    <div id="modal-container">
      <img src="${pageContext.request.contextPath}/resources/img/exit.png" alt="close modal" id="modal-exit" />
    </div>
  </body>
</html>
