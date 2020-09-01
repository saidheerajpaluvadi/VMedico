<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<title>Dashboard |VMedico</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<style>

ul {
  list-style-type: none;
  margin: 0;
  padding: 0;
  overflow: hidden;
  background-color: #f1f1f1;
  
}

li {
  float: left;
}

li a, .dropbtn {
  display: inline-block;
  color: black;
  text-align: center;
  padding: 14px 16px;
  text-decoration: none;
}

li a:hover, .dropdown:hover .dropbtn {
  background-color: #4C4646;
  color:white;
}


body {
  margin: 0;
  font-family: Arial; 
}

.topnav {
  overflow: hidden;
  background-color: #cc0000;
}
.topnav a {
  float: left;
  color: #f2f2f2;
  text-align: center;
  padding: 16px 16px;
  text-decoration: none;
  font-size: 18px;
}

.topnav a:hover {
  background-color: #ddd;
  color: black;
}

.topnav a.active {
  background-color: white;
  color: #cc0000;
  font-weight:bold;
}

.doctordiv{
float: right;
width:100px;
 cursor: pointer;
 font-size:18px;
 
}
.testinglabdiv{
float: right;
width:150px;
 cursor: pointer;
 font-size:18px;
}
.mylabel{
background-color: #cc0000;
padding:14px;
color:white;
float: left;
width:200px;
font-size:22px;"
}

</style>
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

<div class="topnav">
  
 <div class=mylabel>Vmedico</div>
 <div class="testinglabdiv"><a href="patientstabfortestlab.jsp">Testing lab</a></div>
 <div class="doctordiv"> <a class="active" href="patientstab.jsp">Doctors</a></div>
 <br style="clear: left;" />
</div>

<ul>
  <li><a href="bookanappointment.jsp">Book an Appointment</a></li>
  <li><a href="cancelanappointment">Cancel an Appointment</a></li>
  <li><a href="status">Status</a></li>
  <li><a href="prescriptiongivenbydoctor">Prescription</a></li>
 
  <li  style="float:right;width:100px;margin-right:60px;"> <a href="patientlogout"> Logout <i class="fa fa-sign-out"></i></a></li>
	<li style="float:right;"> <a href="patientprofile">Profile</a></li>
</ul>


</body>
</html>
