package dungeon;

import constants.CellType;
import constants.Direction;

import java.util.List;
import java.util.Map;


/**
 * Read only version of the cell to return the info related to the cell.
 */
public interface CellReadOnly {

  /**
   * Gets the type of the cell.
   * @return type of the cell
   */
  CellType getType();


  /**
   * Get the treasure in a cell.
   * @return a map of treasure name as key and count of treasure
   */
  Map<String, Integer> getTreasure();



  /**
   * Query if a cell contains treasure.
   * @return true if there is treasure in the cell else returns false
   */
  boolean containsTreasure();

  /**
   * gets all possible directions to traverse from the cell.
   * @return List of all possible direction enums
   */
  List<Direction> getPossibleDirections();

  /**
   * Gets the count of arrows in a cell.
   * @return count of arrows in cave
   */
  int getArrowCount();

  /**
   * Specifies if there is a demon in a cave.
   * @return true if there is a demon in a cave, else returns false
   */
  boolean containsDemons();

  /**
   * Checks if the cell is visited.
   * @return true if visited elase false
   */
  boolean isVisited();

  /**
   * Checks if a cell contains a thief.
   * @return true if there is a thief else false
   */
  boolean containsThief();

  /**
   * Checks if there is a pit.
   * @return true if there is a pit else false
   */
  boolean containsPit();

}
