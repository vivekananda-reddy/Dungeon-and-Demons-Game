package players;

import java.util.Map;

import dungeon.Location;


/**
 * Player interface to track player and player related operations.
 */
public interface GenericPlayer {

  /**
   * Gets name of the player.
   * @return name of palayer
   */
  String getName();

  /**
   * Current location of the player.
   * @return Location object of the current location
   */
  Location getPlayerLocation();

  /**
   * sets location of the player. Used to move player as well.
   * @param movePlayerTo new location where player needs to be moved
   */
  void setLocation(Location movePlayerTo);

  /**
   * Gets treasure player collected.
   * @return A Map of treasure name as key and amount of each treasure as value
   */
  Map<String, Integer> getTreasure();

  /**
   * Picks the treasure.
   * @param pickedTreasures A Map of treasure name as key and amount of each treasure as value
   */
  void pickTreasure(Map<String, Integer> pickedTreasures);

  /**
   * Add an arrow to the player.
   */
  void addArrow();

  /**
   * Gets the count of arrows player has.
   * @return count of arrows player has
   */
  int getArrowCount();

  /**
   * Removes an arrow from the player.
   * @throws IllegalStateException when player doesn't have any arrows left
   */
  void removeArrow();


  /**
   * Removes all the treasure with the player.
   * @return Treasure in the form of a map with treasure name and count of each item
   */
  Map<String, Integer> removeTreasure();


}
