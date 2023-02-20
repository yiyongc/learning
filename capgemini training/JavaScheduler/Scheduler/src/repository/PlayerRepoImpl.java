package repository;

import data.Player;

public class PlayerRepoImpl implements IPlayerRepo {
	
	private Player[] players = new Player[5];
	
	@Override
	public String save(Player p) {
		
		StringBuilder result = new StringBuilder();
		
		//Search for empty name
		if (p.getName().trim().isEmpty()) {
			result.append("Error: Player does not have a name.");
			
			return result.toString();
		}
		
		//Search for zero players
		if (p.getGames().length == 0) {
			result.append("Error: Player cannot have no games.");
			
			return result.toString();
		}
		
		//Search for duplicates
		if (findOne(p.getName()) != null) {			
			result.append("Error: Player already exists in the repository.");
			
			return result.toString();
		}
			
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

	@Override
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

	@Override
	public Player[] findAll() {
		return players;
	}

}
