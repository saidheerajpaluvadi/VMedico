<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.example.vmedico.model.DoctorAppointment" %>
<%@ page import="com.example.vmedico.model.Doctor" %>
<%@ page import="com.example.vmedico.model.Patient" %>
<%@ page import="com.example.vmedico.model.PrescriptionRemainder" %>
<%@ page import="java.text.SimpleDateFormat" %>  
<%@ page import="java.text.Format" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Calendar" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Prescription |VMedico</title>


<style>
.popup{
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
 height:300px;
 width: 500px;
 background:#fff;
 padding:20px;
 border-radius:5px;
 position:relative;
}
input{
 margin:20px auto;
 display: block;
 width:150px;
 padding:8px;
 border: 1px solid gray;
}
#close {
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

#remainder {
  font-family: arial, sans-serif;
  border-collapse: collapse;
   margin-left:auto; 
    margin-right:auto;
  
}

#remainder td,#remainder th {
  text-align: left;
  padding: 8px;
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

#status tr:hover {background-color: #ddd;}

#status th {
  padding-top: 12px;
  padding-bottom: 12px;
  text-align: left;
  background-color: #cc0000;
  color: white;
}
button{
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
</style>

</head>
<body>
<%! float daysBetween = 0.0f;
	String enddate = "";
%>
<%
response.setHeader("Cache-Control","no-cache, no-store, must-revalidate");
response.setHeader("Pragma","no-cache");
response.setHeader("Expires", "0");

if(session.getAttribute("patientmail") == null){
	response.sendRedirect("home.jsp");
}
Patient p = (Patient) request.getAttribute("patientobj");
%>
<jsp:include page="patientstab.jsp"></jsp:include>

<p style="color:#cc0000;font-size:22px;text-align:center;">Remainder For Prescription</p>


<%
ArrayList<PrescriptionRemainder> premlist = (ArrayList<PrescriptionRemainder>)request.getAttribute("prescriptionremainder");
if(premlist!=null && ! premlist.isEmpty()){

%>
<table id="remainder">
<tr>
<th>Start Date</th>
<th>End Date</th>
<th>Remainder Time </th>
<th>Days left</th>
</tr>

<%
for(int i=0;i<premlist.size();i++){ %>
<tr>
<td><%=premlist.get(i).getStartdate() %></td>

<%
try{
SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy");
Calendar c = Calendar.getInstance();
c.setTime(sdf.parse(premlist.get(i).getStartdate()));
c.add(Calendar.DAY_OF_MONTH, premlist.get(i).getNoofdays());
 enddate = sdf.format(c.getTime());

Date date1;
Format formatter;
Calendar calendar = Calendar.getInstance();

date1 = calendar.getTime();
formatter = new SimpleDateFormat("dd/MMM/yyyy");
String todaydate = formatter.format(date1);

Date today = sdf.parse(todaydate);
Date end = sdf.parse(enddate);
long difference = end.getTime() - today.getTime();
 daysBetween = (difference / (1000*60*60*24));
 
 if(daysBetween < 0)
	 daysBetween = 0;
}
catch(Exception e){
	e.printStackTrace();
}
%>
<td><%=enddate %></td>
<td><%=premlist.get(i).getRemaindertime() %>
<td><%=daysBetween %></td>
<td><form action="deletearemainder" > <button type="submit" name="premobj" value=<%=premlist.get(i).getId() %>> Delete</button></form> </td>
</tr>
<%} %>
</table>

<%
}
%>

<p id="addaremainder" style="color:blue;font-size:18px;text-align:center;cursor:pointer;">Add a remainder</p>

<div class = "popup">
	<div class = "popup-content">
	<div id="close">+</div>
	<form action="addaremainder" method="POST"> 
	<label style="color:#cc0000;font-size:22px;">Remainder</label>
	<table id="remainder" style="margin-top:20px;margin-bottom:10px;">
    <tr>
    <th>Time </th>
    <td> <input type="time" id="appt" name="remaindertime">
    </tr>
    <tr>
    <th>Number of days</th>
    <td> <input type="number" name="noofdays">
    </tr>
    </table>
    <button type ="submit">Submit</button>
	</form>
	
	</div>
	
</div>
<script>
document.getElementById('addaremainder').addEventListener("click", function() {
	
	document.querySelector('.popup').style.display = "flex";
});

document.getElementById('close').addEventListener("click", function() {
	document.querySelector('.popup').style.display = "none";
	
});
</script>


<table id = "status">

<tr>
<th>Doctor Name </th>
<th>Specialization</th>
<th>Hospital Name</th>
<th> Appointment date </th>
<th> Appointment time </th>
<th> Prescription </th>
</tr>
<%
@SuppressWarnings("unchecked")
ArrayList<DoctorAppointment> al = (ArrayList<DoctorAppointment>) request.getAttribute("doctorappointmentstotakeprescription");
@SuppressWarnings("unchecked")
ArrayList<Doctor> dl = (ArrayList<Doctor>) request.getAttribute("doctorsgivenprescription"); 


for(int i=0;i< al.size() ;i++){
	%>
	
	<tr>
	
	<td> <%= dl.get(i).getFirstname() %> </td>
	<td> <%= dl.get(i).getSpecialization() %> </td>
		<td> <%= dl.get(i).getHospitalname() %> </td>
	<td> <%= al.get(i).getAppointmentdate() %> </td>
	<td> <%= al.get(i).getAppointmenttime() %> </td>
	
	<td><a download href="downloadFile/<%=al.get(i).getId() %>"  > Download</a></td>
	</tr>
<% 	
}
	
%>
</table>



</body>
</html>