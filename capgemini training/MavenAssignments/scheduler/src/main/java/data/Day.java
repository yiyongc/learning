package data;

public class Day {
	private String name;
	private Game[] games;
	
	public Day() {
		//No argument constructor
	}
	
	public Day(String name, Game[] games) {
		this.name = name;
		this.games = games;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Game[] getGames() {
		return games;
	}
	
	public void setGames(Game[] games) {
		this.games = games;
	}
	
}
