<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<title>Home |VMedico </title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css">
 <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">


<style>
body, html {
  height: 100%;
  margin: 0;
  overflow-y: hidden; 
}

.bg {
  /* The image used */
  background-image: url("virtual-health.jpg");

  /* Full height */
  height: 100%; 

  /* Center and scale the image nicely */
  background-position: center;
  background-repeat: no-repeat;
  background-size: cover;
}

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
.popupdoctor{
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
.popuptestlab{
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
 height:380px;
 width: 500px;
 background:#fff;
 padding:20px;
 border-radius:5px;
 position:relative;
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
.close{
	position: absolute;
	top: 60px;
	right: 10px;
	font-size: 42px;
	color: #cc0000;
	transform: rotate(45deg);
	cursor: pointer;
	&:hover {
		color: #666;
	}
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
.closedoctor {
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
.closetestlab {
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
.companyname{
color:#cc0000;
font-size:22px
}

.topright {
  position: absolute;
  top: 64px;
  right: 16px;
  font-size: 18px;
 
  color:#cc0000;
  padding:10px;
  font-weight:bold;
}
.about-section {
  padding: 50px;
  text-align: center;
  background-color: #474e5d;
  color: white;
  display:none;
}

</style>

</head>
<body>
<% 
response.setHeader("Cache-Control","no-cache, no-store, must-revalidate");

response.setHeader("Pragma","no-cache");
response.setHeader("Expires", "0");
%>
  <nav class="#d32f2f red darken-2">
    <div class="nav-wrapper">
      <a href="  " class="brand-logo" style="margin-left:30px;">VMedico</a>
      <ul id="nav-mobile" class="right hide-on-med-and-down" style="margin-right:30px;">
        <li><a href="#" id="about-section">About US</a></li>
        <li><a href="#" id="patient">Login As Patient</a></li>
        <li><a href="#" id="doctor">Login As Doctor</a></li>
        <li><a href="#" id="testlaboratory">Login As Test laboratory</a></li>
      </ul>
    </div>
  </nav>
  <div class="bg">
  <div class="topright">${errormsgpatient}</div>
   <div class="topright">${errormsgdoctor}</div>
    <div class="topright">${errormsgtestlab}</div>
    <div class="about-section" >
    <div class="close">+</div>
  <h1>VMedico</h1><br/>
  <p>VMedico is an online medical services company recently established by a pharmaceutical company to
enable not only tele-consulting services integrating doctor and patients but also to integrate laboratory
reports and to monitor the health of patients.</p>

</div>
  </div>
  
 
        
<div class = "popuppatient">
	<div class = "popup-content">
	<div class="closepatient">+</div>
	<form action="loginpatient" method="POST"> 
	<i class="medium material-icons">person</i><br>
	
	<label class="companyname">Patient Login</label>
	<input type = "text" placeholder="Email ID" name="email"/>
	<input type = "password" placeholder="Password" name="password"/><br>
	${errormsgpatient} <br>
	<button class="myclass"  type ="submit">Login</button>
	</form>
	<a href="patientsignup" ><button class="myclass">Signup</button></a>
	
	
	
	</div>
	
</div>
       
<div class = "popupdoctor">
	<div class = "popup-content">
	<div class="closedoctor">+</div>
	<form action="logindoctor" method="POST"> 
	<i class="medium material-icons">person</i><br>
	
	<label class="companyname">Doctor Login</label>
	<input type = "text" placeholder="Email ID" name="email"/>
	<input type = "password" placeholder="Password" name="password"/><br>
	${errormsgdoctor} <br>
	<button class="myclass"  type ="submit">Login</button>
	</form>
	<a href="doctorsignup" ><button class="myclass">Signup</button></a>
	
	
	
	</div>
	
</div>
       
<div class = "popuptestlab">
	<div class = "popup-content">
	<div class="closetestlab">+</div>
	<form action="logintestlab" method="POST"> 
	<i class="medium material-icons">person</i><br>
	
	<label class="companyname">Testing Laboratory Login</label>
	<input type = "text" placeholder="Email ID" name="email"/>
	<input type = "password" placeholder="Password" name="password"/><br>
	${errormsgtestlab} <br>
	<button class="myclass"  type ="submit">Login</button>
	</form>
	<a href="testinglabsignup" ><button class="myclass">Signup</button></a>
	
	</div>
	
</div>

<script>
document.getElementById('about-section').addEventListener("click", function() {
	
	document.querySelector('.about-section').style.display = "flex";
});
document.getElementById('patient').addEventListener("click", function() {
	
	document.querySelector('.popuppatient').style.display = "flex";
});
document.getElementById('doctor').addEventListener("click", function() {
	
	document.querySelector('.popupdoctor').style.display = "flex";
});
document.getElementById('testlaboratory').addEventListener("click", function() {
	
	document.querySelector('.popuptestlab').style.display = "flex";
});

document.querySelector('.closepatient').addEventListener("click", function() {
	document.querySelector('.popuppatient').style.display = "none";
	
});

document.querySelector('.closedoctor').addEventListener("click", function() {
	document.querySelector('.popupdoctor').style.display = "none";
	
});
document.querySelector('.closetestlab').addEventListener("click", function() {
	document.querySelector('.popuptestlab').style.display = "none";
	
});

document.querySelector('.close').addEventListener("click", function() {
	document.querySelector('.about-section').style.display = "none";
	
});
	
</script>


</body>
</html>
