package assignment.exceptionlib.actions;

import java.util.Map;

public class Email extends Action {

	@Override
	public String execute(Map<String, String> attributes) {
		StringBuilder sb = new StringBuilder("Sending Email ");
		
		String sendTo = attributes.get("to");
		String message = attributes.get("message");
		
		if (sendTo != null)
			sb.append("to " + sendTo);
		if (message != null)
			sb.append(" Message contents: " + message);
			
		return sb.toString();
	}

}
