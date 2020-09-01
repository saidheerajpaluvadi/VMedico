<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.example.vmedico.model.TestingLabAppointment" %>
<%@ page import="com.example.vmedico.model.TestingLab" %>
<!DOCTYPE html>
<html>

<head>
<meta charset="ISO-8859-1">
<title>Cancel |VMedico</title>

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
<jsp:include page="patientstabfortestlab.jsp"/>
<%
@SuppressWarnings("unchecked")
ArrayList<TestingLab> dal =(ArrayList<TestingLab>) request.getAttribute("Bookedtestlablist");
@SuppressWarnings("unchecked")
ArrayList<TestingLabAppointment> daal =(ArrayList<TestingLabAppointment>) request.getAttribute("testlabappointmentlist");

%>

<%
for(int i=0;i<dal.size();i++){  %>
<div style="margin:10px;">
<div class="card">

  <div class="container">
  <form action="canceltestlab" method="POST">
    <h4><b>Lab Name:	<%= 	dal.get(i).getNameoflab()  %> </b></h4>
    <p>Years of Establishment: <%=  dal.get(i).getYearofestablishment() %> </p>

  <p>Date: <%= daal.get(i).getAppointmentdate() %></p>
		
  <button id="dmail" style="float: right;" name="testlabmailamdappointmnetdetails" value="<%= dal.get(i).getEmail()+"-"+daal.get(i).getAppointmentdate()+"-"+daal.get(i).getAppointmenttime() %>" > Cancel </button>
 <p>Time: <%= daal.get(i).getAppointmenttime()  %>
    </p>   
  </form>
  </div>
</div>
</div>
<% } %>
          

</body>
</html>