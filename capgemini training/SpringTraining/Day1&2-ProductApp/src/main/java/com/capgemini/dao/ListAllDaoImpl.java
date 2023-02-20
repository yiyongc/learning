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
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.capgemini.model.Product;

public class ListAllDaoImpl implements IListAllDao {

	static Properties prop = new Properties();
	static Logger logger = Logger.getLogger("listDAO");
	
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
	public List<Product> getAllProducts() {
		List<Product> products = new ArrayList<>();
		
		String query = "select * from products";
		PreparedStatement prepStmt = null;
		try {
			Connection connection = getDatabaseConnection();
			if(connection != null) {
				prepStmt = connection.prepareStatement(query);
				ResultSet rs = prepStmt.executeQuery();
				while (rs.next()) {
					String productName = rs.getString("name");
					String productType = rs.getString("type");
					Date productDate = rs.getDate("expiry");
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					String date = sdf.format(productDate);
					String description = rs.getString("description");
					int qty = rs.getInt("qty");
					double price = rs.getDouble("price");
					
					Product product = new Product(productName, productType, date, description, Integer.toString(qty), Double.toString(price));
					product.setProductId(rs.getInt("idproducts"));
					
					products.add(product);
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
		
		return products;
	}

}
