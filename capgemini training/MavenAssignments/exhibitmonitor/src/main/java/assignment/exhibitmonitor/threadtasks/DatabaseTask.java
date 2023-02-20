package assignment.exhibitmonitor.threadtasks;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import assignment.exhibitmonitor.beans.Record;
import assignment.exhibitmonitor.utility.Database;

public class DatabaseTask implements Runnable {
	
	List<Record> recordList;
	String tableName;
	Logger logger = Logger.getLogger("database");
	boolean scanForChanges = true;
	
	public DatabaseTask(List<Record> recordList, String table) {
		this.recordList = recordList;
		this.tableName = table;
	}

	@Override
	public void run() {
		while(scanForChanges) {
			synchronized(recordList) {
				while(recordList.isEmpty()) {
					try {
						recordList.wait();
					} catch (InterruptedException e) {
						logger.log(Level.FINE, e.getMessage(), e);
						Thread.currentThread().interrupt();
					}
				}
				addToDatabase(recordList.get(0));
				recordList.remove(0);
			}
		}
	}


	private void addToDatabase(Record record) {
		
		
		try (Connection con = Database.getConnection();
				PreparedStatement statement = con.prepareStatement("INSERT INTO " + tableName + "(filename, linenum, date, record) VALUES(?,?,?,?)");){
			
			statement.setString(1, record.getFileName());
			statement.setInt(2, record.getRecordNo());
			statement.setTimestamp(3, new Timestamp(record.getDate().getTime()));
			statement.setString(4, record.getRecord());

		
			statement.execute();

		} catch (SQLException e) {
			logger.log(Level.FINE, e.getMessage(), e);
		} 
		
	}

}
