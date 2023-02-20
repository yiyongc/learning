# This is Read Me file for the Scheduler Program

The program is an application which generates various reports based on schedule of the games e.g. Olympic Games Schedule.  
Current program uses Arrays instead of other data structures for learning purposes.  
 
# Functionalities 

Application accepts information about Games, Players and Days on which games are scheduled.  

Application should generate following reports.  
  
	1.	Game wise report – Given the name of the Game , display Players who are playing the Game and Days on which this Game is scheduled.  
	2.	Player wise report – Given the name of the Player, display the Games that he plays and the days on which these games are scheduled.  
	3.	 Day wise report – Given the name of the Day , display the Games which are scheduled on that day , the players who play these games.  

# Classes
## Game  

A game has a name and the number of players which can play the game. This will be the primary class which is acted upon.  

## Day  

A day has a name and a game list which indicates what games are scheduled for the day.  

## Player  

A player has a name and a game list which indicates what games the player is participating in according to the schedule.  

# Testing Procedure  

Application is developed on Spring Tool Suite and tested using Junit. Test cases text file is provided.  

# Other Information  

Application is not expected to create the schedule. Schedule needs to be fed to the system.  
Appropriate Validations should be done e.g. No Duplicate Players, Games, Days etc.  


