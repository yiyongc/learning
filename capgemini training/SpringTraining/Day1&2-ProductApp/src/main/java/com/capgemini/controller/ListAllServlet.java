package com.capgemini.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.capgemini.model.Product;
import com.capgemini.service.IListAllService;
import com.capgemini.service.ListAllServiceImpl;


public class ListAllServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		IListAllService service = new ListAllServiceImpl();

		List<Product> products = service.listAll();
		
		PrintWriter out = response.getWriter();
		out.println("<html>");
		
		//Import stylesheet
		out.println("<link type=\"text/css\" href=\"styles/mainstyle.css\" rel=\"stylesheet\"></link>");	
		
		out.println("<h2 style=\"text-align:center\">List of all products</h2>");
		out.println("<hr>");
				
		out.println("<table style=\"margin: 0 200px 0 200px\">");
		//Headers of table
		out.println("<tr>");
		out.println("<th style=\"padding:10\">Product ID</th>");
		out.println("<th style=\"padding:10\">Product Name</th>");
		out.println("<th style=\"padding:10\">Product Type</th>");
		out.println("<th style=\"padding:10\">Expiry Date</th>");
		out.println("<th style=\"padding:10\">Description</th>");
		out.println("<th style=\"padding:10\">Quantity</th>");
		out.println("<th style=\"padding:10\">Price</th>");
		out.println("<th style=\"padding:10\">Edit</th>");
		out.println("</tr>");
	
		for (Product product : products) {
			out.println("<tr>");
			out.println("<td style=\"padding:10\">" + product.getProductId() + "</td>");
			out.println("<td style=\"padding:10\">" + product.getProductName() + "</td>");
			out.println("<td style=\"padding:10\">" + product.getProductType() + "</td>");
			out.println("<td style=\"padding:10\">" + product.getProductExpiry() + "</td>");
			out.println("<td style=\"padding:10\">" + product.getProductDesc() + "</td>");
			out.println("<td style=\"padding:10\">" + product.getProductQty() + "</td>");
			out.println("<td style=\"padding:10\">" + product.getProductPrice() + "</td>");
			out.println("<td style=\"padding:10\"> <span><a href=\"DeleteServlet?productId=" + product.getProductId() + "\">Delete</a></span><span style=\"margin-left:10px\"><a href=\"UpdateServlet?productId=" + product.getProductId() + "\">Update</a></span> </td>");
			out.println("</tr>");
		}
	
		out.println("</table>");	
		out.println("</html>");
	
	}

}
