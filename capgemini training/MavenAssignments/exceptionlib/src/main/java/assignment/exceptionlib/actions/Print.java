package assignment.exceptionlib.actions;

import java.util.Map;

public class Print extends Action {

	@Override
	public String execute(Map<String, String> attributes) {
		return "Printing Message: " + attributes.get("message");
	}

}
