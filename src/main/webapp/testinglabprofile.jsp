<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.example.vmedico.model.TestingLab" %>
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

if(session.getAttribute("testinglabmail") == null){
	response.sendRedirect("home.jsp");
}
%>
<jsp:include page="testlabtab.jsp"/>

<%
TestingLab tl = (TestingLab)request.getAttribute("testinglabprofileobj");
session.setAttribute("testinglabprofileobj", tl);
%>




<table id = "status">
<tr>
<th colspan="2" style="text-align:center">Profile</th>
</tr>
<tr>
<td><strong>Name of Laboratory</strong></td><td><%= tl.getNameoflab() %></td>
</tr>
<tr>
<td><strong>Email</strong></td><td><%=tl.getEmail() %></td>
</tr>
<tr>
<td><strong>Year of establishment</strong></td><td><%=tl.getYearofestablishment() %></td>
</tr>
<tr>
<td><strong>Address </strong></td><td><%=tl.getAddress() %></td>
</tr>
<tr>
<td style ="color:#cc0000;text-decoration:underline;"><form action="testinglabprofileedit.jsp"><button id="edit" type="submit"><i class='far fa-edit' style="font-size:18px;"></i> Edit Profile</button></form></td>
</tr>
</table>




</body>
</html>