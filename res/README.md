## Read Me

1. **About/Overview:**
This project is to build a game with a dungeon which has caves and tunnels. Caves have treasure, thieves, pits and demons; tunnels have arrows. Player can move from one cell in the dungeon to another based on the dungeon structure.

2. **List of features:** The list of features supported by the program are mentioned below:
    - Game starts with a default dungeon and some default settings. PLayer can change the settings.
    - User can provide the size of the dungeon, interconnectivity, percentage of treasure to be added in caves, difficulty of the game which determines the number of demons(Otyughs) in the game and if the dungeon should be wrapping(one edge connected to the other side of the dungeon) or unwrapping, number of pits and thieves via settings option on the options menu.
    - Start point is set randomly and an end point is also set randomly which is at-least 5 units away from the start point.
    - Player can be moved in the dungeon based on the possible moves at a particular location of the dungeon, which is based on the structure of the dungeon. Player can move by pressing the arrows for the appropriate direction.
    - Treasure and arrows in a cell are displayed on GUI in Cell Info panel and a player can pick this treasure in the current cell by pressing "p" on keyboard. Player starts the game with 3 arrows.
    - Player can kill the demons by shooting the arrows and specifying the direction and distance to shoot the arrow. To shoot an arrow key "s" should be pressed followed the arrow key for the appropriate direction to shoot and then a popup appears to choose the distance to shoot.
    - Player can sense a strong smell if the demons are one cell away or multiple demons 2 cells away from the current player location, faint smell if there is one demon 2 cells away. Smell info is displayed on the cell info panel and as a icon on the cell.
    - Player details about the amount of treasure and arrows picked is displayed in the player info panel on GUI.
    - Player wins once the player reaches the end location and loses if player is killed by a demon.
    - Thieves are placed at random in the caves, when a player encounters a thief all the treasure collection will be stolen by thief.
    - Pits are placed at random in the caves, player can sense the pit one cell away with a weird sound coming from the pit. This sound is displayed on the cell into panel to the right. When a player falls in a pit, game is over.
    - A status bar on the top displays the results of each action performed by the player
    - By clicking on 'options' menu on the top, player can choose to play a new game, restart an existing game, change game setting or quit the game.
    - Game can also be played with text interactions without a GUI
   
3. **How to run:**
   - To launch a GUI based game, run command 'java -jar Project 5 Graphical Adventure Game.jar'.
   - To launch a text based game, run command 'java -jar Project 5 Graphical Adventure Game.jar <number_of_rows> <number_of_columns> <interconnectivity> <treasure_percentage> <difficulty> <wrapping>' from command line. Provide the parameters as per the specifications of the Dungeon class. One example command: "java -jar Project 3 Dungeon Model.jar 6 6 2 20 false"

4. **How to Use the Program:** Following are the details on how to use the program: </br>
   - Use arrow keys to move the player around the dungeon. Highlighted cell shows the location of the player
   - Press "s" followed by the arrow for the direction to shoot an arrow. A popup appears to specify the distance to shoot.
   - Press "p" to pickup anything in the current cell
   - Player and cells details will be displayed on the right of the screen and status of each action and game result will be displayed on the top 
   - Click on 'options' to get the options to restart or start the new game
   - Settings option will allow the player to set the dungeon/game related items and launches a new game automatically as per the settings
   
5. **Description of Examples:** <br/> 
    <u> Run: UI screenshots.pdf:</u> Following items are covered in the run<br/>
   - In the UI screenshots initially game begins, coloured cell shows the player current location
   - Player moved around, collected treasure and finally reached the end cell
   - Status panel shows the results that player won
   - In last screenshot game settings screen is shown, to apply new settings to the game

6. **Design/Model Changes:** No major design changes were made compared to the initial design

7. **Assumptions:** Following assumptions are made based on the interpretations of the requirements.
   - Percentage of cells with treasure can't be more than 100. If it is more than 100 program will throw an exception
   - There will be only one demon/Otyugh in one cave
   - There will be only one arrow in one cell
   - Dead Otyughs won't smell, they disappear from the cell once dead
   - There will be one thief in one cave
   - There will be one pit in one cave
 
8. **Limitations:**
   - Each cave can have a maximum of 3 units of treasure
   - Minimum of 6 rows and 6 columns are needed for the dungeon.
   - Maximum distance an arrow can travel is the total number of cells in the dungeon
   - Maximum amount of demons can be added are one third the number of cells in the dungeon
   - Maximum amount of pits and thieves can be added are 1/20th the total number of cells in the dungeon
   - The number of rows and columns values are close to each other. If the gap is relatively more the dungeon might not look clean on GUI.
   
9. **Citations:**
   - https://algs4.cs.princeton.edu/15uf/WeightedQuickUnionUF.java.html - Used some methods for weighted union find from this link
   - https://user-images.githubusercontent.com/25103430/71287503-897b8080-2336-11ea-8b31-848bfab5176e.png - Used thief icon from this link
   - https://www.pngitem.com/middle/wiRhmo_blue-circle-png-blue-dot-transparent-background-png/ - Used blue circle icon from this link for pits