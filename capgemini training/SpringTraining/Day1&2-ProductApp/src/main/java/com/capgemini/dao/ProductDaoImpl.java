package com.capgemini.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.capgemini.model.Product;

public class ProductDaoImpl implements IProductDao {

	static Properties prop = new Properties();
	static Logger logger = Logger.getLogger("productDao");
	
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
	public boolean createProduct(Product product) {
		boolean result = false;
		
		String query = 	"INSERT INTO products"
						+ "(name, type, expiry, description, qty, price) VALUES"
						+ "(?,?,?,?,?,?)";
		
		PreparedStatement prepStmt = null;
		
		try {
			Connection connection = getDatabaseConnection();
			if(connection != null) {
				prepStmt = connection.prepareStatement(query);
				prepStmt.setString(1, product.getProductName());
				prepStmt.setString(2, product.getProductType());
				Date sqlDate = new Date(product.getProductExpiry().getTime());
				prepStmt.setDate(3, sqlDate);
				prepStmt.setString(4, product.getProductDesc());
				prepStmt.setInt(5, product.getProductQty());
				prepStmt.setDouble(6, product.getProductPrice());
				result = (prepStmt.executeUpdate() == 1) ? true : false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
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
