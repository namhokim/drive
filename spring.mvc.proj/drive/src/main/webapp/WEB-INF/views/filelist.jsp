<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>    
<!DOCTYPE html>
<html>
<head>
	<%@include file="commonHeader.jsp"%>
	<title>Download the New Files</title>
</head>
<body>
    <%@include file="menu.jsp"%>

    <div class="container">

      <div class="message">
        <h1><span class="glyphicon glyphicon-list" aria-hidden="true"></span> Download your file</h1>
        <p class="lead">Select the filename for download.<br> Don't forget remove after download for security.</p>
      </div>
      
      <div class="files">
			<form:form>
				<ul class="list-inline">
					<c:forEach var="listValue" items="${lists}">
						<li class="list-group-item">
							<div class="btn-group" role="group" aria-label="file action">
								<a href="./download/${listValue.name}" class="btn btn-default">
									<span class="glyphicon glyphicon-save" aria-hidden="true"></span>
									${listValue.name}
								</a>
								<button type="button" class="btn btn-default">
									<span class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span>
								</button>
						</div>
						</li>
					</c:forEach>
				</ul>
			</form:form>
		</div>

    </div><!-- /.container -->

    <%@include file="commonJs.jsp"%>
    <script>
    $( document ).ready(function() {
    	$('#navDownload').addClass('active');
    });
    </script>
</body>
</html>