<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>



<head>
<meta charset="UTF-8">
<title >Input Form</title>
</head>

<body>
<form name="signupForm" action="./" method="post">
		
  <h2> User Details: </h2>
  
  <label for="fname">First Name:</label><br>
  <input type="text" id="fname" name="fname" minlength="1" maxlength="20" placeholder="John" required><br>
  <label for="lname">Last Name:</label><br>
  <input type="text" id="lname" name="lname" minlength="1" maxlength="20" placeholder="Doe" required><br><br>
  
  <h2> Vehicle Details: </h2>
  
  <label for="carmake">Car Make:</label><br>
  <input type="text" id="carmake" name="carmake" minlength="1" maxlength="20" placeholder="Toyota" required><br>
  
   <label for="carmodel">Car Model:</label><br>
  <input type="text" id="carmodel" name="carmodel" minlength="1" maxlength="20" placeholder="Corolla" required><br>
  
  <label for="carregistration">Vehicle Registration:</label><br>
  <input type="text" id="carregistration" name="carregistration" minlength="10" maxlength="10" placeholder= "ABCD123456" required><br>
  
  
    <h2> Payment Details: </h2>
    
  <a href="https://www.shift4shop.com/credit-card-logos.html"><img alt="Credit Card Logos" title="Credit Card Logos" src="https://www.shift4shop.com/images/credit-card-logos/cc-lg-4.png" width="200" height="32" border="0" /></a><br>
    
  <label for="ccn">Credit Card Number </label><br>
  <input type="text" id="ccn" inputmode="numeric" pattern="[0-9\s]{16,16}" name="ccn" minlength="16" maxlength="16" placeholder="XXXX XXXX XXXX XXXX" required><br>

    
  <label for="cvv">Cvv </label><br>
  <input type="password" id="cvv" name="cvv" minlength="3" maxlength="3" placeholder="123" required><br>
  
  <label>Expiry Date </label><br>
	  <select name='expireMM' id='expireMM' required>
	    <option value=''>Month</option>
	    <option value='01'>January</option>
	    <option value='02'>February</option>
	    <option value='03'>March</option>
	    <option value='04'>April</option>
	    <option value='05'>May</option>
	    <option value='06'>June</option>
	    <option value='07'>July</option>
	    <option value='08'>August</option>
	    <option value='09'>September</option>
	    <option value='10'>October</option>
	    <option value='11'>November</option>
	    <option value='12'>December</option>
	</select> 
	<select name='expireYY' id='expireYY' required>
	    <option value=''>Year</option>
	    <option value='22'>2022</option>
	    <option value='23'>2023</option>
	    <option value='24'>2024</option>
	    <option value='24'>2025</option>
	    <option value='24'>2026</option>
	    <option value='24'>2027</option>
	    
	</select> 
  
  <br>
  <br>
  <input type="submit" value="Submit">
</form> 

</body>
</html>