package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import data.Game;
import data.Player;
import repository.DayRepoImpl;
import repository.GameRepoImpl;
import repository.PlayerRepoImpl;
import service.ISchedulerService;
import service.SchedulerServiceImpl;

public class AddPlayerTestCases {
		
	ISchedulerService service = new SchedulerServiceImpl(new GameRepoImpl(), new PlayerRepoImpl(), new DayRepoImpl() );
	String tennisString = "Tennis";
	String footballString = "Football";
	String badmintonString = "Badminton";
	
	@Test
	public void addProperPlayer() {
		Game tennis = new Game(tennisString, 2);
		service.createGame(tennis);
		
		Game[] gameList = { tennis, new Game(footballString, 16), new Game(badmintonString,4) };
		Player myPlayer = new Player("Tom", gameList);
		
		assertEquals("Player " + myPlayer.getName() + " is successfully added.", service.createPlayer(myPlayer));
	}
	
	@Test
	public void addPlayerWithNoGamesInSystem() {
		Game[] gameList = { new Game(tennisString, 2), new Game(footballString, 16), new Game(badmintonString,4) };
		Player myPlayer = new Player("Tom", gameList);
		
		assertEquals("Error: Player does not play any game in the System.", service.createPlayer(myPlayer));
	}
	
	@Test
	public void addPlayerWithNoGames() {
		Player myPlayer = new Player("Tom", new Game[5]);
		
		assertEquals("Error: Player does not play any game in the System.", service.createPlayer(myPlayer));
	}
	
	@Test
	public void addPlayerWithoutName() {
		Game tennis = new Game(tennisString, 2);
		service.createGame(tennis);
		
		Game[] gameList = { new Game(tennisString, 2), new Game(footballString, 16), new Game(badmintonString,4) };
		Player myPlayer = new Player("", gameList);
		
		assertEquals("Error: Player does not have a name.", service.createPlayer(myPlayer));
	}
	
	@Test
	public void addDuplicatePlayer() {
		Game tennis = new Game(tennisString, 2);
		service.createGame(tennis);
		
		Game[] gameList = { tennis, new Game(footballString, 16), new Game(badmintonString,4) };
		Player myPlayer = new Player("Tom", gameList);
		service.createPlayer(myPlayer);
		
		assertEquals("Error: Player already exists in the repository.", service.createPlayer(myPlayer));
	}
	
	@Test
	public void addNullPlayer() {
		Player myPlayer = null;
		
		assertEquals("Error: Player is a null object.", service.createPlayer(myPlayer));
	}
	
	@Test
	public void addPlayerWithNoGameList() {
		Player myPlayer = new Player("Tom", null);
		
		assertEquals("Error: Player does not have any games.", service.createPlayer(myPlayer));
	}
	
}
