package com.capgemini.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.capgemini.service.DeleteServiceImpl;
import com.capgemini.service.IDeleteService;


public class DeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("productId");
		int productId = Integer.parseInt(id);
		
		IDeleteService service = new DeleteServiceImpl();
		
		service.deleteProduct(productId);
		
		request.getRequestDispatcher("ListAllServlet").forward(request, response);
	}

	

}
