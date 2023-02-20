package assignment.exhibitmonitor.context;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import assignment.exhibitmonitor.beans.CSVInputFile;
import assignment.exhibitmonitor.beans.CSVOutputFile;
import assignment.exhibitmonitor.beans.Field;
import assignment.exhibitmonitor.beans.Record;

public class ApplicationContext {
	
	
	private static final Map<String, Date> fileLog = new HashMap<>();
	private static final Map<CSVInputFile, List<Field>> inputFiles = new HashMap<>();
	private static final Map<CSVOutputFile, List<String>> outputFiles = new HashMap<>();
	private static final List<Record> validRecords = new Vector<>();
	private static final List<Record> invalidRecords = new Vector<>();
	
	private ApplicationContext() {}
	
	public static List<Record> getInvalidRecords() { return invalidRecords; }
	public static Map<String, Date> getFileLog() { return fileLog; }
	public static List<Record> getValidRecords() { return validRecords; }
	public static Map<CSVOutputFile, List<String>> getOutputFiles() { return outputFiles; }
	public static Map<CSVInputFile, List<Field>> getInputFiles() { return inputFiles; }
}
