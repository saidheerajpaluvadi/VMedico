<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.example.vmedico.model.TestingLab" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Edit Profile |VMedico</title>
<script src='https://kit.fontawesome.com/a076d05399.js'></script>
<meta name="viewport" content="width=device-width, initial-scale=1">
<style>
button {
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
button:hover {
  background-color: #cc0000;
  color: white
  }

input{
	font-size:16px;
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
TestingLab tl = (TestingLab)session.getAttribute("testinglabprofileobj");

%>



<form action="edittestlabprofile" method="POST">
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
<td><strong>Address </strong></td><td> <input type="text" name="address" value="<%=tl.getAddress() %>" /></td>
</tr>
<tr >
<td colspan="2" style="text-align:center"><button type="submit">Save</button></td>
</tr>
</table>

</form>


</body>
</html>