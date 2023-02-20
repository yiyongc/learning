package data;

public class Game {
	private String name;
	private int noOfPlayers;
	
	public Game() {
		//No argument constructor
	}
	
	public Game(String name, int noOfPlayers) {
		this.name = name;
		this.noOfPlayers = noOfPlayers;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
		
	public void setNoOfPlayers(int noOfPlayers) {
		this.noOfPlayers = noOfPlayers;
	}
	
	public int getNoOfPlayers() {
		return noOfPlayers;
	}
	
}
