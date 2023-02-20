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

public class PlayerWiseReportTestCases {

	ISchedulerService service = new SchedulerServiceImpl(new GameRepoImpl(), new PlayerRepoImpl(), new DayRepoImpl() );
	
	@Test
	public void playerReportEmptySearch() {
		assertEquals("Error: Search field cannot be empty.", service.playerWiseReport("").toString());
	}
	
	@Test
	@SuppressWarnings("all")
	public void playerReportProperOneGame() {
		Game myGame = new Game("Tennis", 2);
		Game[] gameList = { myGame };
		Day myDay = new Day("Day One", gameList);
		Day myDay2 = new Day("Day Two", gameList);
		Player myPlayer = new Player("Tom", gameList);
		
		service.createGame(myGame);
		service.createDay(myDay);
		service.createDay(myDay2);
		service.createPlayer(myPlayer);
		
		assertEquals("Player Wise Report for Player Tom\n\n"
				+ "Tom's Game(s): \n"
				+ "Game: Tennis\n"
				+ "Game is played on Day One\n"
				+ "Game is played on Day Two\n"
				+ "\n", service.playerWiseReport("Tom").toString());
	}
	
	@Test
	@SuppressWarnings("all")
	public void playerReportProperTwoGames() {
		Game gameTennis = new Game("Tennis", 2);
		Game gameTennisDoubles = new Game("Tennis Doubles", 4);
		Game[] gameList = { gameTennis, gameTennisDoubles };
		Day dayOne = new Day("Day One", gameList);
		Day dayTwo = new Day("Day Two", gameList);
		Player myPlayer = new Player("Tom", gameList);
		
		service.createGame(gameTennis);
		service.createDay(dayOne);
		service.createDay(dayTwo);
		service.createPlayer(myPlayer);
		
		assertEquals("Player Wise Report for Player Tom\n\n"
				+ "Tom's Game(s): \n"
				+ "Game: Tennis\n"
				+ "Game is played on Day One\n"
				+ "Game is played on Day Two\n"
				+ "\n"
				+ "Game: Tennis Doubles\n"
				+ "Game is played on Day One\n"
				+ "Game is played on Day Two\n"
				+ "\n", service.playerWiseReport("Tom").toString());
	}
	
	@Test
	public void playerReportPlayerDoesNotExist() {
		assertEquals("Error: Player cannot be found in the System.", service.playerWiseReport("Mr Kim").toString());
	}
	
	@Test
	@SuppressWarnings("all")
	public void playerReportWithoutGamesNotInSystem() {
		Game myGame = new Game("Tennis", 2);
		Game myGame2 = new Game("Tennis Doubles", 4);
		Game[] gameListForPlayer = { myGame, myGame2 };
		Game[] gameList = { myGame };
		Day myDay = new Day("Day One", gameList);
		Day myDay2 = new Day("Day Two", gameList);
		Player myPlayer = new Player("Tom", gameListForPlayer);
		
		service.createGame(myGame);
		service.createDay(myDay);
		service.createDay(myDay2);
		service.createPlayer(myPlayer);
		
		assertEquals("Player Wise Report for Player Tom\n\n"
				+ "Tom's Game(s): \n"
				+ "Game: Tennis\n"
				+ "Game is played on Day One\n"
				+ "Game is played on Day Two\n"
				+ "\n", service.playerWiseReport("Tom").toString());
	}

}
