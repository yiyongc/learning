package service;

import data.*;

public interface ISchedulerService {
	
	public String createGame(Game game);
	
	public String createPlayer(Player player); 
	
	public String createDay(Day day); 
	
	public StringBuilder gameWiseReport(String gameName); 
	
	public StringBuilder playerWiseReport(String playerName); 
	
	public StringBuilder dayWiseReport(String dayName); 
	
}
