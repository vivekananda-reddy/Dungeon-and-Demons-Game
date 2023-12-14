package controller;

import java.io.IOException;
import java.util.Scanner;

import constants.Direction;
import constants.GameResult;
import dungeon.Dungeon;


/**
 * Handles move command. Made it package private as it is accessed only by the controller.
 */
class Move implements ControllerCommands {

  @Override
  public void execute(Dungeon dungeon, Scanner scan, Appendable out) {
    try {
      while (true) {
        out.append("Direction of move - ");
        String move = scan.next();

        if (move.equalsIgnoreCase("n") || move.equalsIgnoreCase("north")) {
          try {

            dungeon.movePlayer(Direction.NORTH);

          }

          catch (IllegalArgumentException e) {
            out.append("Can't move North from this location. Enter a valid move\n");
            continue;
          }

        }

        else if (move.equalsIgnoreCase("s") || move.equalsIgnoreCase("south")) {
          try {

            dungeon.movePlayer(Direction.SOUTH);
          }

          catch (IllegalArgumentException e) {
            out.append("Can't move South from this location. Enter a valid move\n");
            continue;
          }

        }

        else if (move.equalsIgnoreCase("west") || move.equalsIgnoreCase("w")) {
          try {

            dungeon.movePlayer(Direction.WEST);
          }

          catch (IllegalArgumentException e) {
            out.append("Can't move West from this location. Enter a valid move\n");
            continue;
          }

        }

        else if (move.equalsIgnoreCase("e") || move.equalsIgnoreCase("east")) {
          try {

            dungeon.movePlayer(Direction.EAST);
          }

          catch (IllegalArgumentException e) {
            out.append("Can't move East from this location. Enter a valid move\n");
            continue;
          }

        }

        else {
          out.append("Enter a valid direction(N S E W)\n");
          continue;
        }

        break;
      }

      if (dungeon.endGameCheck()) {
        out.append("You encountered a Demon in this cell\n");
        if (dungeon.getGameResult() == GameResult.PLAYING) {
          out.append("You are lucky it's an injured Demon, it didn't kill you\n");
        }

        else if (dungeon.getGameResult() == GameResult.LOSE) {
          out.append("Demon killed you\n");
          out.append("**You " + dungeon.getGameResult().name() + "**\n");
        }

        else {
          out.append("You are lucky it's an injured Demon, it didn't kill you\n");
          out.append("You reached the end cave\n");
          out.append("\n**You " + dungeon.getGameResult().name() + "**\n");
        }

      }

      else {
        if (dungeon.getGameResult() == GameResult.WIN) {
          out.append("You reached the end cave\n");
          out.append("\n**You " + dungeon.getGameResult().name() + "**\n");
        }
      }


    }
    catch (IOException e) {
      throw new IllegalStateException("Append failed", e);

    }

  }
}
