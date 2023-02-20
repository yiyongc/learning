package assignment.exceptionlib.actions;

import java.util.Map;

public class Log extends Action {

	@Override
	public String execute(Map<String, String> attributes) {
		String locationOfFile = attributes.get("to");
		String message = attributes.get("message");
		
		StringBuilder sb = new StringBuilder("Logging");
		if (locationOfFile != null)
			sb.append(" to " + locationOfFile);
		if (message != null)
			sb.append(" Message: " + message);
			
		return sb.toString();
	}

}
