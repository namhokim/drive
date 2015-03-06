<%@ page session="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
<head>
	<%@include file="commonHeader.jsp"%>
	<title><spring:message code="common.title" /> <spring:message code="common.version" /></title>
</head>
<body>
	<%@include file="menu.jsp"%>

	<div class="container">

      	<div class="message">
        	<h1><span class="glyphicon glyphicon-list" aria-hidden="true"></span> <spring:message code="download.title" /></h1>
        	<p class="lead"><spring:message code="download.message" /></p>
      	</div>
      
		<div class="files">
		<form:form>
			<ul class="list-inline">
				<c:forEach var="listValue" items="${lists}">
					<li class="list-group-item">
					<div class="btn-group" role="group" aria-label="file action">
						<a href="${listValue.name}" class="btn btn-default file download">
							<span class="glyphicon glyphicon-save" aria-hidden="true"></span>
							${listValue.name}
						</a>
						<button type="button" class="btn btn-default removeMe" value="${listValue.name}">
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
	        <h4 class="modal-title" id="noticeBoxTitle">modal title</h4>
	      </div>
	      <div class="modal-body"  id="noticeBoxMessage">
	        <p>modal message</p>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-primary" data-dismiss="modal"><spring:message code="download.close" /></button>
	      </div>
	    </div><!-- /.modal-content -->
	  </div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
	
	<!-- for javascript File download -->
	<iframe id=my_iframe style='display:none;'></iframe>

    <%@include file="commonJs.jsp"%>
    <script>
    $( document ).ready(function() {
    	$('#navDownload').addClass('active');
    	$('.download').each(function(obj) {
    		var filename = $(this).attr('href');
    		console.log(filename);
    		$(this).attr('href', './file?filename=' + encodeURIComponent(filename))
    	});
    	//console.log(dn.size());
    	$('.removeMe').click(function() {
    		var filename = $(this).val();
    		removeFile(filename);
    		var elemList = $(this).offsetParent().offsetParent(); 
    		elemList.css( "background-color", "#C04848" );
    		elemList.fadeOut(1000, function() { $(this).remove(); });
    	});
    });
    
    function removeFile(filename, succeedHook) {
    	$.ajax({
			  type: "DELETE",
			  url: './remove?filename=' + encodeURIComponent(filename)
		}).done(function(data) {
			if (data.success) {
				showMessageBox('<spring:message code="download.success" />', '"' + filename + '" <spring:message code="download.wasDeleted" />');	
			} else {
				showMessageBox('<spring:message code="download.failure" />', data.reason);
			}
		}).fail(function(request) {
			if (request.status === 0) {
				showMessageBox('<spring:message code="download.error" />', '<spring:message code="download.cannotConnectServer" />');
			} else {
				showMessageBox('<spring:message code="download.error" />', request.statusText + '(' + request.status + ').');
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