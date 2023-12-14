package controller;

import java.util.Scanner;

import dungeon.Dungeon;


/**
 * Represents all commands used in the controller. MAde package default as this is accessed
 * only by controller.
 */
interface ControllerCommands {

  /**
   * Used to execute the command.
   * @param dungeon model object
   * @param scan input object to take input form user
   * @param out output object to give output back to user
   */
  void execute(Dungeon dungeon, Scanner scan, Appendable out);
}
