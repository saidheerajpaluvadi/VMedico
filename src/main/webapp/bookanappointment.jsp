<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<html>
<head>
<meta charset="ISO-8859-1">
<title>Specialists |VMedico</title>
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
<jsp:include page="patientstab.jsp"/>
<div style="text-align:center">
<h2>Specialists Available</h2>

<form action="doctordetails" method="POST">
<p >
<input type="submit" name="spec" value="Cardiologist"/>

<input type="submit" name="spec" value="Dentist"/>

<input type="submit" name="spec" value="ENT specialist"/>

<input type="submit" name="spec" value="Gynaecologist"/>
</p>
</form>

<form action="doctordetails" method="POST">
<p>
<input type="submit" name="spec" value="Orthopaedic surgeon"/>

<input type="submit" name="spec" value="Paediatrician"/>

<input type="submit" name="spec" value="Psychiatrists"/>

<input type="submit" name="spec" value="Neurologist"/>
</p>
</form>
</div>

</body>
</html>