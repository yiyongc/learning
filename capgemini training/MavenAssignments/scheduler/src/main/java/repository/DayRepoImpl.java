package repository;

import data.Day;

public class DayRepoImpl implements IDayRepo {
	
	private Day[] days = new Day[5];

	public String save(Day d) {
		
		StringBuilder result = new StringBuilder();
		
		result.append(errorChecking(d));
		
		if (result.length() != 0)
			return result.toString();
			
		for (int i = 0; i < days.length ; i++) {
			if (days[i] == null) {
				days[i] = d;
				result.append(d.getName() + " is successfully added.");
				break;
			}
			
			//Array is full
			if (i == days.length - 1) {
				Day[] temp = new Day[days.length + 5];
				
				for (int j = 0; j < days.length; j++) {
					temp[j] = days[j];
				}
				temp[days.length] = d;
				
				days = temp;
				result.append(d.getName() + " is successfully added.");
			}
		}
		
		return result.toString();
	}

	public Day findOne(String name) {
		Day resultOfSearch = null;
		
		for(Day day : days) {
			if(day == null)
				return resultOfSearch;
			if(day.getName().equalsIgnoreCase(name)) {
				resultOfSearch = day;
				break;
			}
		}
		
		return resultOfSearch;
	}


	public Day[] findAll() {
		return days;
	}
	
	private String errorChecking(Day d) {
		//Search for empty name
		if (d.getName().trim().isEmpty()) {
			return "Error: Day does not have a name.";
		}

		//Search for zero players
		if (d.getGames().length == 0) {
			return "Error: Day cannot have no games.";
		}

		//Search for duplicates
		if (findOne(d.getName()) != null) {			
			return "Error: Day already exists in the repository.";
		}
		
		return "";
	}

}
