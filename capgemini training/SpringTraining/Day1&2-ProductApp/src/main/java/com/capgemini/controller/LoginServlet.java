package com.capgemini.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.capgemini.model.Login;
import com.capgemini.service.ILoginService;
import com.capgemini.service.LoginServiceImpl;

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		String user = request.getParameter("username");
		String pass = request.getParameter("password");
		System.out.println(this.getServletContext().getInitParameter("myName"));
		System.out.println(this.getServletConfig().getInitParameter("loginTag"));
		
		//Bind login to POJO
		Login login = new Login(user, pass);
		
		ILoginService service = new LoginServiceImpl();
		
		if (service.isValidLogin(login)) {
			//response.sendRedirect("pages/product.html");
			request.getRequestDispatcher("pages/product.html").forward(request, response);
		} else {
			//response.sendRedirect("index.html");
			request.getRequestDispatcher("index.html").forward(request, response);
		}
	}

}
