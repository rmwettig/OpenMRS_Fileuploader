<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>

<%@ include file="template/localHeader.jsp"%>
<script type="text/javascript">
$j( function() {
    $('#jquery_dirtree').fileTree({ root: 'appdata' }, function(file) {
        alert(file);
    });
});
</script>
<openmrs:htmlInclude  file="/moduleResources/fileupload/fileupload.css"/>

<div class="fileupload_display_settings">
	<form method="POST" action="managefolders.form">
		<p>
			Directory Name:
			<input type="text" name="newDirName"/>
		</p>
		<p>
			Choose operation:
			<input type="radio" value="create" name="dirOp"/>Create
			<input type="radio" value="delete" name="dirOp"/>Delete
			<input type="radio" value="up" name="dirOp"/>Up
			<input type="radio" value="down" name="dirOp"/>Down
			<input type="radio" value="default" name="dirOp"/>Default
			<!-- <select name="dirOp">
				<option value="create">Create</option>
				<option value="delete">Delete</option>
				<option value="up">Up</option>
				<option value="down">Down</option>
				<option value="default">Default</option>
			</select>-->
		</p>
		<p>
			<select name="selectedDir">
				<option value="${ appDirRoot }">${ appDirRoot }</option>
				<c:forEach var="fi" items="${ appdir }">
					<option value="${ currentPath }/${ fi }">${ fi }</option>
				</c:forEach>
			</select>
		</p>
		<p>
			<input type="hidden" name="breadcrumb" value="${ currentPath }"/>
		</p>
		<p>
			<input type="submit" value="Submit"/>
		</p>
	</form>
	<div id ="jquery_dirtree" class="fileupload_directory_tree">
		
	</div>
	----
	<div class="fileupload_directory_tree">
		<p>Current Location: ${ currentPath }</p>
		<c:forEach var="fi" items="${ appdir }">
			<p>${ fi }</p>
		</c:forEach>
	</div>
</div>
<p><openmrs:contextPath /></p>
<!-- <div class="fileupload_directory_tree">
		<c:forEach var="fi" items="${ contextpath }">
			<p>${ fi }</p>
		</c:forEach>
	</div> -->

<%@ include file="/WEB-INF/template/footer.jsp"%>