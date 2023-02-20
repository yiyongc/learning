package com.capgemini.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.capgemini.model.Product;

public class UpdateDaoImpl implements IUpdateDao {

	static Properties prop = new Properties();
	static Logger logger = Logger.getLogger("updatedao");
	
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
	public Product getProductForUpdate(int productId) {
		Product product = null;
		
		String query = 	"select * from products where idproducts = ?";
		PreparedStatement prepStmt = null;

		try {
			Connection connection = getDatabaseConnection();
			if(connection != null) {
				prepStmt = connection.prepareStatement(query);
				prepStmt.setInt(1, productId);
				ResultSet rs = prepStmt.executeQuery();
				
				if (rs.next()) {
					String productName = rs.getString("name");
					String productType = rs.getString("type");
					Date productDate = rs.getDate("expiry");
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					String date = sdf.format(productDate);
					String description = rs.getString("description");
					int qty = rs.getInt("qty");
					double price = rs.getDouble("price");
					
					product = new Product(productName, productType, date, description, Integer.toString(qty), Double.toString(price));
					product.setProductId(rs.getInt("idproducts"));
					
				}
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

		return product;
	}
	
	
	@Override
	public boolean updateProduct(Product product) {
		boolean result = false;
		
		String query = 	"UPDATE products SET name = ?, type = ?, expiry = ?, description = ?, qty = ?, price = ?"
				+ "WHERE idproducts = ?";
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
				prepStmt.setInt(7, product.getProductId());

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
