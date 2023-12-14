package dungeon;

import java.util.List;
import java.util.Map;

import constants.CellType;
import constants.DemonHealth;
import constants.DemonSmell;
import constants.Direction;
import constants.GameResult;

/**
 * A read only version of the dungeon to supply getter methods and other read only methods.
 */
public interface DungeonReadOnly {

  /**
   * Sets player start location on the dungeon randomly.
   *
   * @return Location object os the start position on the 2D grid
   */
  Location getStartPlayerLocation();

  /**
   * Sets end player location which is atleast 5 units away from start location.
   *
   * @return Location object as the end position on the 2D grid
   */
  Location getEndPlayerLocation();

  /**
   * Gets the current loction of the player in the dungeon.
   *
   * @return Location object representing the coordinated on 2D grid
   */
  Location getPlayerCurrentLocation();

  /**
   * Gets the cell type is a tunnel or a cave.
   *
   * @return Enum specifying a tunnel or a cave
   */
  CellType getCurrentLocationType();

  /**
   * Gets the treasure in current player location.
   *
   * @return A map of treasure names as key and count of treasures as values
   */
  Map<String, Integer> getCurrentLocTreasureDetails();

  /**
   * Gets the number of arrows in the current location.
   *
   * @return count of arrows in a cell
   */
  int getCurrentLocArrowDetails();

  /**
   * Gets the health of demon in the current location. Used to identify the demon is injured or
   * healthy.
   *
   * @return Enum of health state for demon
   */
  DemonHealth getCurrentLocDemonDetails();

  /**
   * Gets the possible paths from the current location.
   *
   * @return List of direction enums
   */
  List<Direction> getCurrentLocPathDetails();

  /**
   * Gets the player details about the treasure with player.
   *
   * @return A map of treasure names as key and count of treasures as values
   */
  Map<String, Integer> getPlayerDetails();


  /**
   * Gets the total number of arrows player has.
   *
   * @return count of arrows player has
   */
  int getPlayerArrowsCollected();

  /**
   * Get the name of the player.
   *
   * @return name of the player
   */
  String getPlayerName();

  /**
   * Gets the intensity of the smell from the demons in a particular cave.
   *
   * @return Enum of smell strength
   */
  DemonSmell getDemonSmell();

  /**
   * gets the result of the game in the form of an enum.
   *
   * @return Enum indicating the result of the game
   */
  GameResult getGameResult();


  /**
   * Checks if the player reached the end location to detect end the game.
   *
   * @return true if game is over else false
   */
  boolean isGameOver();

  /**
   * Gets the dungeon after making a defensive copy of cells.
   * @return read only cell objects array
   */
  CellReadOnly[][] getDungeon();

  /**
   * USed to check if a cell is visited by player.
   * @param i row co-ordinate
   * @param j column co-ordinate
   * @return true if visited else false
   */
  boolean isVisited(int i, int j);


  /**
   * Gets the sound of the pit.
   * @return true if there is a pit in next cell else false
   */
  boolean getPitSound();



}
