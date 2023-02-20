package assignment.exhibitmonitor.threadtasks;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import assignment.exhibitmonitor.beans.CSVInputFile;
import assignment.exhibitmonitor.beans.Field;
import assignment.exhibitmonitor.beans.Record;
import assignment.exhibitmonitor.context.ApplicationContext;
import assignment.exhibitmonitor.exceptions.FileNotDeletedException;

public class WorkerTask implements Runnable {
	Logger logger = Logger.getLogger("Worker");
	private String file;

	public WorkerTask(String file) {
		this.file = file;
	}

	@Override
	public void run() {
		try (FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);) {
	
			int lineNo = 1;
			String currentLine;

			while ((currentLine = br.readLine()) != null) {
				checkStructure(currentLine, lineNo++);
			}
			fr.close();
			br.close();
			
			File f = new File(file);
	
			if(!f.delete())
				throw new FileNotDeletedException();

		} catch (IOException | FileNotDeletedException e) {
			logger.log(Level.FINE, e.getMessage(), e);
		}
		
	}

	private void checkStructure(String currentLine, int lineNo) {
		File f = new File(file);
		CSVInputFile input = new CSVInputFile(f.getName(), new Date(), (byte) 0);
		
		List<Field> structure = ApplicationContext.getInputFiles().get(input);
		String[] givenValues = currentLine.split(",");

		Record record = new Record(f.getName(), new Date(), lineNo, currentLine);

		if(structure.size() != givenValues.length) {
			synchronized(ApplicationContext.getInvalidRecords()) {
				ApplicationContext.getInvalidRecords().add(record);
				ApplicationContext.getInvalidRecords().notifyAll();
			}
		} else {
			if(checkTypes(structure, givenValues)) {
				synchronized(ApplicationContext.getValidRecords()) {
					ApplicationContext.getValidRecords().add(record);
					ApplicationContext.getValidRecords().notifyAll();
				}
			}
			else {
				synchronized(ApplicationContext.getInvalidRecords()) {
					ApplicationContext.getInvalidRecords().add(record);
					ApplicationContext.getInvalidRecords().notifyAll();
				}
			}
		}
	}

	private boolean checkTypes(List<Field> structure, String[] givenValues) {
		boolean result = false;
		
		for (int i = 0; i < structure.size(); i++) {

			String type = structure.get(i).getType();
			String value = givenValues[i];
			
			switch(type) {
			
			case "text":
				result = textCheck(value);
				break;
			case "date":
				result = dateCheck(value);
				break;
			case "datetime":
				result = dateTimeCheck(value);
				break;
			case "int":
				result = intCheck(value); 
				break;
			case "double":
				result = doubleCheck(value);
				break;
			default:
				return false;
			}
			if(!result)
				return result;
		}
		
		return result;
	}

	private boolean doubleCheck(String value) {
		try (Scanner sc = new Scanner(value.trim())) {
		
			if(!sc.hasNextDouble())
				return false;
			sc.nextDouble();

			return !sc.hasNext();
		}
		catch(Exception e) {
			logger.log(Level.FINE, e.getMessage(), e);
			return false;
		}
	}

	private boolean intCheck(String value) {
		try (Scanner sc = new Scanner(value.trim())){
			if(!sc.hasNextInt())
				return false;
			sc.nextInt();

			return !sc.hasNext();
		}
		catch(Exception e) {
			logger.log(Level.FINE, e.getMessage(), e);
			return false;
		}

	}
	
	private boolean dateCheck(String date) {
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
		try {
			sdf.parse(date);
			return true;
		} catch (ParseException e) {
			logger.log(Level.FINE, e.getMessage(), e);
			return false;
		}
	}

	private boolean dateTimeCheck(String dateTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			sdf.parse(dateTime);
			return true;
		} catch (ParseException e) {
			logger.log(Level.FINE, e.getMessage(), e);
			return false;
		}
	}

	private boolean textCheck(String value) {
		return (value.trim().length() == 0) ? false : true;
	}
}

