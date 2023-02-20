package assignment.exhibitmonitor.utility;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Connection;

public class Database {

	static Logger logger = Logger.getLogger("database class");
	
	private static String dbconnectionString = "jdbc:mysql://localhost:3306/exhibitmonitor?autoReconnect=true&useSSL=false";
	private static String dbuser = "root";
    private static String dbpass = "root";
    
    private Database() {}
    
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			logger.log(Level.FINE, e.getMessage(), e);
		}  
		
	}
	
	
	
	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(dbconnectionString, dbuser, dbpass);   
	}
}
