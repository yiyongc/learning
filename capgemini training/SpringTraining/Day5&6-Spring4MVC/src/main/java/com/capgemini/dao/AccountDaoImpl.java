package com.capgemini.dao;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.capgemini.pojo.Account;
import com.capgemini.util.AccountRowMapper;

@Repository("accountDao")
public class AccountDaoImpl implements IAccountDao {

	@Autowired
	private NamedParameterJdbcTemplate namedJdbcTemplate;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	@Override
	public void registerAccount(Account account) {
//		String query = "INSERT INTO accounts(firstname, lastname, dateofbirth, address, "
//				+ "occupation, gender, email, mobile)"
//				+ " VALUES(?,?,?,?,?,?,?,?)";
//		
//		int count =jdbcTemplate.update(query, account.getFirstName(), account.getLastName(), 
//				new Date(account.getDateOfBirth().getTime()), account.getAddress(), 
//				account.getOccupation(), account.getGender(), account.getEmail(), 
//				Integer.parseInt(account.getMobile()));
		String query = "INSERT INTO accounts(firstname, lastname, dateofbirth, address, "
				+ "occupation, gender, email, mobile)"
				+ " VALUES(:fName,:lName,:dob,:add,:occ,:gender,:email,:mobile)";
		
		Map<String, Object> params = new HashMap<>();
		
		params.put("fName", account.getFirstName());
		params.put("lName", account.getLastName());
		params.put("dob", new Date(account.getDateOfBirth().getTime()));
		params.put("add", account.getAddress());
		params.put("occ", account.getOccupation());
		params.put("gender", account.getGender());
		params.put("email", account.getEmail());
		params.put("mobile", Integer.parseInt(account.getMobile()));
		
		int count = namedJdbcTemplate.update(query, params);
		
		if (count > 0)
			System.out.println("Account added.");
	
	}

	@Override
	public List<Account> getAllAccounts() {
		String query = "SELECT * FROM accounts";
		
		return jdbcTemplate.query(query, new AccountRowMapper());
	}

	@Override
	public void deleteAccount(int id) {
		String query = "DELETE FROM accounts WHERE idaccounts = ?";
		
		int count = jdbcTemplate.update(query, id);
		
		if (count > 0)
			System.out.println("Account " + id + " has been deleted.");
	}
	
	
	@Override
	public Account getAccount(int id) {
		String query = "SELECT * FROM accounts WHERE idaccounts = ?";
		Account acc = null;
		
		try{
			acc = jdbcTemplate.queryForObject(query, new AccountRowMapper(), id);
		} catch(EmptyResultDataAccessException e) {
			
		}
		
		return acc;
	}

	@Override
	public void updateAccount(Account acc) {
		
		String query = "UPDATE accounts SET firstname = ?, lastname = ?, dateofbirth = ?, "
				+ "address = ?, occupation = ?, gender = ?, email = ?, mobile = ? "
		     	+ "WHERE idaccounts = ?";

		int count = jdbcTemplate.update(query, acc.getFirstName(), acc.getLastName(), acc.getDateOfBirth(), 
				acc.getAddress(), acc.getOccupation(), acc.getGender(), acc.getEmail(), acc.getMobile(), acc.getAccId());

		if (count > 0)
			System.out.println("Account updated!");
	}

}
