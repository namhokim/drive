<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>    
<!DOCTYPE html>
<html>
<head>
	<%@include file="commonHeader.jsp"%>
	<title>Upload New File</title>
</head>
<body>
	<%@include file="menu.jsp"%>

    <div class="container">

      <div class="message">
        <h1><span class="glyphicon glyphicon-send" aria-hidden="true"></span> Upload your file</h1>
        <p class="lead">Select the file explore then push trasmit button,<br> or Drag &amp; Drop to below box.</p>
      </div>
      
      <div class="content">
      	<form:form modelAttribute="uploadItem" class="form-group" method="POST" enctype="multipart/form-data" action="./upload">
      		<label for="exampleInputFile">File select : </label>
      		<form:input path="fileData" type="file" id="send_file" name="file" size="100" style="width:500px;" />
			<button class="btn btn-primary" id="submit_button" type="submit">전송</button>
		</form:form>
      </div>

	</div><!-- /.container -->

    <%@include file="commonJs.jsp"%>
    <script>
    $( document ).ready(function() {
    	$('#navUpload').addClass('active');
    });
    </script>
</body>
</html>