package dungeon;

import constants.Direction;
import demons.Demon;
import treasures.Treasure;

import java.util.Map;


/**
 * Represents each cell in the 2D grid of the dungeon. Tracks all the operations related to the
 * cell and maintains connections between cells.
 */
public interface Cell extends CellReadOnly {

  /**
   * Method to set a cell as a tunnel.
   *
   * @throws IllegalStateException if the cell has treasure or if cell doesn't have exact 2
   *                               connections
   */
  void setAsTunnel();


  /**
   * Method to add the treasure to the cell.
   * @param toBeAddedTreasure treasure to be added to cell
   * @throws IllegalStateException if the cell is a tunnel
   */
  void addTreasure(Treasure toBeAddedTreasure);



  /**
   * Removes treasure completly from the cell.
   * @return a map of treasure name as key and count of each treasure as value
   */
  Map<String, Integer> removeTreasure();

  /**
   * Forms a connection by adding the provided direction.
   * @param possibleDirections direction in which connection should be formed
   */
  void formConnection(Direction possibleDirections);


  /**
   * Adds an arrow to the cell.
   */
  void addArrow();



  /**
   * Removes all arrows in the cave.
   * @return number of arrows removed
   */
  int removeArrows();



  /**
   * returns the demon object in the cave.
   * @return Demon object in the current cell
   * @throws IllegalStateException if there is no demon in the cell
   */
  Demon getDemon();

  /**
   * Adds a demon to the cave and not a tunnel.
   * @throws IllegalStateException if the cell is a tunnel or if cell already has a demon
   */
  void addDemon();

  /**
   * Removes a demon from the cell.
   * @return removed demon object
   * @throws IllegalStateException if there is no demon in the cell
   */
  Demon removeDemon();

  /**
   * Marks the cell visited if the player is in the cell.
   */
  void markVisited();

  /**
   * Adds a thief to the Cell.
   */
  void addThief();

  /**
   * Adds a pit to the cell.
   */
  void addPit();

}
