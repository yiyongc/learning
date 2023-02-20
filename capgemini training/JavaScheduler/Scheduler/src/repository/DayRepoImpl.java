package repository;

import data.Day;

public class DayRepoImpl implements IDayRepo {
	
	private Day[] days = new Day[5];

	@Override
	public String save(Day d) {
		
		StringBuilder result = new StringBuilder();
		
		//Search for empty name
		if (d.getName().trim().isEmpty()) {
			result.append("Error: Day does not have a name.");
			
			return result.toString();
		}
		
		//Search for zero players
		if (d.getGames().length == 0) {
			result.append("Error: Day cannot have no games.");
			
			return result.toString();
		}
		
		//Search for duplicates
		if (findOne(d.getName()) != null) {			
			result.append("Error: Day already exists in the repository.");
			
			return result.toString();
		}
			
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

	@Override
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

	@Override
	public Day[] findAll() {
		return days;
	}

}
