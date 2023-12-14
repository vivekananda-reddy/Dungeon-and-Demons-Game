package controller;

import dungeon.Dungeon;

/**
 * Represents a controller interface to handle moves from the player by executing them using the
 * model. Conveys the game state and other game details to user.
 */
public interface Controller {

  /**
   * Executes the game play. When the game is out this method exits.
   * @param dungeon model object
   */
  void play(Dungeon dungeon);
}
