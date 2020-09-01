<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.example.vmedico.model.DoctorAppointment" %>
<%@ page import="com.example.vmedico.model.Patient" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>New Prescription |VMedico</title>
<style>
form {
    display: inline;
}

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
<th>Gender</th>
<th> Appointment date </th>
<th> Appointment time </th>
<th> Prescription </th>
</tr>
<%
@SuppressWarnings("unchecked")
ArrayList<DoctorAppointment> al = (ArrayList<DoctorAppointment>) request.getAttribute("doctorappointmentstogiveprescriptionnew");
@SuppressWarnings("unchecked")
ArrayList<Patient> dl = (ArrayList<Patient>) request.getAttribute("patientsforprescriptionnew"); 


for(int i=0;i< al.size() ;i++){
	%>
	
	<tr>
	
	<td> <%= dl.get(i).getFirstname() %> </td>
	<td> <%= dl.get(i).getGender() %> </td>
	<td> <%= al.get(i).getAppointmentdate() %> </td>
	<td> <%= al.get(i).getAppointmenttime() %> </td>
	<% System.out.println(al.get(i).getAppointmenttime()); %>
	<!--  -->
	<td><form action="addprescription" method="POST"> <button type="submit" name="appdetails" value=<%= al.get(i).toString() %>>ADD</button> </form>
	<form action="noneedprescription" method="POST"> <button type="submit" name="appdetails" value=<%= al.get(i).toString() %>>No Need a Prescription</button> </form>
	</td>
		
	
	
	</tr>
<% 	
}
	
%>
</table>


</body>
</html>