package assignment.exhibitmonitor.beans;

import java.util.Date;

public class CSVInputFile {
	
	private String name;
	private Date time;
	private byte gracePeriod;
	
	
	public CSVInputFile(String name, Date time, byte gracePeriod) {
		super();
		this.name = name;
		this.time = time;
		this.gracePeriod = gracePeriod;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Date getTime() {
		return time;
	}


	public void setTime(Date time) {
		this.time = time;
	}


	public byte getGracePeriod() {
		return gracePeriod;
	}


	public void setGracePeriod(byte gracePeriod) {
		this.gracePeriod = gracePeriod;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CSVInputFile other = (CSVInputFile) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	
	
}
