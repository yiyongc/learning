package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import data.Day;
import data.Game;
import repository.DayRepoImpl;
import repository.GameRepoImpl;
import repository.PlayerRepoImpl;
import service.ISchedulerService;
import service.SchedulerServiceImpl;

public class AddDayTestCases {
	
	ISchedulerService service = new SchedulerServiceImpl(new GameRepoImpl(), new PlayerRepoImpl(), new DayRepoImpl() );
	
	@Test
	public void addProperDay() {
		Game tennis = new Game("Tennis", 2);
		service.createGame(tennis);
		
		Game[] gameList = { tennis, new Game("Football", 16), new Game("Badminton",4) };
		Day myDay = new Day("Day One", gameList);
		
		assertEquals(myDay.getName() + " is successfully added.", service.createDay(myDay));
	}
	
	@Test
	public void addDayWithNoGamesInSystem() {
		Game[] gameList = { new Game("Tennis", 2), new Game("Football", 16), new Game("Badminton",4) };
		Day myDay = new Day("Day One", gameList);
		
		assertEquals("Error: Day does not contain any game in the System.", service.createDay(myDay));
	}
	
	@Test
	public void addDayWithNoGames() {
		Day myDay = new Day("Tom", new Game[5]);
		
		assertEquals("Error: Day does not contain any game in the System.", service.createDay(myDay));
	}
	
	@Test
	public void addDayWithoutName() {
		Game tennis = new Game("Tennis", 2);
		service.createGame(tennis);
		
		Game[] gameList = { new Game("Tennis", 2), new Game("Football", 16), new Game("Badminton",4) };
		Day myDay = new Day("", gameList);
		
		assertEquals("Error: Day does not have a name.", service.createDay(myDay));
	}
	
	@Test
	public void addDuplicateDay() {
		Game tennis = new Game("Tennis", 2);
		service.createGame(tennis);
		
		Game[] gameList = { tennis, new Game("Football", 16), new Game("Badminton",4) };
		Day myDay = new Day("Tom", gameList);
		service.createDay(myDay);
		
		assertEquals("Error: Day already exists in the repository.", service.createDay(myDay));
	}
	
	@Test
	public void addNullDay() {
		Day myDay = null;
		
		assertEquals("Error: Day is a null object.", service.createDay(myDay));
	}
	
	@Test
	public void addDayWithNoGameList() {
		Day myDay = new Day("Tom", null);
		
		assertEquals("Error: Day does not have any games.", service.createDay(myDay));
	}
	
}
