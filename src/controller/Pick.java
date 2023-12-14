package controller;

import java.io.IOException;
import java.util.Scanner;

import dungeon.Dungeon;


/**
 * Handles pick command. Made it package private as it is accessed only by the controller.
 */
class Pick implements ControllerCommands {

  @Override
  public void execute(Dungeon dungeon, Scanner scan, Appendable out) {
    try {
      if (dungeon.getCurrentLocTreasureDetails().size() == 0 && dungeon
              .getCurrentLocArrowDetails() == 0) {
        out.append("This cell is empty. Nothing to pick\n");
        return;
      }

      if (dungeon.getCurrentLocTreasureDetails().size() != 0) {
        out.append("picked treasure: " + dungeon.getCurrentLocTreasureDetails() + "\n");

        dungeon.pickTreasure();
      }

      else {
        out.append("No treasure here\n");
      }

      if (dungeon.getCurrentLocArrowDetails() != 0) {
        out.append("picked an arrow\n");
        dungeon.pickArrows();
      }

      else {
        out.append("No arrows here\n");
      }


    }
    catch (IOException e) {
      throw new IllegalStateException("Append failed", e);

    }

  }
}
