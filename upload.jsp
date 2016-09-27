<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>

<%@ include file="template/localHeader.jsp"%>
<openmrs:htmlInclude  file="/moduleResources/fileupload/fileupload.css"/>

<div class="fileupload_display_settings">
	<form method="POST" action="upload.form" enctype="multipart/form-data">
		<p>
			Target directory:
			<select name="targetDir">
				<c:forEach var="td" items="${ targetDirs }">
					<option value="${ td }">${ td }</option>
				</c:forEach>
			</select>
		</p>
		<p>
			Choose your file:
			<input type="file" name="file"/>
		</p>
		<p>
			<input type="submit" value="Upload"/>
		</p>
	</form>
</div>
<div class="fileupload_result">
	<p>${ result }</p>
</div>
<%@ include file="/WEB-INF/template/footer.jsp"%>