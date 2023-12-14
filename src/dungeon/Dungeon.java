package dungeon;


import constants.DemonHit;
import constants.Direction;

import java.util.Map;


/**
 * Interface to create and maintain all operations related to the dungeon like creating player,
 * creating dungeon as per user specifications, moving player etc. are performed.
 */
public interface Dungeon extends DungeonReadOnly {


  /**
   * Moves the player in the specified direction.
   *
   * @param moveTo Direction enum specifying the direction to move
   * @throws IllegalArgumentException if the move is illegal for a particular cell
   */
  void movePlayer(Direction moveTo);


  /**
   * Used to check if the player encountered a demon in a cell. If player encounters an injured
   * demon, calculated the chance of player survival. Determined the end of game based on
   * encountering the demon and sets the Game Result accordinglyMust be called after moving the
   * player.
   *
   * @return true if there is a demon in the cell, else false
   */
  boolean endGameCheck();

  /**
   * Picks all the treasure in a particular cell player is in.
   */
  void pickTreasure();

  /**
   * Picks all arrows in a particular cell player is in.
   */
  void pickArrows();

  /**
   * Shoots an arrow in the specified direction to the specified distance. Distance is the
   * number of caves an arrow can travel. Indicates the result of the arrow hit in the form of an
   * Enum.
   *
   * @param shootDirection Direction to be shot in the form of enum
   * @param distance       number of caves arrow to travel
   * @return the result of the arrow shoot
   * @throws IllegalArgumentException if the distance is more than number of cells in the dungeon
   *                                  or less than 1 or if initial shoot direction is not a valid
   *                                  path from the current cell
   * @throws IllegalStateException    if player is out of arrows
   */
  DemonHit shootArrow(Direction shootDirection, int distance);

  /**
   * Checks if the thief is in the cell and deducts the treasure form the player. Returns null if
   * there is no thief in the cell.
   * @return treasure in the form of Map
   */
  Map<String, Integer> thiefCheck();

  /**
   * Checks if the pit is in the cell. Marks game over and sets game state as lose if there is
   * a pit.
   * @return true if there is a put else false
   */
  boolean pitCheck();


  /**
   * Reloads the initial copy of the dungeon to restore it back to the original state.
   */
  void resetModel();


}
