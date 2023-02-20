package com.capgemini.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.capgemini.pojo.Account;
import com.capgemini.service.IAccountService;

@org.springframework.web.bind.annotation.RestController
public class RestController {

	@Autowired
	private IAccountService accService;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping("/accounts")
	public ResponseEntity<List<Account>> getAllAccounts() {
		List<Account> accounts = accService.getAllAccounts();
		
		if (accounts == null) {
			return new ResponseEntity("Sorry, unable to retrieve objects!", HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(accounts, HttpStatus.OK);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping("/accounts/{accId}")
	public ResponseEntity<Account> getAccount(@PathVariable("accId") int accId) {
		
		Account account = accService.getAccount(accId);
		
		if (account == null) {
			return new ResponseEntity("Sorry, account is not found!", HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(account, HttpStatus.OK);
	}
	
	@PostMapping("/accounts")
	public ResponseEntity<Account> createAccount(@RequestBody Account acc) {
		accService.registerAccount(acc);
		
		return new ResponseEntity<>(acc, HttpStatus.OK);
	}
	
	@DeleteMapping("/accounts/{accId}")
	public ResponseEntity<String> deleteAccount(@PathVariable("accId") int accId) {
		accService.deleteAccount(accId);
		
		return new ResponseEntity<>("Deleting Account "+accId+ "!", HttpStatus.OK);
	}
	
	@PutMapping("/accounts")
	public ResponseEntity<Account> updateAccount(@RequestBody Account acc) {
		accService.updateAccount(acc);
		
		return new ResponseEntity<>(acc, HttpStatus.OK);
	}
	
	
}
