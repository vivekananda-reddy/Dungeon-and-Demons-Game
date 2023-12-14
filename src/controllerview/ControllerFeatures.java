package controllerview;

import constants.Direction;


/**
 * An interface for controller to manage all GUI related operations for view.
 * Operates on Model as per the info from the view.
 */

public interface ControllerFeatures {

  /**
   * Starts the game from controller.
   */
  void playGame();

  /**
   * Calls the move in model to move the player.
   * @param direction Enum value for the direction to be moved
   */
  void movePlayer(Direction direction);

  /**
   * Calls the pick methods in model to manage the picking in dungeon.
   */
  void pickItems();

  /**
   * Handles clicks to move the player around the dungeon.
   * @param x x co-ordinate of the dungeon
   * @param y y co-ordinate of the dungeon
   */
  void handleDungeonClick(int x, int y);

  /**
   * Manages shoot operation by interacting with model based on the input form view.
   * @param direction Enum value for the direction to shoot
   * @param distance Distance for the arrow to travel
   */
  void shootArrow(Direction direction, int distance);

  /**
   * Exist the program when called.
   */
  void exitProgram();

  /**
   * Manages game settings from the view. Stores the settings for future new game operation.
   * @param dungeonRows        number of rows in the dungeon
   * @param dungeonCols        number of columns in the dungeon
   * @param interConnectivity  interconnectivity of the dungeon
   * @param wrappingNeeded     to determine wrapping or unwrapped dungeon
   * @param treasurePercentage percentage of caves to have treasure
   * @param demonCount Count of the demons to be added
   * @param pitCount count of pits to be added in dungeon
   * @param thiefCount Count of thieves to be added in dungeon
   */
  void gameSettings(int dungeonRows, int dungeonCols, int interConnectivity,
                    boolean wrappingNeeded, int treasurePercentage, int demonCount, int pitCount,
                    int thiefCount);

  /**
   * Used to initiate a new games based on the values stored form the settings.
   */
  void initiateNewGame();

  /**
   * Restarts the same game with exact same dungeon.
   */
  void restartGame();


}
