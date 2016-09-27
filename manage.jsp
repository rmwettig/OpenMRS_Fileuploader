<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>

<%@ include file="template/localHeader.jsp"%>
<openmrs:htmlInclude  file="/moduleResources/fileupload/fileupload.css"/>

<div class="fileupload_display_settings">
	<form action="manage.form" method="POST">
		<p>
			Current File Size:
			<input type="text" name="maxFileSize" value="${ uploadsettings.maxFileSizeValue } ${ uploadsettings.maxFileSizeUnit }"/>
		</p>
		<p>
			Allowed File Types:
			<input type="text" name="allowedFileTypes" value="${ uploadsettings.allowedFileTypesString }"/>
		</p>
		<p>
			<input type="submit" value="Save"/>
		</p>
	</form>
<!-- </div> -->

<p> spring form tags test </p>
<!-- <div class="fileupload_display_settings"> -->
	<form:form action="manage.form" method="POST" commandName="uploadsettings">
		<p>
			<form:label path="maxFileSizeValue">Max. File Size</form:label>
			<form:input path="maxFileSizeValue"/>
		</p>
		<p>
			<form:label path="maxFileSizeUnit">Max. File Size Unit (B,K,M,G)</form:label>
			<form:input path="maxFileSizeUnit"/>
		</p>
		<p>
			<form:label path="allowedFileTypesString">Allowed File Types (comma-separated)</form:label>
			<form:input path="allowedFileTypesString"/>
		</p>
		<p>
			<form:label path="disallowedFileTypesString">Disallowed File Types (comma-separated)</form:label>
			<form:input path="disallowedFileTypesString"/>
		</p>
		<p>
			<input type="submit" value="Save" />
		</p>
	</form:form>
<!-- </div> -->

<p>global property portlet</p>
<openmrs:portlet url="globalProperties" parameters="propertyPrefix=fileupload.|hidePrefix=true|readOnly=false"/>
</div>
<%@ include file="/WEB-INF/template/footer.jsp"%>