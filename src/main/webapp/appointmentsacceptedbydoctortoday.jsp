<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.example.vmedico.model.DoctorAppointment" %>
<%@ page import="com.example.vmedico.model.Patient" %>
<%@ page import="com.example.vmedico.dao.PatientFileRepo" %>
<%@ page import="com.example.vmedico.model.PatientFiles" %>
<%@ page import="com.example.vmedico.model.TestingLabAppointment" %>
<%@ page import="com.example.vmedico.dao.TestLabAppointmentRepo" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Today Appointments |VMedico</title>
<style>
.popuppatient{
background: rgba(0,0,0,0.6);
width:100%;
height:100%;
position:absolute;
top:0;
display:none;
justify-content:center;
align-items:center;
text-align: center;
}

.popup-content{
 height:400px;
 width: 600px;
 background:#fff;
 padding:20px;
 border-radius:5px;
 position:relative;
 overflow: scroll;
}

input{
 margin:20px auto;
 display: block;
 width:50%;
 padding:8px;
 border: 1px solid gray;
}

.myclass{
 background-color: #cc0000;
  border: none;
  color: white;
  padding: 10px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 16px;
  margin: 4px 2px;
  cursor: pointer;
  border-radius:8px;
}

.closepatient {
	position: absolute;
	top: 0;
	right: 10px;
	font-size: 42px;
	color: #333;
	transform: rotate(45deg);
	cursor: pointer;
	&:hover {
		color: #666;
	}
}

.heading{
color:#cc0000;
font-size:22px
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
.patient{
 color:#cc0000;
text-decoration:underline;
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
<%! String patientmail = null; %>
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

@SuppressWarnings("unchecked")
ArrayList<DoctorAppointment> al = (ArrayList<DoctorAppointment>) request.getAttribute("doctorappointmentsacceptedtoday");
@SuppressWarnings("unchecked")
ArrayList<Patient> dl = (ArrayList<Patient>) request.getAttribute("acceptedpatientslisttoday");
PatientFileRepo pfrepo = (PatientFileRepo)request.getAttribute("patientfilesrepo");
TestLabAppointmentRepo tlarepo = (TestLabAppointmentRepo) request.getAttribute("testinglabappointmentrepo");



for(int i=0;i< al.size() ;i++){
	%>
	<tr>
	<td> <label class="patient" onclick="fun(<%=i %>)"> <%= dl.get(i).getFirstname() %></label>
	</td>
	<td> <%= dl.get(i).getGender() %> </td>
	<td> <%= al.get(i).getAppointmentdate() %> </td>
	<td> <%= al.get(i).getAppointmenttime() %> </td>
	<td> <%= al.get(i).getStatus() %></td>
	<%
	if(dl.get(i).getMedicalhistory() == null || dl.get(i).getMedicalhistory() == ""){
		dl.get(i).setMedicalhistory("No data available");
	}
	%>
	</tr>
	<div class = "popuppatient">
			<div class = "popup-content">
				<div class="closepatient" onclick="fun2(<%=i %>)">+</div>
	
				<label class="heading">Patient Details</label><br/>
				 <p><b>Name :</b> <%=dl.get(i).getFirstname() %> </p>
				 <p><b>Gender :</b> <%= dl.get(i).getGender() %></p>
				 <p><b>Date of Birth:</b> <%= dl.get(i).getDob() %></p>
				 <p><b>Email ID:</b><%= dl.get(i).getEmail() %></p>
				 <p><b>Medical History:</b> <%=dl.get(i).getMedicalhistory() %> <br/>
				 <p><b>Reason :</b> <%= al.get(i).getReason() %></p>
                 <label class="heading">Medical Reports</label><br/>
                 
                 <% 
                 patientmail = dl.get(i).getEmail();
                 ArrayList<PatientFiles> fileslist = pfrepo.getFiles(patientmail);
                 System.out.println("files list size "+fileslist.size());
                 System.out.println(patientmail);
                 if(fileslist.size()==0){
                	 %>
                	<h3>No data Available</h3> 
                <% }
                
                 for(int j=0;j<fileslist.size();j++){
         
                 %>
                
			<p><b><%=fileslist.get(j).getPatientfilename() %></b>
	<a download href="downloadPatientFiles/<%=fileslist.get(j).getId() %>"> Download </a></p>  
<% 	
}
	
%>
				<label class="heading">Testing Lab Reports</label><br/>
				<%
					ArrayList<TestingLabAppointment> tllist = tlarepo.findAllAppointmentsOfPatients(dl.get(i).getEmail(), "booked");
					if(tllist.size()==0){
						%>
						<h3>No data Available</h3> 
				<% 	}
				for(TestingLabAppointment tla : tllist){
						if(tla.getReportname()!=null){
						%>
						<p><b><%=tla.getReportname() %></b>
						<a download href="downloadPatientReports/<%=tla.getId() %>"> Download </a></p>  
					 <% 
						}
					}
				%>


				 
			</div>
	</div>
	
<% 	
}
	
%>

</table>
<script>
function fun(i){
	document.getElementsByClassName("popuppatient")[i].style.display = "flex";
}
function fun2(i){
	document.getElementsByClassName("popuppatient")[i].style.display = "none";
}

</script>

</body>
</html>