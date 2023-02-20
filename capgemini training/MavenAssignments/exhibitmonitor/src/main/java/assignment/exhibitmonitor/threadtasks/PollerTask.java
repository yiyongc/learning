package assignment.exhibitmonitor.threadtasks;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import assignment.exhibitmonitor.beans.CSVInputFile;
import assignment.exhibitmonitor.context.ApplicationContext;

public class PollerTask implements Runnable {
	File folder;
	Logger logger = Logger.getLogger("Poller");
	
	public PollerTask(File folder) {
		this.folder = folder;
	}

	@Override
	public void run() {
		while(true) {
			for (File f : folder.listFiles()) {
					if (checkPassed(f)) {
						Path newPath = Paths.get("D:\\Users\\yichee\\Desktop\\Playground\\Assignment 4\\Process\\" + f.getName());
						Date date = new Date(f.lastModified());
						performTaskOnFile(f, true, newPath);
						ApplicationContext.getFileLog().put(f.getName(), date);
						Thread worker = new Thread(new WorkerTask(newPath.toString()));
						worker.start();
					} 
					else {
						performTaskOnFile(f, false, null);
					}
				
			}
			
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				logger.log(Level.FINE, e.getMessage(), e);
				Thread.currentThread().interrupt();
			}
		}
		
	}
	
	private void performTaskOnFile(File f, boolean result, Path newPath) {
		try {
			if (result) {
				Files.move(Paths.get(f.getAbsolutePath()), newPath);
			}
			else 
				Files.delete(Paths.get(f.getAbsolutePath()));
		} catch (IOException e) {
			logger.log(Level.FINE, e.getMessage(), e);
		}
	}
	
	private boolean checkPassed(File f) {
		try {
			return isValidFile(f.getName()) && !isDuplicate(f) && isOnTime(f);
		} catch (Exception e) {
			logger.log(Level.FINE, e.getMessage(), e);
		}
		return false;
	}

	private boolean isValidFile(String file) {
		CSVInputFile input = new CSVInputFile(file, new Date(), (byte)30);
		
		return ApplicationContext.getInputFiles().containsKey(input) ? true : false;
	}
	
	private boolean isDuplicate(File f) {
		Date fileDate = new Date(f.lastModified());
		
		if (ApplicationContext.getFileLog().containsKey(f.getName())) {
			Date history = ApplicationContext.getFileLog().get(f.getName());
			Calendar cal1 = Calendar.getInstance();
			Calendar cal2 = Calendar.getInstance();
			cal1.setTime(fileDate);
			cal2.setTime(history);

			return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
			                  cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
		}
		return false;
	}
	
	private boolean isOnTime(File f) throws ParseException {
		Date date = new Date(f.lastModified());
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		Date dateFormatted = sdf.parse(sdf.format(date));

		
		for (CSVInputFile key : ApplicationContext.getInputFiles().keySet()) {
			if (key.getName().equals(f.getName())) {
				long timeLimit = key.getTime().getTime() + TimeUnit.MINUTES.toMillis(key.getGracePeriod());
				if (timeLimit < dateFormatted.getTime())
					return false;
			}
		}
		return true;	
		
	}
	
}
