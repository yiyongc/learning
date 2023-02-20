package com.capgemini.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.capgemini.model.Login;

public class LoginDaoImpl implements ILoginDao {

	static Properties prop = new Properties();
	static Logger logger = Logger.getLogger("logindao");
	
	private Connection getDatabaseConnection() {
		Connection connection = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			String filename = "config.properties";
			InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream(filename);
			prop.load(input);
			
			connection = DriverManager.getConnection(prop.getProperty("database"), prop.getProperty("dbuser"), prop.getProperty("dbpassword"));
			
		} catch (ClassNotFoundException | SQLException | IOException e) {
			logger.log(Level.FINE, e.getMessage(), e);
		}
		
		return connection;
	}
	
	@Override
	public boolean isValidUser(Login login) {
		boolean result = false;
		
		String query = "select * from users where username = ? and password = ?";
		PreparedStatement prepStmt = null;
		try {
			Connection connection = getDatabaseConnection();
			if(connection != null) {
				prepStmt = connection.prepareStatement(query);
				prepStmt.setString(1, login.getUserName());
				prepStmt.setString(2, login.getUserPwd());
				ResultSet rs = prepStmt.executeQuery();
				if (rs.next()) {
					result = true;
				}
			}
		} catch (SQLException e) {
			logger.log(Level.FINE, e.getMessage(), e);
		} finally {
			try {
				if(prepStmt != null)
					prepStmt.close();
			} catch (SQLException e) {
				logger.log(Level.FINE, e.getMessage(), e);
			}
		}
		
		return result;
	}

}
