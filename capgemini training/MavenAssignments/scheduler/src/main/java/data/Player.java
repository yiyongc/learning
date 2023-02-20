package data;

public class Player {
	private String name;
	private Game[] games;
	
	public Player() {
		//default constructor	
	}
	
	public Player(String name, Game[] games) {
		this.name = name;
		this.games = games;
	}
	
	
	public Game[] getGames() {
		return games;
	}
	
	public void setGames(Game[] games) {
		this.games = games;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
