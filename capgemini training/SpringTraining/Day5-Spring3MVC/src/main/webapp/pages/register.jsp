<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Registration Page</title>
</head>
<body>
	<h2 style="text-align:center">Register</h2>
	<hr>
<form:form commandName="registerAccount" action="saveRegistration" method="post">
<table align="center" cellpadding="10px">
	<tr>
		<td>First Name: </td>
		<td><form:input path="firstName"/></td>
	</tr>
	<tr>
		<td>Last Name: </td>
		<td><form:input path="lastName"/></td>
	</tr>
	<tr>
		<td>Date of Birth: </td>
		<td><form:input path="dateOfBirth"/></td>
	</tr>
	<tr>
		<td>Address: </td>	
		<td><form:textarea path="address"/></td>
	</tr>
	<tr>
		<td>Occupation: </td>
		<td>
			<form:select path="occupation">
				<form:option value="public">Public</form:option>
				<form:option value="govt">Government</form:option>
				<form:option value="private">Private</form:option>
				<form:option value="other">Other</form:option>
			</form:select>
		</td>
	</tr>
	<tr>
		<td>Gender: </td> 
		<td>
			<form:radiobutton path="gender" value="male"/>Male
			<form:radiobutton path="gender" value="female"/>Female	
		</td>
	</tr>
	<tr>
		<td>Email: </td>
		<td><form:input path="email"/></td>
	</tr>
	<tr>
		<td>Mobile: </td>
		<td><form:input path="mobile"/></td>
	</tr>
	<tr>
		<td></td>
		<td>
			<button type="submit">Register</button> &nbsp;
			<button type="reset">Clear</button>
		</td>
	</tr>
</table>
</form:form>	
</body>
</html>