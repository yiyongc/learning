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

public class DayWiseReportTestCases {

	ISchedulerService service = new SchedulerServiceImpl(new GameRepoImpl(), new PlayerRepoImpl(), new DayRepoImpl() );
	
	@Test
	public void dayReportEmptySearch() {
		assertEquals("Error: Search field cannot be empty.", service.dayWiseReport("").toString());
	}
	
	@Test
	public void dayReportProperOneGame() {
		Game myGame = new Game("Volleyball", 10);
		Game[] gameList = { myGame };
		Day myDay = new Day("Finals", gameList);
		Player myPlayer = new Player("Tom", gameList);
		Player myPlayer2 = new Player("Tim", gameList);
		Player myPlayer3 = new Player("Tam", gameList);
		
		service.createGame(myGame);
		service.createDay(myDay);
		service.createPlayer(myPlayer);
		service.createPlayer(myPlayer2);
		service.createPlayer(myPlayer3);
		
		assertEquals("Day Wise Report for Finals\n\n"
				+ "Game(s) on Finals: \n"
				+ "Game Volleyball\n"
				+ "Player List:\n"
				+ "Tom\n"
				+ "Tim\n"
				+ "Tam\n"
				+ "\n", service.dayWiseReport("Finals").toString());
	}
	
	
	@Test
	public void dayReportProperTwoGames() {
		Game myGame = new Game("Volleyball", 10);
		Game myGame2 = new Game("Tennis", 2);
		Game[] gameList = { myGame, myGame2 };
		Day myDay = new Day("Finals", gameList);
		Player myPlayer = new Player("Tom", gameList);
		Player myPlayer2 = new Player("Tim", gameList);
		Player myPlayer3 = new Player("Tam", gameList);
		
		service.createGame(myGame);
		service.createDay(myDay);
		service.createPlayer(myPlayer);
		service.createPlayer(myPlayer2);
		service.createPlayer(myPlayer3);
		
		assertEquals("Day Wise Report for Finals\n\n"
				+ "Game(s) on Finals: \n"
				+ "Game Volleyball\n"
				+ "Player List:\n"
				+ "Tom\n"
				+ "Tim\n"
				+ "Tam\n"
				+ "\n"
				+ "Game Tennis\n"
				+ "Player List:\n"
				+ "Tom\n"
				+ "Tim\n"
				+ "Tam\n"
				+ "\n", service.dayWiseReport("Finals").toString());
	}
	
	
	@Test
	public void dayReportDayDoesNotExist() {
		assertEquals("Error: Day cannot be found in the System.", service.dayWiseReport("Tennis").toString());
	}
	
	@Test
	public void dayReportWithGamesWithNoPlayers() {
		Game myGame = new Game("Volleyball", 10);
		Game myGame2 = new Game("Tennis", 2);
		Game[] gameList = { myGame };
		Game[] gameListDay = { myGame, myGame2 };
		Day myDay = new Day("Finals", gameListDay);
		Player myPlayer = new Player("Tom", gameList);
		Player myPlayer2 = new Player("Tim", gameList);
		Player myPlayer3 = new Player("Tam", gameList);
		
		service.createGame(myGame);
		service.createDay(myDay);
		service.createPlayer(myPlayer);
		service.createPlayer(myPlayer2);
		service.createPlayer(myPlayer3);
		
		assertEquals("Day Wise Report for Finals\n\n"
				+ "Game(s) on Finals: \n"
				+ "Game Volleyball\n"
				+ "Player List:\n"
				+ "Tom\n"
				+ "Tim\n"
				+ "Tam\n"
				+ "\n"
				+ "Game Tennis\n"
				+ "Game Tennis has no players.\n"
				+ "\n", service.dayWiseReport("Finals").toString());
	}

}
