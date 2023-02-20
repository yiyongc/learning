package data;

public class Game {
	private String name;
	private int noOfPlayers;
	
	public Game(String name) {
		this.name = name;
	}
	
	public Game(String name, int noOfPlayers) {
		this.name = name;
		this.noOfPlayers = noOfPlayers;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getNoOfPlayers() {
		return noOfPlayers;
	}
	
	public void setNoOfPlayers(int noOfPlayers) {
		this.noOfPlayers = noOfPlayers;
	}
	
}
