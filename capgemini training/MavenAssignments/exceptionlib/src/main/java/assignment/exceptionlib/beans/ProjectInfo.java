package assignment.exceptionlib.beans;

public class ProjectInfo {
	private String projectName;
	private String moduleName;
	private String exceptionType;
	
	public ProjectInfo(String projectName, String moduleName, String exceptionType) {
		this.projectName = projectName;
		this.moduleName = moduleName;
		this.exceptionType = exceptionType;
	}
	
	@Override
	public String toString() {
		return "[" + projectName + ", " + moduleName + ", " + exceptionType +"]";
	}
	
	public String getProject() {
		return projectName;
	}
	
	public String getModule() {
		return moduleName;
	}
	
	public String getException() {
		return exceptionType;
	}
	
	public void setProject(String project) {
		this.projectName = project;
	}
	
	public void setModule(String module) {
		this.moduleName = module;
	}
	

	public void setException(String exception) {
		this.exceptionType = exception;
	}
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof ProjectInfo))
			return false;
		
		ProjectInfo other = (ProjectInfo) o;
		
		return this.getProject().equals(other.getProject()) &&
				this.getModule().equals(other.getModule()) &&
				 this.getException().equals(other.getException());
	}
	
	@Override
	public int hashCode() {
		StringBuilder sb = new StringBuilder(projectName);
		sb.append(moduleName);
		sb.append(exceptionType);
		
		return sb.toString().hashCode();
	}

}
