<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.example.vmedico.model.DoctorAppointment" %>
<%@ page import = "com.example.vmedico.model.Patient" %>
<!DOCTYPE html>
<html>

<head>
<meta charset="ISO-8859-1">
<title>Patients |VMedico</title>

<meta name="viewport" content="width=device-width, initial-scale=1">
<style>
.card {
  box-shadow: 0 4px 8px 0 rgba(0,0,0,0.2);
  transition: 0.3s;
  border-radius:8px;
  width: 60%;
  margin: auto;
}

.card:hover {
  box-shadow: 0 8px 16px 0 rgba(0,0,0,0.2);
 
}

.container {
  padding: 2px 16px;
   justify-content:center;

}
</style>
</head>
<body>
<%! String reason = null; %>
<%
response.setHeader("Cache-Control","no-cache, no-store, must-revalidate");
response.setHeader("Pragma","no-cache");
response.setHeader("Expires", "0");

if(session.getAttribute("doctormail") == null){
	response.sendRedirect("home.jsp");
}
%>
<jsp:include page="doctorstab.jsp"/>
<%
@SuppressWarnings("unchecked")
ArrayList<DoctorAppointment> al =(ArrayList<DoctorAppointment>) request.getAttribute("alldoctorappointments");
@SuppressWarnings("unchecked")
ArrayList<Patient> pal = (ArrayList<Patient>) request.getAttribute("patientslist");
%>

<%
for(int i=0;i<al.size();i++){  %>
<div style="margin:10px;">
<div class="card">

  <div class="container">
  <form action="acceptorrejectbydoctor" method="POST">
    <p><b>Name:</b>	<%= 	pal.get(i).getFirstname()  %> </p>
    <p><b>Gender:</b> <%=  pal.get(i).getGender() %> </p>
    <p><b>Date of Birth:</b><%=pal.get(i).getDob() %> </p>
    
  <button id="#" style="float: right;" name="pateintmailandresult" value="<%= "reject-"+al.get(i).getId() %>" > Reject </button>
  
  <button id="#" style="float: right;" name="pateintmailandresult" value="<%= "accept-"+al.get(i).getId() %>" > Accept </button>
   <p><b>Appointment Date:</b> <%=al.get(i).getAppointmentdate() %> <b>Appointment Time:</b><%=al.get(i).getAppointmenttime() %></p>
   <%
   reason = al.get(i).getReason();
   if(reason == null)
	   reason = "Patient didn't gave any reason...";
   %>
    <p><b>Reason :</b> <%=reason %> </p>
  </form>
  </div>
</div>
</div>
<% } %>
          



</body>
</html>