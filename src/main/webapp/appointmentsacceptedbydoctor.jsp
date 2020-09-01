<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.example.vmedico.model.DoctorAppointment" %>
<%@ page import="com.example.vmedico.model.Patient" %>


<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>All Appointments |VMedico</title>
<style>

#status {
  font-family: "Trebuchet MS", Arial, Helvetica, sans-serif;
  border-collapse: collapse;
  margin-left:auto; 
    margin-right:auto;
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
</style>

</head>
<body>
<%
response.setHeader("Cache-Control","no-cache, no-store, must-revalidate");
response.setHeader("Pragma","no-cache");
response.setHeader("Expires", "0");

if(session.getAttribute("doctormail") == null){
	response.sendRedirect("home.jsp");
}
%>
<jsp:include page="doctorstab.jsp"/>

<table id = "status">

<tr>
<th>Patient Name </th>
<th>Gender </th>
<th> Appointment date </th>
<th> Appointment time </th>
<th> Status </th>
</tr>
<%

ArrayList<DoctorAppointment> al = (ArrayList<DoctorAppointment>) request.getAttribute("doctorappointmentsaccepted");

ArrayList<Patient> dl = (ArrayList<Patient>) request.getAttribute("acceptedpatientslist"); 

if(al==null)
	System.out.print("al is null");
else if(dl == null)
	System.out.print("dl is null");

for(int i=0;i< al.size() ;i++){
	%>
	<tr>
	<td> <%= dl.get(i).getFirstname() %> </td>
	<td> <%= dl.get(i).getGender() %> </td>
	<td> <%= al.get(i).getAppointmentdate() %> </td>
	<td> <%= al.get(i).getAppointmenttime() %> </td>
	<td> <%= al.get(i).getStatus() %></td>
	</tr>
<% 	
}
	
%>
</table>
</body>
</html>