package assignment.exhibitmonitor.main;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

import assignment.exhibitmonitor.beans.CSVOutputFile;
import assignment.exhibitmonitor.context.ApplicationContext;
import assignment.exhibitmonitor.threadtasks.DatabaseTask;
import assignment.exhibitmonitor.threadtasks.ExportTask;
import assignment.exhibitmonitor.threadtasks.PollerTask;
import assignment.exhibitmonitor.utility.XMLParser;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

public class Main {
	
	private static String folderToExportTo = "D:\\Users\\yichee\\Desktop\\Playground\\Assignment 4\\Export";
	
	private Main() {}
	
	public static void main(String[] args) throws IOException {
		XMLParser.parseXML();
		String folderToPoll = "D:\\Users\\yichee\\Desktop\\Playground\\Assignment 4\\Watch";
		Thread pollerThread = new Thread(new PollerTask(new File(folderToPoll)));
		
		pollerThread.start();
		
		Thread dbThreadValid = new Thread(new DatabaseTask(ApplicationContext.getValidRecords(), "valid"));
		Thread dbThreadInvalid = new Thread(new DatabaseTask(ApplicationContext.getInvalidRecords(), "invalid"));
		
		dbThreadValid.start();
		dbThreadInvalid.start();
		
		for(CSVOutputFile key : ApplicationContext.getOutputFiles().keySet()) {
			Date deadline = key.getTime();
			SimpleDateFormat timeOnly = new SimpleDateFormat("HH:mm");
			String timeOfDeadLine = timeOnly.format(deadline);
			
			String[] time = timeOfDeadLine.split(":");
			
			Calendar today = Calendar.getInstance();
			if(time.length > 0) {
				today.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time[0]));
				today.set(Calendar.MINUTE, Integer.parseInt(time[1]));
				today.set(Calendar.SECOND, 0);
			}
			else {
				continue;
			}
			
			Timer timer = new Timer();
			timer.schedule(new ExportTask(key.getName(), folderToExportTo), today.getTime(), TimeUnit.MILLISECONDS.convert(1, TimeUnit.MINUTES));
		}
		
	}
}
