<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.capgemini.model.Product"%>
<%@page import="java.util.List"%>
<%@page import="com.capgemini.service.ListAllServiceImpl"%>
<%@page import="com.capgemini.service.IListAllService"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>JSP List</title>
</head>
<body>
<%!
	private IListAllService service = new ListAllServiceImpl();
	List<Product> products = service.listAll();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
%>

<h1>List of Products</h1>
<table>
	<tr>
		<th>Product Id</th>
		<th>Product Name</th>
		<th>Product Type</th>
		<th>Expiry</th>
		<th>Description</th>
		<th>Quantity</th>
		<th>Price</th>
	</tr>
	
	<%
		for (Product product : products) {
			String date = sdf.format(product.getProductExpiry());
	%>
	<tr>
		<td><% out.println(product.getProductId()); %></td>
		<td><% out.println(product.getProductName());%></td>
		<td><%= product.getProductType() %></td>
		<td><%= date %></td>
		<td><%= product.getProductDesc() %></td>
		<td><%= product.getProductQty() %></td>
		<td><%= product.getProductPrice() %></td>
	</tr>
	<%
		}
	%>
</table>

</body>
</html>