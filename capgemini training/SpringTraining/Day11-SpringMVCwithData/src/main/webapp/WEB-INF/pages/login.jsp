<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style>
.errMsg {
	color:red;
	font-style:italic;
	font-weight:bold;
	font-size: 0.7em;
}
</style>
</head>
<body>

<h1 align="center">Employee Management</h1>
<hr>
<fieldset style="margin:100px 400px 100px 400px">
<legend>Login</legend>

<form:form action="loginForm" method="post" commandName="login">
	<table cellpadding="10px">
		<tr>
			<td>Username: </td>
			<td><form:input path="username"/></td>
			<td><form:errors path="username" cssClass="errMsg"></form:errors></td>
		</tr>
		<tr>
			<td>Password: </td>
			<td><form:password path="password"/></td>
			<td><form:errors path="password" cssClass="errMsg"></form:errors></td>
		</tr>
		<tr>
			<td></td>
			<td>
				<button type="submit">Login</button> &nbsp;&nbsp;&nbsp;
				<button type="reset">Clear</button>
			</td>
		</tr>
	</table>
</form:form>

</fieldset>
<hr>
</body>
</html>
