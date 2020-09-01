<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Lab Tests |VMedico</title>
</head>
<style>
input{
 padding: 16px 32px;
 text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 16px;
  margin: 10px 2px;
  transition-duration: 0.4s;
  cursor: pointer;
  background-color: white; 
  color: black; 
  border: 2px solid #cc0000;
  width:200px;
}
input:hover{
  background-color: #f44336;
  color: white;
}
p{
padding:10px;
margin-top: 40px; 
margin-bottom:40px;
}
</style>
<body>
<%
response.setHeader("Cache-Control","no-cache, no-store, must-revalidate");
response.setHeader("Pragma","no-cache");
response.setHeader("Expires", "0");

if(session.getAttribute("patientmail") == null){
	response.sendRedirect("home.jsp");
}
%>
<jsp:include page="patientstabfortestlab.jsp"/>
<div style="text-align:center">
<h2>Tests Available</h2>

<form action="testlaboratorydetails" method ="POST">
<p >
<input type="submit" name="testname" value="Glucose"/>

<input type="submit" name="testname" value="Haemoglobin"/>

<input type="submit" name="testname" value="Urine"/>

<input type="submit" name="testname" value="Platelet Count"/>
</p>
</form>

<form action="testlaboratorydetails" method ="POST">
<p>
<input type="submit" name="testname" value="CT-Brain"/>

<input type="submit" name="testname" value="CT-Chest"/>

<input type="submit" name="testname" value="MRI-KNEE"/>

<input type="submit" name="testname" value="MRI-PELVIS"/>
</p>
</form>
</div>

</body>
</html>