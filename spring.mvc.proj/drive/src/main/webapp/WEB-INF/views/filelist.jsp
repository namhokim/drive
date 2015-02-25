<%@ page session="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
						<a href="./download/${listValue.name}" class="btn btn-default file">
							<span class="glyphicon glyphicon-save" aria-hidden="true"></span>
							${listValue.name}
						</a>
						<button type="button" class="btn btn-default removeMe">
							<span class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span>
						</button>
					</div>
					</li>
				</c:forEach>
			</ul>
		</form:form>
		</div>
    </div><!-- /.container -->
    
    <!-- Modal -->
	<div class="modal fade" id="noticeBox" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
	        <h4 class="modal-title" id="noticeBoxTitle">모달 제목</h4>
	      </div>
	      <div class="modal-body"  id="noticeBoxMessage">
	        <p>적절한 본문…</p>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-primary" data-dismiss="modal">닫기</button>
	      </div>
	    </div><!-- /.modal-content -->
	  </div><!-- /.modal-dialog -->
	</div><!-- /.modal -->

    <%@include file="commonJs.jsp"%>
    <script>
    var prefixDownload = './download/';
    var prefixRemove = './remove/';

    $( document ).ready(function() {
    	$('#navDownload').addClass('active');
    	$('.removeMe').click(function() {
    		var filename = $(this).prev().attr('href').substr(prefixDownload.length);
    		removeFile(filename);
    		var elemList = $(this).offsetParent().offsetParent(); 
    		elemList.css( "background-color", "#C04848" );
    		elemList.fadeOut(1000, function() { $(this).remove(); });
    	});
    });
    
    function removeFile(filename, succeedHook) {
    	$.ajax({
			  type: "DELETE",
			  url: prefixRemove + filename
		}).done(function(data) {
			if (data.success) {
				showMessageBox('Success', '"' + filename + '" was deleted');	
			} else {
				showMessageBox('Failure', data.reason);
			}
		}).fail(function(request) {
			if (request.status === 0) {
				showMessageBox('Error', 'Cannot connect to server');
			} else {
				showMessageBox('Error', request.statusText + '(' + request.status + ').');
			}
		});
    }
    
    function showMessageBox(title, message) {
    	$('#noticeBoxTitle').text(title);
		$('#noticeBoxMessage').text(message);
		$('#noticeBox').modal();
    }
    </script>
</body>
</html>