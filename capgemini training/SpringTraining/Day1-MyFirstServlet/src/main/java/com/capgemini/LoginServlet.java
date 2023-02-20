package com.capgemini;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		String user = request.getParameter("username");
		String pass = request.getParameter("password");		

		if(user.equalsIgnoreCase("tom") && pass.equals("tom1234")) {
			response.sendRedirect("success.html");
		}
		else {
			response.sendRedirect("index.html");
		}
	}

}
