<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<title>Date |VMedico</title>
<style>

.modal {
  display: none; 
  position: fixed;
  z-index: 1;
  padding-top: 100px; 
  left: 0;
  top: 0;
  width: 100%; 
  height: 100%; 
  overflow: auto; 
  background-color: rgb(0,0,0); 
  background-color: rgba(0,0,0,0.4); 
}

.modal-content {
  position: relative;
  background-color: #fefefe;
  margin: auto;
  padding: 0;
  border: 1px solid #888;
  width: 80%;
  box-shadow: 0 4px 8px 0 rgba(0,0,0,0.2),0 6px 20px 0 rgba(0,0,0,0.19);
  -webkit-animation-name: animatetop;
  -webkit-animation-duration: 0.4s;
  animation-name: animatetop;
  animation-duration: 0.4s
}

@-webkit-keyframes animatetop {
  from {top:-300px; opacity:0} 
  to {top:0; opacity:1}
}

@keyframes animatetop {
  from {top:-300px; opacity:0}
  to {top:0; opacity:1}
}

.close {
  color: white;
  float: right;
  font-size: 28px;
  font-weight: bold;
}

.close:hover,
.close:focus {
  color: #000;
  text-decoration: none;
  cursor: pointer;
}

.modal-header {
  padding: 2px 16px;
  background-color: #5cb85c;
  color: white;
}

.modal-body {padding: 2px 16px;}

.modal-footer {
  padding: 2px 16px;
  background-color: #5cb85c;
  color: white;
}

.timebutton{
  padding: 15px 32px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 16px;
  margin: 4px 2px;
  cursor: pointer;
 background-color: white;
  color: black;
  border: 2px solid #cc0000;
}
.mylabel{
background-color: #cc0000;
padding:10px;
font-size:22px;
color:white;
}
</style>

</head>
<body>

<jsp:include page="patientstab.jsp"/>
<%
response.setHeader("Cache-Control","no-cache, no-store, must-revalidate");
response.setHeader("Pragma","no-cache");
response.setHeader("Expires", "0");

if(session.getAttribute("patientmail") == null){
	response.sendRedirect("home.jsp");
}
%>


<div style="height:50%;">
<form action="bookondate" method = "POST">
<h3 style="text-align:center;color:#cc0000"> ${doctorbookingerrormsg} </h3>  
<h3 style="text-align:center;color:Green"> ${doctorbookingmsg} </h3>
<h3 style="text-align:center" id="todaydate"></h3>
<p style="text-align:center">
<button class="timebutton" name="time" value="today-10:00:00 AM" id="todbtn1"  type="submit">10:00AM</button>
<button class="timebutton" name="time" value="today-11:00:00 AM" id="todbtn2"  type="submit">11:00AM</button>
<button class="timebutton" name="time" value="today-02:00:00 PM" id="todbtn3"  type="submit">02:00PM</button>
</p>
<p style="text-align:center">
<button class="timebutton" name="time" value="today-03:00:00 PM" id="todbtn4"  type="submit">03:00PM</button>
<button class="timebutton" name="time" value="today-06:00:00 PM" id="todbtn5"  type="submit">06:00PM</button>
<button class="timebutton" name="time" value="today-07:00:00 PM" id="todbtn6"  type="submit">07:00PM</button>
</p>
</form>
</div>
<hr>
<div style="height:50%;">
<form action="bookondate" method = "POST">
<h3 style="text-align:center" id="tomorrowdate" ></h3>
<p style="text-align:center">
<button class="timebutton" name="time" value="tomorrow-10:00:00 AM" type="submit">10:00AM</button>
<button class="timebutton" name="time" value="tomorrow-11:00:00 AM" type="submit">11:00AM</button>
<button class="timebutton" name="time" value="tomorrow-02:00:00 PM" type="submit">02:00PM</button>
</p>
<p style="text-align:center">
<button class="timebutton" name="time" value="tomorrow-03:00:00 PM" type="submit">03:00PM</button>
<button class="timebutton" name="time" value="tomorrow-06:00:00 PM" type="submit">06:00PM</button>
<button class="timebutton" name="time" value="tomorrow-07:00:00 PM" type="submit">07:00PM</button>
</p>
</form>
</div>
<script>
var d = new Date();
var month = d.getMonth()+1;
document.getElementById("todaydate").innerHTML = d.getDate()+"-"+month+"-"+d.getFullYear();

if(d.getHours()>=10)
{
document.getElementById("todbtn1").disabled=true;
document.getElementById("todbtn1").style.opacity="0.6";
document.getElementById("todbtn1").style.cursor="not-allowed";

}
if(d.getHours()>=11)
{
document.getElementById("todbtn2").disabled=true;
document.getElementById("todbtn2").style.opacity="0.6";
document.getElementById("todbtn2").style.cursor="not-allowed";
}
if(d.getHours()>=14)
{
document.getElementById("todbtn3").disabled=true;
document.getElementById("todbtn3").style.opacity="0.6";
document.getElementById("todbtn3").style.cursor="not-allowed";
}
if(d.getHours()>=15)
{
document.getElementById("todbtn4").disabled=true;
document.getElementById("todbtn4").style.opacity="0.6";
document.getElementById("todbtn4").style.cursor="not-allowed";
}
if(d.getHours()>=18)
{
document.getElementById("todbtn5").disabled=true;
document.getElementById("todbtn5").style.opacity="0.6";
document.getElementById("todbtn5").style.cursor="not-allowed";
}
if(d.getHours()>=19)
{
document.getElementById("todbtn6").disabled=true;
document.getElementById("todbtn6").style.opacity="0.6";
document.getElementById("todbtn6").style.cursor="not-allowed";
}


d.setDate(d.getDate() + 1);
document.getElementById("tomorrowdate").innerHTML = d.getDate()+"-"+month+"-"+d.getFullYear();

</script>

</body>
</html>
