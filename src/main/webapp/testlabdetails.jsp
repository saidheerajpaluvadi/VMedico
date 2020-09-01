<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.example.vmedico.model.TestingLab" %>
<!DOCTYPE html>
<html>

<head>
<meta charset="ISO-8859-1">
<title>Testing Labs |VMedico</title>

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
ArrayList<TestingLab> al =(ArrayList<TestingLab>) request.getAttribute("testlist");
%>

<% if(al.size()==0){ %>
<p style="text-align:center;color:#cc0000;font-size:18px">Sorry, no labs found</p>

<%} else if(al.size()==1){ %>
<p style="text-align:center;color:#cc0000;font-size:18px"><%=al.size() %> Lab found</p>
<%} else{ %>
<p style="text-align:center;color:#cc0000;font-size:18px"><%=al.size() %> Labs found</p>

<%} %>

<%
for(int i=0;i<al.size();i++){  %>
<div style="margin:10px;">
<div class="card">

  <div class="container">
  <form action="booktestlab" method="POST">
    <h4><b>Name:	<%= 	al.get(i).getNameoflab()  %> </b></h4>
    <p>Years of Establishment: <%=  al.get(i).getYearofestablishment() %> </p>
  
 
    <p>Email ID: <%= al.get(i).getEmail() %></p>
     <button id="dmail" style="float: right;" name="testlabmail" value="<%= al.get(i).getEmail() %>" > Book </button>
    <p>Address: <%= al.get(i).getAddress() %></p>
  </form>
  </div>
</div>
</div>
<% } %>
          



</body>
</html>