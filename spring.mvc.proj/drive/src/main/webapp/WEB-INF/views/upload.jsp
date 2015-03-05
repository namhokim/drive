<%@ page session="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>  
<!DOCTYPE html>
<html>
<head>
	<%@include file="commonHeader.jsp"%>
	<title><spring:message code="common.title" /></title>
	<link href="resources/css/upload.css" rel="stylesheet" media="screen">
</head>
<body>
	<%@include file="menu.jsp"%>

    <div class="container">

      <div class="message">
        <h1><span class="glyphicon glyphicon-send" aria-hidden="true"></span> <spring:message code="upload.title" /></h1>
        <p class="lead"><spring:message code="upload.message" /></p>
      </div>
      
      <div class="content">
      	<form:form modelAttribute="uploadItem" class="form-group" method="POST" enctype="multipart/form-data" action="./upload">
      		<label for="exampleInputFile"><spring:message code="upload.fileSelect" /> : </label>
      		<form:input path="fileData" type="file" id="send_file" name="file" size="100" style="width:500px;" />
			<button class="btn btn-primary" id="submit_button" type="submit"><spring:message code="upload.send" /></button>
		</form:form>
      </div>
      
		<div id="dnd" class="row">
			<div class="well">
				<div id="dragandrophandler"><span><spring:message code="upload.dragDropBox" /></span></div>
				<br>
				<br>
				<div id="status1"></div>
			</div>
		</div>

	</div><!-- /.container -->

    <%@include file="commonJs.jsp"%>
    <script>
    $( document ).ready(function() {
    	$('#navUpload').addClass('active');
    	initializeDragAndDrop();
    });
    
    var console = window.console || {log:function(){}};
    
 	// refs. http://hayageek.com/drag-and-drop-file-upload-jquery/jq
    function initializeDragAndDrop() {
    	var obj = $('#dragandrophandler');
		obj.on('dragenter', function(e) {
			e.stopPropagation();
			e.preventDefault();
			$(this).css('border', '2px solid #0B85A1');
		});
		obj.on('dragover', function(e) {
			e.stopPropagation();
			e.preventDefault();
		});
		obj.on('drop', function(e) {
			$(this).css('border', '2px dotted #0B85A1');
			e.preventDefault();			
			var files = e.originalEvent.dataTransfer.files;

			//We need to send dropped files to Server
			handleFileUpload(files, obj);
		});
    }
    
    function handleFileUpload(files, obj) {
		if (files == null) {
			alert('Not support browser!');
			return;
		}
		console.log('files: ' + files.length);
		for (var i = 0; i < files.length; i++) {
			var fd = new FormData();
			fd.append('fileData', files[i]);

			var status = new createStatusbar(obj); //Using this we can set progress.
			status.setFileNameSize(files[i].name, files[i].size);
			sendFileToServer(fd, status);
		}
	}

	var rowCount=0;
	
	function createStatusbar(obj) {
	     rowCount++;
	     var row="odd";
	     if(rowCount %2 ==0) row ="even";
	     this.statusbar = $("<div class='statusbar "+row+"'></div>");
	     this.filename = $("<div class='filename'></div>").appendTo(this.statusbar);
	     this.size = $("<div class='filesize'></div>").appendTo(this.statusbar);
	     this.progressBar = $("<div class='progressBar'><div></div></div>").appendTo(this.statusbar);
	     this.abort = $("<div class='abort'>Abort</div>").appendTo(this.statusbar);
	     obj.after(this.statusbar);
	 
	    this.setFileNameSize = function(name,size) {
	        var sizeStr="";
	        var sizeKB = size/1024;
	        if(parseInt(sizeKB) > 1024) {
	            var sizeMB = sizeKB/1024;
	            sizeStr = sizeMB.toFixed(2)+" MB";
	        } else {
	            sizeStr = sizeKB.toFixed(2)+" KB";
	        }
	 
	        this.filename.html(name);
	        this.size.html(sizeStr);
	    }
	    this.setProgress = function(progress) {       
	        var progressBarWidth =progress*this.progressBar.width()/ 100;  
	        this.progressBar.find('div').animate({ width: progressBarWidth }, 10).html(progress + "% ");
	        if(parseInt(progress) >= 100) {
	            this.abort.hide();
	        }
	    }
	    this.setAbort = function(jqxhr) {
	        var sb = this.statusbar;
	        this.abort.click(function() {
	            jqxhr.abort();
	            sb.hide();
	        });
	    }
	}
	
	function sendFileToServer(formData, status) {
		var uploadURL = "./upload"; //Upload URL
		var extraData = {}; //Extra Data.
		var jqXHR = $.ajax({
			xhr : function() {
				var xhrobj = $.ajaxSettings.xhr();
				if (xhrobj.upload) {
					xhrobj.upload.addEventListener('progress',
							function(event) {
						var percent = 0;
						var position = event.loaded || event.position;
						var total = event.total;
						if (event.lengthComputable) {
							percent = Math.ceil(position / total * 100);
						}
						//Set progress
						status.setProgress(percent);
					}, false);
				}
				return xhrobj;
			},
			url : uploadURL,
			type : "POST",
			contentType : false,
			processData : false,
			cache : false,
			data : formData,
			success : function(data) {
				status.setProgress(100);
			}
		});

		status.setAbort(jqXHR);
	}
    </script>
</body>
</html>