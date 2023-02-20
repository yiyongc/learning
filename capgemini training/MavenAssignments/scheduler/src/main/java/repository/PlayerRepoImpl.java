package repository;

import data.Player;

public class PlayerRepoImpl implements IPlayerRepo {
	
	private Player[] players = new Player[5];
	
	
	public String save(Player p) {
		
		StringBuilder result = new StringBuilder();
		
		result.append(errorChecking(p));
		
		if (result.length() != 0)
			return result.toString();
		
			
		for (int i = 0; i < players.length ; i++) {
			if (players[i] == null) {
				players[i] = p;
				result.append("Player " + p.getName() + " is successfully added.");
				break;
			}
			
			//Array is full
			if (i == players.length - 1) {
				Player[] temp = new Player[players.length + 5];
				
				for (int j = 0; j < players.length; j++) {
					temp[j] = players[j];
				}
				temp[players.length] = p;
				
				players = temp;
				result.append("Player " + p.getName() + " is successfully added.");
			}
		}
		
		return result.toString();
	}

	
	public Player findOne(String name) {
		Player resultOfSearch = null;
		
		for(Player player : players) {
			if(player == null)
				return resultOfSearch;
			if(player.getName().equalsIgnoreCase(name)) {
				resultOfSearch = player;
				break;
			}
		}
		
		return resultOfSearch;
	}

	
	public Player[] findAll() {
		return players;
	}
	
	private String errorChecking(Player p) {
		//Search for empty name
		if (p.getName().trim().isEmpty()) {
			return "Error: Player does not have a name.";
		}

		//Search for zero players
		if (p.getGames().length == 0) {
			return "Error: Player cannot have no games.";
		}

		//Search for duplicates
		if (findOne(p.getName()) != null) {			
			return "Error: Player already exists in the repository.";
		}

		return "";
	}

}
