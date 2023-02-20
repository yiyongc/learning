package com.capgemini.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.capgemini.pojo.Account;

public class AccountRowMapper implements RowMapper<Account> {

	@Override
	public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
		Account acc = new Account();
		
		acc.setAccId(rs.getInt("idaccounts"));
		acc.setFirstName(rs.getString("firstname"));
		acc.setLastName(rs.getString("lastname"));
		acc.setEmail(rs.getString("email"));
		acc.setDateOfBirth(rs.getDate("dateOfBirth"));
		acc.setAddress(rs.getString("address"));
		acc.setGender(rs.getString("gender"));
		acc.setOccupation(rs.getString("occupation"));
		acc.setMobile("" +rs.getInt("mobile"));
		
		return acc;
	}

}
