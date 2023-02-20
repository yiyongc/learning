package repository;

import data.Game;

public class GameRepoImpl implements IGameRepo {
	
	private Game[] games = new Game[5];
	
	public GameRepoImpl() {
		//default constructor
	}
	
	public GameRepoImpl(Game[] games) {
		this.games = games;
	}

	
	public String save(Game g) {
		
		StringBuilder result = new StringBuilder();
		
		result.append(errorChecking(g));
		
		if(result.length() != 0)
			return result.toString();
			
		for (int i = 0; i < games.length ; i++) {
			if (games[i] == null) {
				games[i] = g;
				result.append("Game " + g.getName() + " is successfully added.");
				break;
			}
			
			//Array is full
			if (i == games.length - 1) {
				Game[] temp = new Game[games.length + 5];
				
				for (int j = 0; j < games.length; j++) {
					temp[j] = games[j];
				}
				temp[games.length] = g;
				
				games = temp;
				result.append("Game " + g.getName() + " is successfully added.");
			}
		}
		
		return result.toString();
	}

	
	public Game findOne(String name) {
		Game resultOfSearch = null;
		
		for(Game game : games) {
			if (game == null)
				return resultOfSearch;
			else if (game.getName().equalsIgnoreCase(name)) {
				resultOfSearch = game;
				break;
			}
		}
		
		return resultOfSearch;
	}

	
	public Game[] findAll() {
		return games;
	}

	
	private String errorChecking(Game g) {
		//Search for null object
		if (g == null) {
			return "Error: Game is a null object.";
		}

		//Search for empty name
		if (g.getName().trim().isEmpty()) {
			return "Error: Game does not have a name.";
		}

		//Search for zero players
		if (g.getNoOfPlayers() <= 0) {
			return "Error: Game cannot have no players.";
		}

		//Search for duplicates
		if (findOne(g.getName()) != null) {			
			return "Error: Game already exists in the repository.";
		}
		
		return "";
	}
}
