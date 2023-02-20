package com.capgemini.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.capgemini.pojo.Login;
import com.capgemini.service.IEmployeeService;

@Controller
public class EmployeeController {

	@Autowired
	private IEmployeeService employeeService;
	
	@RequestMapping("/")
	public String showIndexPage(ModelMap map) {
		map.put("login", new Login());
		return "login";
	}
	
	
//	@RequestMapping(value="/loginForm", method=RequestMethod.POST)
//	public String afterLogin(@RequestParam("username") String user, @RequestParam("password") String pass) {
//		
//		if(user.equals("tom") && pass.equals("tom123"))
//			return "successPage";
//		else
//			return "./index";
//	}
	
	@RequestMapping(value="/loginForm", method=RequestMethod.POST)
	public String afterLogin(@Valid @ModelAttribute("login") Login login, BindingResult result) {
		if(result.hasErrors())
			return "login";
		
		Login user = employeeService.isValidUser(login);
		
		if(user != null) {
			if (user.getUserType().equals("admin"))
				return "admin";
			else
				return "userpage";
		}
		else
			return "login";
	}
}
