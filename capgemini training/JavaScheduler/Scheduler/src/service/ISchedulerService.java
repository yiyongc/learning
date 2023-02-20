package service;

import data.*;

public interface ISchedulerService {
	
	public String createGame(Game game);
	
	public String createPlayer(Player player); 
	
	public String createDay(Day day); 
	
	public StringBuffer gameWiseReport(String gameName); 
	
	public StringBuffer playerWiseReport(String playerName); 
	
	public StringBuffer dayWiseReport(String dayName); 
	
}
