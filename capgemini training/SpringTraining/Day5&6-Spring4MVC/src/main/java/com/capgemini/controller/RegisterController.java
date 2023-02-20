package com.capgemini.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.capgemini.pojo.Account;
import com.capgemini.service.IAccountService;

@Controller
public class RegisterController {
	
	@Autowired
	private IAccountService accountService;
	private boolean editFlag = false;
	private Account editAcc = null;
	
	@RequestMapping("/hello")
	public ModelAndView sayHello() {
		
		String message = "Hello! bloody Spring4 Web Application!";
		
		return new ModelAndView("helloPage", "msg", message);
	}
	
//	@RequestMapping("/register")
//	public ModelAndView registration() {
//		return new ModelAndView("register", "registerAccount", new Account());
//	}
	
	@RequestMapping("/register")
	public String showRegisterPage(ModelMap map) {
		if (editFlag)
			map.put("editAcc", editAcc);
		else {
			map.put("registerAccount", new Account());
		}
		map.put("editFlag", editFlag);
		map.put("occupations", getOccupation());
		map.put("accounts", accountService.getAllAccounts());
		//map.put("editFlag", false);
		return "register";
	}
	
	
	@RequestMapping(value="/saveRegistration", method=RequestMethod.POST)
	public String showRegistration(@Valid @ModelAttribute("registerAccount") Account acc, BindingResult result, ModelMap map) {
		map.put("occupations", getOccupation());
		
		if (result.hasErrors()) {
			return "register";
		} else {
			accountService.registerAccount(acc);
			return "redirect:register";
			//return "showRegistration";
		}
	}
	
	@RequestMapping("/delete/{accId}")
	public String deleteAcc(@PathVariable("accId") int accId) {
		accountService.deleteAccount(accId);
		return "redirect:/register";
	}
	
	@RequestMapping("/edit/{accId}")
	public String editAccount(@PathVariable("accId") int accId, ModelMap map) {
		editAcc = accountService.getAccount(accId);
		editFlag = true;
		
		return "redirect:/register";
	}
	
	@RequestMapping(value="/updateAccount", method=RequestMethod.POST)
	public String updateAccount(@Valid @ModelAttribute("editAcc") Account acc, BindingResult result, ModelMap map) {
		map.put("editFlag", editFlag);
		map.put("occupations", getOccupation());
		map.put("accounts", accountService.getAllAccounts());
		
		if (result.hasErrors()) {
			return "register";
		} else {
			accountService.updateAccount(acc);
			editFlag = false;
			return "redirect:register";
			//return "showRegistration";
		}
	}
	
	public List<String> getOccupation() {
		List<String> occupation = new ArrayList<>();
		occupation.add("Public Sector");
		occupation.add("Private Sector");
		occupation.add("Government Sector");
		occupation.add("Others");
		
		return occupation;
	}
}
