<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<title>Sign up |VMedico</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css">

    <!-- Compiled and minified JavaScript -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/js/materialize.min.js"></script>
            
<style>

.signup{
width:100%;
height:100%;
position:absolute;
top:0;
display:flex;
justify-content:center;

text-align: center;
}
.signup-content{
 height:600px;
 width: 500px;
 background:#fff;
 padding:20px;
 border-radius:5px;
 position:relative;
 
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


}
</style>

</head>
<body>

<div class = "signup">
	<div class = "signup-content">
	<div class="#d32f2f red darken-2" style="border-radius:5px;">
	<label style="color:white;font-size:22px">Doctor Details</label>
	</div>
	<div class="row">
    <form class="col s12" action="newdoctor" method="POST">
      <div class="row">
        <div class="input-field col s6">
          <input id="first_name" type="text" class="validate" name="firstname">
          <label for="first_name">First Name</label>
        </div>
        <div class="input-field col s6">
          <input id="last_name" type="text" class="validate" name="lastname">
          <label for="last_name">Last Name</label>
        </div>
      </div>
      <div class="row">
        <div class="input-field col s12" >
         <input type="text"  name="highestqualification">
          <label>Highest Qualification </label>
        </div>
      </div>
	<div class="row">
        <div class="input-field col s12" >
         <input type="text"  name="hospitalname">
          <label>Name of the Hospital</label>
        </div>
      </div>
      <div class="row">
        <div class="input-field col s12" >
         <input type="text"  name="location">
          <label>Location </label>
        </div>
      </div>
      	
	  
	 <div class="row">
       <label style="font-size:15px; align:right">Specialization</label>
  <select class="browser-default" name="specialization">
    <option value="" disabled selected>Choose your option</option>
    <option value="Cardiologist">Cardiologist</option>
    <option value="Dentist">Dentist</option>
    <option value="ENT specialist">ENT specialist</option>
    <option value="Gynaecologist">Gynaecologist</option>
    <option value="Orthopaedic surgeon">Orthopaedic surgeon</option>
    <option value="Paediatrician">Paediatrician</option>
    <option value="Psychiatrists">Psychiatrists</option>
    <option value="Neurologist">Neurologist</option>
  </select>
  
      </div>
	  <div class="row">
        <div class="input-field col s12">
         <input type="number" name=yearsofexperience >
          <label>Years of experience</label>
        </div>
      </div>
	 

    <div class="row">
        <div class="input-field col s12">
          <input id="email" type="email" class="validate" name="email">
          <label for="email">Email</label>
        </div>
      </div>
      <div class="row">
        <div class="input-field col s6">
          <input id="password" type="password" class="validate" name="pwd">
          <label for="password">Password</label>
        </div>
		 <div class="input-field col s6">
          <input id="password" type="password" class="validate" name="cpwd">
          <label for="password">Confirm Password</label>
        </div>
      </div>
      ${errormsg} <br>
    <button class="myclass" type = "submit">Submit</button>
    </form>
  </div>
	
	
	</div>
	
</div>



</body>

</html>
