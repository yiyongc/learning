package assignment.exhibitmonitor.threadtasks;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

import assignment.exhibitmonitor.beans.CSVOutputFile;
import assignment.exhibitmonitor.context.ApplicationContext;
import assignment.exhibitmonitor.utility.Database;

public class ExportTask extends TimerTask {

	static Logger logger = Logger.getLogger("ExportTask");
	private String fileName;
	private String folder;


	public ExportTask(String fileName, String folder) {
		this.fileName = fileName;
		this.folder = folder;
	}


	@Override
	public void run() {

		
		//Delete existing file
		File f = new File(folder, fileName);
		if (f.exists() && f.delete())
			logger.log(Level.FINE, "duplicate deleted");
		
		
		List<String> dependencies = ApplicationContext.getOutputFiles().get(new CSVOutputFile(fileName, new Date()));

		for (String dependencyFileName : dependencies) {
			
			if(ApplicationContext.getFileLog().containsKey(dependencyFileName)) {
				
				try {
					writeToFile(dependencyFileName);
				} catch (IOException e) {
					logger.log(Level.FINE, e.getMessage(), e);
				}
			}
		}
		
	
	}


	private void writeToFile(String dependencyFileName) throws IOException {
		List<String> linesToWrite = new ArrayList<>();
		
		try (Connection con = Database.getConnection();
				PreparedStatement statement = con.prepareStatement("SELECT record FROM valid WHERE filename = ?");){
			
			statement.setString(1, dependencyFileName);
			
			ResultSet results = statement.executeQuery();
			
			while(results.next()) {
				String result = results.getString("record");
				linesToWrite.add(result);
			}
			
		} catch (SQLException e) {
			logger.log(Level.FINE, e.getMessage(), e);
		} 

		Files.write(Paths.get(folder + "\\" + fileName), linesToWrite, Charset.forName("UTF-8"), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
		
	}

}
