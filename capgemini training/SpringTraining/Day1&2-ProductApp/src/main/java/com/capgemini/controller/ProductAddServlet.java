package com.capgemini.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.capgemini.model.Product;
import com.capgemini.service.IProductAddService;
import com.capgemini.service.ProductAddServiceImpl;


public class ProductAddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean result;
		
		String productName = request.getParameter("productName");
		String productType = request.getParameter("producttype");
		String productExpiry = request.getParameter("expirydate");
		String productDesc = request.getParameter("description");
		String productQty = request.getParameter("quantity");
		String productPrice = request.getParameter("price");
		
		//Convert to product object to pass it to service
		Product product = new Product(productName, productType, productExpiry, productDesc, productQty, productPrice);
		
		IProductAddService service = new ProductAddServiceImpl();
		result = service.saveProduct(product);
		
		if(result) {
			//response.sendRedirect("pages/success.html");
			request.getRequestDispatcher("pages/success.html").forward(request, response);
		} else {
			//response.sendRedirect("pages/success.html");
			request.getRequestDispatcher("pages/createProduct.html").forward(request, response);
		}
	}

}
