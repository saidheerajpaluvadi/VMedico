<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.example.vmedico.model.Patient" %>   
<%@ page import="com.example.vmedico.model.PatientFiles" %> 
<%@ page import="java.util.ArrayList" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Profile |VMedico</title>
<script src='https://kit.fontawesome.com/a076d05399.js'></script>
<meta name="viewport" content="width=device-width, initial-scale=1">
<style>

#edit {
  background-color: white;
  border: 1px solid #cc0000;
  color: #cc0000;
  padding: 10px 32px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  margin: 4px 2px;
  cursor: pointer;
  font-size: 14px;
  border-radius:5px;
  transition-duration: 0.4s;
}
#edit:hover {
  background-color: #cc0000;
  color: white
  }

label {
    display: block;
    margin-bottom: 10px;
}


#status {
 
  border-collapse: collapse;
  margin-left:auto; 
  margin-right:auto;
  
}

#status td, #status th {
 
  padding:15px;
}



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

if(session.getAttribute("patientmail") == null){
	response.sendRedirect("home.jsp");
}
%>
<jsp:include page="patientstabfortestlab.jsp"/>

<%
Patient p = (Patient)request.getAttribute("patientprofileobj");
session.setAttribute("patientprofileobj", p);

@SuppressWarnings("unchecked")
ArrayList<PatientFiles> al = (ArrayList<PatientFiles>) request.getAttribute("patientfiles");

if(p.getLastname().equals(""))
	p.setLastname("-----------");
%>



<%!String medicalhistory=null;  %>

<%
medicalhistory = p.getMedicalhistory();
if(medicalhistory == null || medicalhistory.equals("") ){
	medicalhistory = "----------";
}
%>
<table id = "status">
<tr>
<th colspan="2" style="text-align:center">Profile</th>
</tr>
<tr>
<td><strong>First Name</strong></td><td><%= p.getFirstname() %></td>
</tr>
<tr>
<td><strong>Last Name</strong></td><td><%=p.getLastname() %></td>
</tr>
<tr>
<td><strong>Gender</strong></td><td><%=p.getGender() %></td>
</tr>
<tr>
<td><strong>Email </strong></td><td><%=p.getEmail() %></td>
</tr>
<tr>
<td><strong>Date of Birth </strong></td><td><%=p.getDob() %></td>
</tr>

<tr>
<td><strong>Number of All Bookings with doctors </strong></td><td><%=request.getAttribute("noofbookingswithdoctor") %></td>
</tr>
<tr>
<td><strong>Number of All Bookings with TestingLabs  </strong></td><td><%=request.getAttribute("noofbookingswithtestlab") %></td>
</tr>
<tr >
<td><strong> Medical History</strong></td><td colspan="2"> <%=medicalhistory %></td>
</tr>

<tr>
<td style ="color:#cc0000;text-decoration:underline;"><form action="patientprofileeditfortestlab.jsp"><button id="edit" type="submit"><i class='far fa-edit' style="font-size:18px;"></i> Edit Profile</button></form></td>
</tr>

<tr>
<th colspan="2" style="text-align:center">Medical Reports</th>
</tr>
<% 
for(int i=0;i<al.size();i++ ){
%>
<tr>
<td ><%=al.get(i).getPatientfilename() %></td>
<td ><form action="deletefile" method="POST"><a download href="downloadPatientFiles/<%=al.get(i).getId() %>"> Download </a>  <button type="submit" style="margin-left:20px;" name="fileID" value="<%=al.get(i).getId()%>"> Delete </button></form> 
</td>
</tr>
<%} %>


<tr>
<td colspan="2" style="text-align:center"><a href="patientfilesupload.jsp">Add a file</a></td>
</tr>

</table>



</body>
</html>