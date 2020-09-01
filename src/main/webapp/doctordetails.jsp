<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.example.vmedico.model.Doctor" %>
<!DOCTYPE html>
<html>

<head>
<meta charset="ISO-8859-1">
<title>Doctors |VMedico</title>

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
<%
response.setHeader("Cache-Control","no-cache, no-store, must-revalidate");
response.setHeader("Pragma","no-cache");
response.setHeader("Expires", "0");

if(session.getAttribute("patientmail") == null){
	response.sendRedirect("home.jsp");
}
%>
<jsp:include page="patientstab.jsp"/>
<%
@SuppressWarnings("unchecked")
ArrayList<Doctor> al =(ArrayList<Doctor>) request.getAttribute("doclist");
%>
<% if(al.size()==0){ %>
<p style="text-align:center;color:#cc0000;font-size:18px">Sorry, no doctors found</p>

<%} else if(al.size()==1){ %>
<p style="text-align:center;color:#cc0000;font-size:18px"><%=al.size() %> doctor found</p>
<%} else{ %>
<p style="text-align:center;color:#cc0000;font-size:18px"><%=al.size() %> doctors found</p>

<%} %>
<%

for(int i=0;i<al.size();i++){  %>

<div style="margin:10px;">
<div class="card">

  <div class="container">
  <form action="book" method="POST">
    <h4><b>Dr.<%= al.get(i).getFirstname()  %> </b></h4>
    <p><%=  al.get(i).getHighestqualification() %> </p>
    <p><%=  al.get(i).getHospitalname()+" Hospital, " %> <%= al.get(i).getLocation() %> </p>
   
  
  <button style="float: right;" name="doctormail" value="<%= al.get(i).getEmail() %>"  > Book </button>
   <p><%= al.get(i).getSpecialization()+"," %> <%= al.get(i).getYearsofexperience() %> yrs of experience</p>
  </form>
  </div>
</div>
</div>
<% } %>
       

</body>
</html>