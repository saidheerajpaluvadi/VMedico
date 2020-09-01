<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<title>Dashboard |VMedico</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<style>
.topnav {
  overflow: hidden;
  background-color: #cc0000;
}
.mylabel{
background-color: #cc0000;
padding:14px;
color:white;
float: left;
width:200px;
font-size:22px;"
}

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

li.dropdown {
  display: inline-block;
}

.dropdown-content {
  display: none;
  position: absolute;
  background-color: #f9f9f9;
  min-width: 160px;
  box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
  z-index: 1;
}

.dropdown-content a {
  color: black;
  padding: 12px 16px;
  text-decoration: none;
  display: block;
  text-align: left;
}

.dropdown-content a:hover {background-color: #4C4646;}

.dropdown:hover .dropdown-content {
  display: block;
}
body {
  margin: 0;
  font-family: Arial; 
  
}
</style>

</head>
<body>
<%
response.setHeader("Cache-Control","no-cache, no-store, must-revalidate");
response.setHeader("Pragma","no-cache");
response.setHeader("Expires", "0");

if(session.getAttribute("testinglabmail") == null){
	response.sendRedirect("home.jsp");
}
%>

<div class="topnav">
  
 <div class=mylabel>Vmedico</div>
 
</div>
<ul>
  <li><a href="alltestlabappointments">Appointment Requests</a></li>
  
  <li class="dropdown">
    <a href="javascript:void(0)" class="dropbtn">Appointments Accepted</a>
    <div class="dropdown-content">
      <a href="todaytestlabappointments">Today </a>
      <a href="upcomingtestlabappointments">Upcoming </a>
      <a href="previoustestlabappointments">Previous </a>
      <a href="allappointmentsacceptedbytestlab">All</a>
    </div>
  </li>
    
  <li class="dropdown">
   <a href="javascript:void(0)" class="dropbtn">Reports</a>
    <div class="dropdown-content">
      <a href="newtestlabreports">New </a>
      <a href="oldtestlabreports">Old </a>
    </div>
  </li>
  
   <li style="float:right;width:100px;margin-right:60px;"><a href="testlablogout"> Logout <i class="fa fa-sign-out"></i></a></li>
	<li style="float:right;"> <a href="testlabprofile">Profile</a></li>
</ul>


</body>
</html>
