<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<nav class="navbar navbar-inverse navbar-fixed-top">
	<div class="container">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#navbar" aria-expanded="false"
				aria-controls="navbar">
				<span class="sr-only">Toggle navigation</span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="./">
				<span class="glyphicon glyphicon-hdd" aria-hidden="true"></span>
			</a>
		</div>
		<div id="navbar" class="collapse navbar-collapse">
			<ul class="nav navbar-nav">
				<li id="navUpload"><a href="./upload"><spring:message code="common.menu.upload" /></a></li>
				<li id="navDownload"><a href="./download"><spring:message code="common.menu.download" /></a></li>
			</ul>
		</div>
		<!--/.nav-collapse -->
	</div>
</nav>