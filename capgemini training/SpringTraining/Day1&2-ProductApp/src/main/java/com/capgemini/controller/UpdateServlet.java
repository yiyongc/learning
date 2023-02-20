package com.capgemini.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.capgemini.model.Product;
import com.capgemini.service.IUpdateProductService;
import com.capgemini.service.UpdateProductServiceImpl;


public class UpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	IUpdateProductService service = new UpdateProductServiceImpl();
       
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("productId");
		int productId = Integer.parseInt(id);
		
		Product product = service.getProductForUpdate(productId);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String productType = product.getProductType();
		String date = sdf.format(product.getProductExpiry());
		
		PrintWriter out = response.getWriter();
		
		out.println("<html>");
		out.println("<script type='text/javascript' src='script/populateProductType.js'></script>");
		out.println("<script type='text/javascript' src='script/validation.js'></script>");
		out.println("<link type='text/css' href='styles/mainstyle.css' rel='stylesheet'></link>");
		out.println("<h2>Update product</h2>"
					+ "<hr>"
					+ "<br>"
					+ "<fieldset class='productform'>"
					+ 	"<form method='post' action='UpdateServlet' onsubmit='return validateProductAdd()'>"
					+ 		"<table>"
					+ 			"<tr>"
					+ 				"<td style='padding:10'>Product Name: </td>"
					+ 				"<td style='padding:10'><input type='text' name='productName' id='productName' value='" + product.getProductName() + "'></input></td>"
					+ 				"<td style='padding:10'><div id='productErr' class='errMsg'></div></td>"
					+ 			"</tr>"
					+ 			"<tr>"
					+ 				"<td style='padding:10'>Product Type: </td>"
					+ 				"<td style='padding:10'>"
					+ 				"<select name='producttype' id='producttype'>"
					+ 					"<option value='' hidden>Select Product Type</option>"
					+ 	                "<option value='food'>Food</option>"
					+ 	                "<option value='electronics'>Electronics</option>"
					+ 	                "<option value='tools'>Tools</option>"
					+ 	                "<option value='others'>Others</option>"
					+ 	        	"</select>"
					+ 	        	"</td>"
					+ 			"</tr>"
					+			"<script>('" + productType + "');</script>"
					+ 			"<tr>"
					+ 				"<td style='padding:10'>Expiry Date: </td>"
					+ 				"<td style='padding:10'><input type='date' name='expirydate' id='expirydate' value='" + date + "'></input></td>"
					+ 				"<td style='padding:10'><div id='dateErr' class='errMsg'></div></td>"
					+ 			"</tr>"
					+ 			"<tr>"
					+ 				"<td style='padding:10'>Description: </td>"
					+ 				"<td style='padding:10'>"
					+ 				"<textarea name='description' rows='5' cols='40' maxlength='200'>" + product.getProductDesc() + "</textarea>"
					+ 				"</td>"
					+ 			"</tr>"
					+ 			"<tr>"
					+ 				"<td style='padding:10'>Quantity: </td>"
					+ 				"<td style='padding:10'><input type='number' name='quantity' id='quantity' value='" + Integer.toString(product.getProductQty()) + "'></input></td>"
					+ 				"<td style='padding:10'><div id='quantityErr' class='errMsg'></div></td>"
					+ 			"</tr>"
					+ 			"<tr>"
					+ 				"<td style='padding:10'>Price: &nbsp; $</td>"
					+ 				"<td style='padding:10'><input type='number' name='price' id='price' value='" + Double.toString(product.getProductPrice()) + "'></input></td>"
					+ 				"<td style='padding:10'><div id='priceErr' class='errMsg'></div></td>"
					+ 			"</tr>"
					+ 			"<tr>"
					+ 				"<td style='padding:10'></td>"
					+ 				"<td style='padding:10'>"
					+ 					"<button type='submit'>Save</button>"
					+ 					"<button type='reset'>Clear</button>"
					+ 				"</td>"
					+ 			"</tr>"
					+ 		"</table>"
					+		"<input type='hidden' name='productid' id='productid' value='" + product.getProductId() + "'></input>"
					+ 	"</form>"
					+ "</fieldset>");
		out.println("</html>");
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String productId = request.getParameter("productid");
		String productName = request.getParameter("productName");
		String productType = request.getParameter("producttype");
		String productExpiry = request.getParameter("expirydate");
		String productDesc = request.getParameter("description");
		String productQty = request.getParameter("quantity");
		String productPrice = request.getParameter("price");
		
		//Convert to product object to pass it to service
		Product product = new Product(productName, productType, productExpiry, productDesc, productQty, productPrice);
		product.setProductId(Integer.parseInt(productId));
		
		boolean result = service.updateProduct(product);
	
		
		if(result) {
			request.getRequestDispatcher("pages/successUpdate.html").forward(request, response);
		} else {
			request.getRequestDispatcher("pages/welcomePage.html").forward(request, response);
		}
	
	}

	
	
}
