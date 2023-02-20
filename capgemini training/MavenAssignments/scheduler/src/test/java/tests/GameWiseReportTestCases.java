package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import data.Day;
import data.Game;
import data.Player;
import repository.DayRepoImpl;
import repository.GameRepoImpl;
import repository.PlayerRepoImpl;
import service.ISchedulerService;
import service.SchedulerServiceImpl;

public class GameWiseReportTestCases {
	
	ISchedulerService service = new SchedulerServiceImpl(new GameRepoImpl(), new PlayerRepoImpl(), new DayRepoImpl() );
	
	@Test
	public void gameReportEmptySearch() {
		assertEquals("Error: Search field cannot be empty.", service.gameWiseReport("").toString());
	}
	
	@Test
	@SuppressWarnings("all")
	public void gameReportProperOneDay() {
		Game myGame = new Game("Tennis", 2);
		Game[] gameList = { myGame };
		Day myDay = new Day("Day One", gameList);
		Player myPlayer = new Player("Tom", gameList);
		
		service.createGame(myGame);
		service.createDay(myDay);
		service.createPlayer(myPlayer);
		
		assertEquals("Game Wise Report for Game Tennis\n\n"
				+ "Player(s) who play Tennis: \n"
				+ "Tom\n\n"
				+ "Day(s) on which Tennis is scheduled: \n"
				+ "Day One\n\n", service.gameWiseReport("tennis").toString());
	}
	
	@Test
	@SuppressWarnings("all")
	public void gameReportProperTwoDays() {
		Game gameTennis = new Game("Tennis", 2);
		Game[] gameList = { gameTennis };
		Day dayOne = new Day("Day One", gameList);
		Day dayFinals = new Day("Finals", gameList);
		Player myPlayer = new Player("Tom", gameList);
		
		service.createGame(gameTennis);
		service.createDay(dayOne);
		service.createDay(dayFinals);
		service.createPlayer(myPlayer);
		
		assertEquals("Game Wise Report for Game Tennis\n\n"
				+ "Player(s) who play Tennis: \n"
				+ "Tom\n\n"
				+ "Day(s) on which Tennis is scheduled: \n"
				+ "Day One\n"
				+ "Finals\n\n", service.gameWiseReport("tennis").toString());
	}
	
	@Test
	public void gameReportGameDoesNotExist() {
		assertEquals("Error: Game cannot be found in the System.", service.gameWiseReport("Tennis").toString());
	}

	@Test
	@SuppressWarnings("all")
	public void gameReportGameExistsButDayAndPlayerDoesnt() {
		Game myGame = new Game("Tennis", 2);
		
		service.createGame(myGame);
		
		assertEquals("Game Wise Report for Game Tennis\n\n"
				+ "There are no players who play the game.\n\n"
				+ "The game is not scheduled on any day.\n\n", service.gameWiseReport("tennis").toString());
	}
	
	@Test
	@SuppressWarnings("all")
	public void gameReportDayExistsButPlayerDoesnt() {
		Game myGame = new Game("Tennis", 2);
		Game[] gameList = { myGame };
		Day myDay = new Day("Day One", gameList);
		
		service.createGame(myGame);
		service.createDay(myDay);
		
		assertEquals("Game Wise Report for Game Tennis\n\n"
				+ "There are no players who play the game.\n\n"
				+ "Day(s) on which Tennis is scheduled: \n"
				+ "Day One\n\n", service.gameWiseReport("tennis").toString());
	}
	
	@Test
	@SuppressWarnings("all")
	public void gameReportPlayerExistsButDayDoesnt() {
		Game myGame = new Game("Tennis", 2);
		Game[] gameList = { myGame };
		Player myPlayer = new Player("Tom", gameList);
		
		service.createGame(myGame);
		service.createPlayer(myPlayer);
		
		assertEquals("Game Wise Report for Game Tennis\n\n"
				+ "Player(s) who play Tennis: \n"
				+ "Tom\n\n"
				+ "The game is not scheduled on any day.\n\n", service.gameWiseReport("tennis").toString());
	}
}
