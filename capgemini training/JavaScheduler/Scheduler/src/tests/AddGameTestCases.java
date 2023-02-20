package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import data.Game;
import repository.*;
import service.ISchedulerService;
import service.SchedulerServiceImpl;

public class AddGameTestCases {
	
	ISchedulerService service = new SchedulerServiceImpl(new GameRepoImpl(), new PlayerRepoImpl(), new DayRepoImpl() );
	
	@Test
	public void addProperGame() {
		Game myGame = new Game("Tennis", 2);
		
		assertEquals("Game " + myGame.getName() + " is successfully added.", service.createGame(myGame));
	}
	
	@Test
	public void addGameWithoutName() {
		Game myGame = new Game("", 2);
		
		assertEquals("Error: Game does not have a name.", service.createGame(myGame));
	}
	
	@Test
	public void addGameWithoutPlayers() {
		Game myGame = new Game("Golf", 0);
		
		assertEquals("Error: Game cannot have no players.", service.createGame(myGame));
	}
	
	@Test
	public void addGameWithNegativePlayerNumbers() {
		Game myGame = new Game("Golf", -2);
		
		assertEquals("Error: Game cannot have no players.", service.createGame(myGame));
	}
	
	@Test
	public void addDuplicateGame() {
		Game myGame = new Game("Tennis", 2);
		service.createGame(myGame);
		
		assertEquals("Error: Game already exists in the repository.", service.createGame(myGame));
	}
	
	@Test
	public void addNullGame() {
		Game myGame = null;
		
		assertEquals("Error: Game is a null object.", service.createGame(myGame));
	}
	
}
