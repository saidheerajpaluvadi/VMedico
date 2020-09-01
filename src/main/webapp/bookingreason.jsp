<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.example.vmedico.model.DoctorAppointment" %>
<%@ page import="com.example.vmedico.model.Doctor" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Confirmation |VMedico</title>
<style>
label,
textarea {
    font-size: 14px;
    letter-spacing: 1px;
}
textarea {
    padding: 10px;
    line-height: 1.5;
    border-radius: 5px;
    border: 1px solid #ccc;
    width:400px;
    box-shadow: 1px 1px 1px #999;
}

label {
    display: block;
    margin-bottom: 10px;
}


#status {
  font-family: "Trebuchet MS", Arial, Helvetica, sans-serif;
  border-collapse: collapse;
  margin-top:10px;
  margin-bottom:40px;
  margin-left:auto; 
  margin-right:auto;
  width:400px;
}

#status td, #status th {
  border: 1px solid #ddd;
  padding: 8px;
}

#status tr:nth-child(even){background-color: #f2f2f2;}

#status tr:hover {background-color: #ddd;}

#status th {
  padding-top: 12px;
  padding-bottom: 12px;
  text-align: left;
  background-color: #cc0000;
  color: white;
}
.button {
  border: none;
  color: white;
  padding: 16px 32px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 16px;
  margin: 4px 8px;
  transition-duration: 0.4s;
  cursor: pointer;
}


.button1 {
  background-color: white; 
  color: black; 
  border: 2px solid #f44336;
}

.button1:hover {
  background-color: #f44336;
  color: white;
}

.button2 {
  background-color: white; 
  color: black; 
  border: 2px solid #32CD32;
}

.button2:hover {
  background-color: #32CD32;
  color: white;
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
<% DoctorAppointment docapp = (DoctorAppointment) request.getAttribute("doctorappointmentobj"); %>
<% Doctor doctor = (Doctor) request.getAttribute("doctor"); %>
<div style="text-align:center;">
<h3 style="text-align:center;color:Green"> ${doctorbookingmsg} </h3>
<% if(docapp!=null && doctor!=null){ %>
<form action="placearequest" method="POST">
<label for="story" style="font-size:22px;">Reason</label>

<textarea id="story" name="reason"  rows="5" cols="33" placeholder="Write your reason of your appointment...">
</textarea> <br/>

<table id = "status">
<tr>
<th colspan="2" style="text-align:center">
Appointment Details
</th>
</tr>
<tr>
<td><strong>Doctor name</strong></td><td><%=doctor.getFirstname() %></td>
</tr>
<tr>
<td><strong>Hospital Name</strong></td><td><%=doctor.getHospitalname() %></td>
</tr>
<tr>
<td><strong>Location</strong></td><td><%=doctor.getLocation() %></td>
</tr>
<tr>
<td><strong>Appointment date</strong></td><td><%=docapp.getAppointmentdate() %></td>
</tr>
<tr>
<td><strong>Appointment time</strong></td><td><%=docapp.getAppointmenttime() %></td>
</tr>
</table>
<button name="result" value="cancel" type="submit"  class="button button1">Cancel</button>
<button name="result" value="confirm" type="submit" class="button button2">Confirm</button>
</form>
<% } %>
</div>
</body>
</html>