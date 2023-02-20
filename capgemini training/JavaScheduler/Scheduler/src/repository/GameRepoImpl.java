package repository;

import data.Game;

public class GameRepoImpl implements IGameRepo {
	
	private Game[] games = new Game[5];
	
	public GameRepoImpl() {}
	
	public GameRepoImpl(Game[] games) {
		this.games = games;
	}

	@Override
	public String save(Game g) {
		
		StringBuilder result = new StringBuilder();
		
		//Search for null object
		if (g == null) {
			result.append("Error: Game is a null object.");
			
			return result.toString();
		}
		
		//Search for empty name
		if (g.getName().trim().isEmpty()) {
			result.append("Error: Game does not have a name.");
			
			return result.toString();
		}
		
		//Search for zero players
		if (g.getNoOfPlayers() <= 0) {
			result.append("Error: Game cannot have no players.");
			
			return result.toString();
		}
		
		//Search for duplicates
		if (findOne(g.getName()) != null) {			
			result.append("Error: Game already exists in the repository.");
			
			return result.toString();
		}
			
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

	@Override
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

	@Override
	public Game[] findAll() {
		return games;
	}

}
