package service;

import data.Day;
import data.Game;
import data.Player;
import repository.IDayRepo;
import repository.IGameRepo;
import repository.IPlayerRepo;

public class SchedulerServiceImpl implements ISchedulerService {

	private IDayRepo dayRepo;
	private IGameRepo gameRepo;
	private IPlayerRepo playerRepo;
	
	public SchedulerServiceImpl() {}
	
	
	public SchedulerServiceImpl(IGameRepo gameRepo, IPlayerRepo playerRepo, IDayRepo dayRepo) {
		this.dayRepo = dayRepo;
		this.playerRepo = playerRepo;
		this.gameRepo = gameRepo;
	}
	
	
	@Override
	public String createGame(Game game) {		
		return gameRepo.save(game);
	}

	@Override
	public String createPlayer(Player player) {
		if (player == null)
			return "Error: Player is a null object.";
		
		Game[] gameList = player.getGames();
		boolean gameExists = false;
		
		if (gameList == null)
			return "Error: Player does not have any games.";
				
		for(Game game : gameList) {
			if (game == null)
				break;
			if (gameRepo.findOne(game.getName()) != null) {
				gameExists = true;
				break;
			}
		}
		
		if (!gameExists) 
			return "Error: Player does not play any game in the System.";
		else	
			return playerRepo.save(player);
	}

	@Override
	public String createDay(Day day) {
		if (day == null)
			return "Error: Day is a null object.";
		
		Game[] gameList = day.getGames();
		boolean gameExists = false;
		
		if (gameList == null)
			return "Error: Day does not have any games.";
		
		for(Game game : gameList) {
			if (game == null)
				break;
			if (gameRepo.findOne(game.getName()) != null) {
				gameExists = true;
				break;
			}
		}
		
		if (!gameExists) 
			return "Error: Day does not contain any game in the System.";
		else	
			return dayRepo.save(day);
	}

	@Override
	public StringBuffer gameWiseReport(String gameName) {
		StringBuffer result = new StringBuffer();
		
		if (gameName.trim().isEmpty()) {
			result.append("Error: Search field cannot be empty.");
			return result;
		}
		
		Game gameFound = gameRepo.findOne(gameName);

		if (gameFound == null) {
			result.append("Error: Game cannot be found in the System.");
			return result;
		}
		
		result.append("Game Wise Report for Game " + gameFound.getName() + "\n\n");
		
		StringBuilder playerList = new StringBuilder();
		StringBuilder dayList = new StringBuilder();
		
		for(Player player : playerRepo.findAll()) {
			if (player == null)
				break;
			for(Game game : player.getGames()) {
				if (game.getName().equalsIgnoreCase(gameFound.getName()))
					playerList.append(player.getName() + "\n");
			}
		}
		
		if (playerList.length() != 0) {
			result.append("Player(s) who play " + gameFound.getName() +": \n");
			result.append(playerList + "\n");
			playerList.delete(0, playerList.length());
		}
		else {
			result.append("There are no players who play the game.\n\n");
		}
		
		
		for(Day day : dayRepo.findAll()) {
			if (day == null)
				break;
			for(Game game : day.getGames()) {
				if (game.getName().equalsIgnoreCase(gameFound.getName()))
					dayList.append(day.getName() + "\n");
			}
		}
		
		if (dayList.length() != 0) {
			result.append("Day(s) on which " + gameFound.getName() + " is scheduled: \n");
			result.append(dayList + "\n");
			dayList.delete(0, dayList.length());
		}
		else {
			result.append("The game is not scheduled on any day.\n\n");
		}
		
		return result;
	}

	@Override
	public StringBuffer playerWiseReport(String playerName) {
		StringBuffer result = new StringBuffer();
		
		if (playerName.trim().isEmpty()) {
			result.append("Error: Search field cannot be empty.");
			return result;
		}
		
		Player playerFound = playerRepo.findOne(playerName);

		if (playerFound == null) {
			result.append("Error: Player cannot be found in the System.");
			return result;
		}
		
		result.append("Player Wise Report for Player " + playerFound.getName() + "\n\n");
		
		result.append(playerFound.getName() + "\'s Game(s): \n");
		
		Game[] playerGames = playerFound.getGames();
		
		for(Game game : playerGames) {
			if (game == null)
				break;
			
			StringBuilder gamesDaySB = new StringBuilder();
			
			for(Day day : dayRepo.findAll()) {
				if (day == null)
					break;
				
				for (Game dayGame : day.getGames()) {
					if (dayGame == null)
						break;
					if (game.getName().equalsIgnoreCase(dayGame.getName())) {
						gamesDaySB.append("Game is played on " + day.getName() + "\n");
						break;
					}
				}
			}
			//Check if game is in system
			if (gamesDaySB.length() != 0) {
				result.append("Game: " + game.getName() + "\n");
				result.append(gamesDaySB);
				gamesDaySB.delete(0, gamesDaySB.length());
				result.append("\n");
			}
		}
		
		return result;
	}

	@Override
	public StringBuffer dayWiseReport(String dayName) {
		StringBuffer result = new StringBuffer();
		
		if (dayName.trim().isEmpty()) {
			result.append("Error: Search field cannot be empty.");
			return result;
		}
		
		Day dayFound = dayRepo.findOne(dayName);

		if (dayFound == null) {
			result.append("Error: Day cannot be found in the System.");
			return result;
		}
		
		result.append("Day Wise Report for " + dayFound.getName() + "\n\n");
		
		result.append("Game(s) on " + dayFound.getName() + ": \n");
		
		Game[] dayGames = dayFound.getGames();
		
		for(Game game : dayGames) {
			if (game == null)
				break;
			result.append("Game " + game.getName() + "\n");
			
			StringBuilder playerInGamesSB = new StringBuilder();
			
			for(Player player : playerRepo.findAll()) {
				if (player == null)
					break;
				for (Game playerGame : player.getGames()) {
					if (playerGame == null)
						break;
					if (game.getName().equalsIgnoreCase(playerGame.getName())) {
						playerInGamesSB.append(player.getName() + "\n");
						break;
					}
				}
			}
			
			if(playerInGamesSB.length() != 0) {
				result.append("Player List:\n");
				result.append(playerInGamesSB);
				result.append("\n");
				playerInGamesSB.delete(0, playerInGamesSB.length());
			}
			else {
				result.append("Game " + game.getName() + " has no players.\n");
				result.append("\n");
			}
		}
		
		return result;
	}

}

