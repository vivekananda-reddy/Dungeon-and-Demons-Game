package players;

import java.util.HashMap;
import java.util.Map;

import dungeon.Location;

/**
 * A class to track player and operations related to a player.
 * Used to store treasures for a player and move the players.
 */
public class Player implements GenericPlayer {

  private final String name;
  private Location location;
  private Map<String, Integer> treasureWithPlayer;
  private int arrowsCollected;

  /**
   * Creates a player object with name of the player as parameter.
   * @param name name of the player
   */
  public Player(String name) {
    if (name == null || name.equals("")) {
      throw new IllegalArgumentException("Name can't be null or blank");
    }

    location = null;
    treasureWithPlayer = new HashMap<>();
    this.name = name;
    this.arrowsCollected = 3;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public Location getPlayerLocation() {
    if (location == null) {
      throw new IllegalStateException("Player location is not set");
    }
    return location;
  }

  @Override
  public void setLocation(Location movePlayerTo) {
    if (movePlayerTo == null) {
      throw new IllegalArgumentException("Move location is null");
    }
    location = movePlayerTo;
  }

  @Override
  public Map<String, Integer> getTreasure() {
    return new HashMap<>(treasureWithPlayer);
  }

  @Override
  public void pickTreasure(Map<String, Integer> pickedTreasures) {
    if (pickedTreasures == null) {
      throw new IllegalArgumentException("picked treasures are null");
    }
    for (String treasure : pickedTreasures.keySet()) {
      treasureWithPlayer.put(treasure,treasureWithPlayer.getOrDefault(treasure, 0)
              + pickedTreasures.get(treasure));
    }

  }

  @Override
  public void addArrow() {
    arrowsCollected++;

  }

  @Override
  public int getArrowCount() {
    return arrowsCollected;
  }

  @Override
  public void removeArrow() {
    if (arrowsCollected < 1) {
      throw new IllegalStateException("No arrows left with player");
    }
    arrowsCollected--;
  }

  @Override
  public Map<String, Integer> removeTreasure() {
    Map<String, Integer> copy = new HashMap<>(treasureWithPlayer);
    treasureWithPlayer = new HashMap<>();
    return copy;
  }
}
