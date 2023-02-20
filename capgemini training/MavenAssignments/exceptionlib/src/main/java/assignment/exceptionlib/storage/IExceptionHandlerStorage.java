package assignment.exceptionlib.storage;

import java.util.Map;
import java.util.Set;

import assignment.exceptionlib.beans.ActionInfo;
import assignment.exceptionlib.beans.ProjectInfo;

public interface IExceptionHandlerStorage {
	
	public boolean addProjectInfo(ProjectInfo pInfo); 
		
	public Set<ActionInfo> getActions(ProjectInfo pInfo); 
	
	public Map<ProjectInfo, Set<ActionInfo>> getHandlerStorage();
	
	public void setHandlerStorage(Map<ProjectInfo, Set<ActionInfo>> handlerStorage);
	
}
