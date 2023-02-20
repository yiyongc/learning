package com.capgemini.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.capgemini.pojo.Account;

@Controller
public class HelloController {
	
	@RequestMapping("/hello")
	public ModelAndView sayHello() {
		
		String message = "Hello! Spring3 Web Application!";
		
		return new ModelAndView("helloPage", "msg", message);
	}
	
	@RequestMapping("/register")
	public ModelAndView registration() {
		return new ModelAndView("register", "registerAccount", new Account());
	}
	
	@RequestMapping(value="/saveRegistration", method=RequestMethod.POST)
	public String saveRegistration(@ModelAttribute("registerAccount")Account acc) {
		System.out.println(acc);
		return "showRegistration";
	}
}
