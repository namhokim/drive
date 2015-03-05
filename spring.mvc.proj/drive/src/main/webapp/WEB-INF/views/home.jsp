<%@ page session="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html>
<html>
<head>
	<%@include file="commonHeader.jsp"%>
	<title><spring:message code="common.title" /></title>
</head>
<body>
    <%@include file="menu.jsp"%>

    <div class="container">
      <div class="message">
        <h1><span class="glyphicon glyphicon-hdd" aria-hidden="true"></span> <spring:message code="home.title" /></h1>
        
        <div class="inner-common">
	        <blockquote class="blockquote-reverse">
	       		<p><spring:message code="home.quotation.content" /></p>
	       		<footer><spring:message code="home.quotation.author" /></footer>
	      	</blockquote>
      	</div>
        <p class="lead">
			<span><spring:message code="home.message" /></span>
        </p>
      </div>
    </div><!-- /.container -->

	<%@include file="commonJs.jsp"%>
</body>
</html>