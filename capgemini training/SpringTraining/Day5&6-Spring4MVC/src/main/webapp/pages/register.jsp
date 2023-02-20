<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Registration Page</title>
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
	<h2 style="text-align:center">Register</h2>
	<hr>
<c:if test="${editFlag eq false}">
	<form:form commandName="registerAccount" action="saveRegistration" method="post">
	<table align="center" cellpadding="10px">
		<tr>
			<td>First Name: </td>
			<td><form:input path="firstName"/></td>
			<td><form:errors path="firstName" cssClass="errMsg"></form:errors></td>
		</tr>
		<tr>
			<td>Last Name: </td>
			<td><form:input path="lastName"/></td>
			<td><form:errors path="lastName" cssClass="errMsg"></form:errors></td>
		</tr>
		<tr>
			<td>Date of Birth: </td>
			<td><form:input path="dateOfBirth"/></td>
			<td><form:errors path="dateOfBirth" cssClass="errMsg"></form:errors></td>
		</tr>
		<tr>
			<td>Address: </td>	
			<td><form:textarea path="address"/></td>
		</tr>
		<tr>
			<td>Occupation: </td>
			<td>
				
				<form:select path="occupation">
					<%-- <form:option value="public">Public</form:option>
					<form:option value="govt">Government</form:option>
					<form:option value="private">Private</form:option>
					<form:option value="other">Other</form:option> --%>
					<c:if test="${!empty occupations }">
						<c:forEach items="${occupations}" var="occupation">
							<form:option value="${occupation}">${occupation}</form:option>
						</c:forEach>
					</c:if>
				</form:select>
			</td>
		</tr>
		<tr>
			<td>Gender: </td> 
			<td>
				<form:radiobutton path="gender" value="male"/>Male
				<form:radiobutton path="gender" value="female"/>Female	
			</td>
			<td><form:errors path="gender" cssClass="errMsg"></form:errors></td>
		</tr>
		<tr>
			<td>Email: </td>
			<td><form:input path="email"/></td>
			<td><form:errors path="email" cssClass="errMsg"></form:errors></td>
		</tr>
		<tr>
			<td>Mobile: </td>
			<td><form:input path="mobile"/></td>
			<td><form:errors path="mobile" cssClass="errMsg"></form:errors></td>
		</tr>
		<tr>
			<td></td>
			<td>
				<button type="submit" value="register">Register</button> &nbsp;
				<button type="reset">Clear</button>
			</td>
		</tr>
	</table>
	</form:form>
</c:if>

<c:if test="${editFlag ne false}">
	<form:form commandName="editAcc" action="updateAccount" method="post">
	<table align="center" cellpadding="10px">
		<tr>
			<td>First Name: </td>
			<td><form:input path="firstName"/></td>
			<td><form:errors path="firstName" cssClass="errMsg"></form:errors></td>
		</tr>
		<tr>
			<td>Last Name: </td>
			<td><form:input path="lastName"/></td>
			<td><form:errors path="lastName" cssClass="errMsg"></form:errors></td>
		</tr>
		<tr>
			<td>Date of Birth: </td>
			<td><form:input path="dateOfBirth"/></td>
			<td><form:errors path="dateOfBirth" cssClass="errMsg"></form:errors></td>
		</tr>
		<tr>
			<td>Address: </td>	
			<td><form:textarea path="address"/></td>
		</tr>
		<tr>
			<td>Occupation: </td>
			<td>
				
				<form:select path="occupation">
					<%-- <form:option value="public">Public</form:option>
					<form:option value="govt">Government</form:option>
					<form:option value="private">Private</form:option>
					<form:option value="other">Other</form:option> --%>
					<c:if test="${!empty occupations }">
						<c:forEach items="${occupations}" var="occupation">
							<form:option value="${occupation}">${occupation}</form:option>
						</c:forEach>
					</c:if>
				</form:select>
			</td>
		</tr>
		<tr>
			<td>Gender: </td> 
			<td>
				<form:radiobutton path="gender" value="male"/>Male
				<form:radiobutton path="gender" value="female"/>Female	
			</td>
			<td><form:errors path="gender" cssClass="errMsg"></form:errors></td>
		</tr>
		<tr>
			<td>Email: </td>
			<td><form:input path="email"/></td>
			<td><form:errors path="email" cssClass="errMsg"></form:errors></td>
		</tr>
		<tr>
			<td>Mobile: </td>
			<td><form:input path="mobile"/></td>
			<td><form:errors path="mobile" cssClass="errMsg"></form:errors></td>
		</tr>
		<tr>
			<td><form:hidden path="accId"></form:hidden></td>
		</tr>
		<tr>
			<td></td>
			<td>
				<button type="submit" value="register">Update</button> &nbsp;
				<button type="reset">Clear</button>
			</td>
		</tr>
	</table>
	</form:form>
</c:if>

<hr>
<c:if test="${!empty accounts }">
	<table align="center" cellpadding="5" border="1">
		<tr>
			<th>Account Id</th>
			<th>First Name</th>
			<th>Last Name</th>
			<th>Date of Birth</th>
			<th>Address</th>
			<th>Occupation</th>
			<th>Gender</th>
			<th>Email</th>
			<th>Mobile</th>
			<th>Edit</th>
		</tr>
		<c:forEach items="${accounts}" var="account">
			<tr>
				<td>${account.accId}</td>
				<td>${account.firstName}</td>
				<td>${account.lastName}</td>
				<td>${account.dateOfBirth}</td>
				<td>${account.address}</td>
				<td>${account.occupation}</td>
				<td>${account.gender}</td>
				<td>${account.email}</td>
				<td>${account.mobile}</td>
				<td> <a href="edit/${account.accId}">Edit</a> &nbsp;&nbsp;&nbsp;&nbsp; <a href="delete/${account.accId}">Delete</a>
			</tr>
		</c:forEach>			
	</table>
</c:if>	
</body>
</html>