<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>File Upload |VMedico</title>
</head>
<body>
<%
response.setHeader("Cache-Control","no-cache, no-store, must-revalidate");
response.setHeader("Pragma","no-cache");
response.setHeader("Expires", "0");

if(session.getAttribute("patientmail") == null){
	response.sendRedirect("home.jsp");
}
%>
<jsp:include page="patientstab.jsp"/>


<script type="text/javascript">
function validate(){
 var img = document.getElementById("patientfile");
 if(img.files.length == 0 ){
 	document.getElementById("error").innerHTML="No file chosen";
    return false;
    }
 
 if(img.files[0].size > 209715200){
 document.getElementById("error").innerHTML="File size is big";
    return false;
 }
 
   else 
    return true;
}
</script>
<div style="text-align:center;margin-top:auto;margin-left:auto;">
<form action="uploadpatientfiles" method="post"  enctype="multipart/form-data" onSubmit = "return validate()">
<input type="file" name="patientfiles" id="patientfile"  required ><br/>
<h2 id="error" style="color: red;"></h2>
<button type ="submit"> Upload</button><br/>
<h2> ${resultofuploadingpatientfiles}</h2>

</form>
</div>

</body>
</html>