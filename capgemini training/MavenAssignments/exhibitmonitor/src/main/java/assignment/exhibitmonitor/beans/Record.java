package assignment.exhibitmonitor.beans;

import java.util.Date;

public class Record {
	String fileName; 
	Date date; 
	int recordNo; 
	String inputRecord;
	
	public Record(String fileName, Date date, int recordNo, String record) {
		this.fileName = fileName;
		this.date = date;
		this.recordNo = recordNo;
		this.inputRecord = record;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getRecordNo() {
		return recordNo;
	}

	public void setRecordNo(int recordNo) {
		this.recordNo = recordNo;
	}

	public String getRecord() {
		return inputRecord;
	}

	public void setRecord(String record) {
		this.inputRecord = record;
	}
	
	
}
