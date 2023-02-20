package assignment.exceptionlib.storage;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import assignment.exceptionlib.beans.ActionInfo;
import assignment.exceptionlib.beans.ProjectInfo;

public class ExceptionHandlerStorage implements IExceptionHandlerStorage {
	private Map<ProjectInfo, Set<ActionInfo>> handlerStorage;
	
	
	public ExceptionHandlerStorage(Map<ProjectInfo, Set<ActionInfo>> handlerStorage) {
		this.handlerStorage = handlerStorage;
	}

	public boolean addProjectInfo(ProjectInfo pInfo) {
		if (handlerStorage.containsKey(pInfo))
			return false;
		else {
			Set<ActionInfo> actions = new HashSet<>();
			handlerStorage.put(pInfo, actions);
			return true;
		}
	}
		
	public Set<ActionInfo> getActions(ProjectInfo pInfo) {
		if (!handlerStorage.containsKey(pInfo))
			return Collections.emptySet();
		else {
			return handlerStorage.get(pInfo);
		}
	
	}

	public Map<ProjectInfo, Set<ActionInfo>> getHandlerStorage() {
		return handlerStorage;
	}

	public void setHandlerStorage(Map<ProjectInfo, Set<ActionInfo>> handlerStorage) {
		this.handlerStorage = handlerStorage;
	}
	
}
