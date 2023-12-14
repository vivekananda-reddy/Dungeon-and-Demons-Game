package controller;

import java.io.IOException;
import java.util.Scanner;

import constants.DemonHit;
import constants.Direction;
import dungeon.Dungeon;


/**
 * Handles shoot command. Made it package private as it is accessed only by the controller.
 */
class Shoot implements ControllerCommands {
  @Override
  public void execute(Dungeon dungeon, Scanner scan, Appendable out) {
    try {
      if (dungeon.getPlayerArrowsCollected() == 0) {
        out.append("You are out of arrows. Explore to find arrows\n");
        return;
      }
      DemonHit result;

      while (true) {


        out.append("Enter direction to shoot - ");
        String direction = scan.next();
        int distance;


        if (direction.equalsIgnoreCase("n") || direction.equalsIgnoreCase("north")) {
          try {
            while (true) {
              out.append("Enter distance to shoot - ");
              try {
                distance = Integer.parseInt(scan.next());

              } catch (NumberFormatException e) {
                out.append("Enter a valid number for distance\n");
                continue;
              }
              break;
            }
            result = dungeon.shootArrow(Direction.NORTH, distance);

          } catch (IllegalArgumentException | IllegalStateException e) {
            out.append(e.getMessage() + "\n");
            continue;
          }

        } else if (direction.equalsIgnoreCase("s") || direction.equalsIgnoreCase("south")) {
          try {

            while (true) {
              out.append("Enter distance to shoot - ");
              try {
                distance = Integer.parseInt(scan.next());

              } catch (NumberFormatException e) {
                out.append("Enter a valid number for distance\n");
                continue;
              }
              break;
            }
            result = dungeon.shootArrow(Direction.SOUTH, distance);
          } catch (IllegalArgumentException | IllegalStateException e) {
            out.append(e.getMessage() + "\n");
            continue;
          }

        } else if (direction.equalsIgnoreCase("west") || direction.equalsIgnoreCase("w")) {
          try {

            while (true) {
              out.append("Enter distance to shoot - ");
              try {
                distance = Integer.parseInt(scan.next());

              } catch (NumberFormatException e) {
                out.append("Enter a valid number for distance\n");
                continue;
              }
              break;
            }

            result = dungeon.shootArrow(Direction.WEST, distance);
          } catch (IllegalArgumentException | IllegalStateException e) {
            out.append(e.getMessage() + "\n");
            continue;
          }

        } else if (direction.equalsIgnoreCase("e") || direction.equalsIgnoreCase("east")) {
          try {

            while (true) {
              out.append("Enter distance to shoot - ");
              try {
                distance = Integer.parseInt(scan.next());

              } catch (NumberFormatException e) {
                out.append("Enter a valid number for distance\n");
                continue;
              }
              break;
            }

            result = dungeon.shootArrow(Direction.EAST, distance);
          } catch (IllegalArgumentException | IllegalStateException e) {
            out.append(e.getMessage() + "\n");
            continue;
          }

        } else {
          out.append("Enter a valid direction(N S E W)\n");
          continue;
        }

        break;
      }

      if (result == DemonHit.MISS ) {
        out.append("You shoot into darkness\n");
      }
      else if (result == DemonHit.HIT ) {
        out.append("You hit and injured a demon\n");
      }
      else if (result == DemonHit.KILL ) {
        out.append("You killed a demon\n");
      }


    }
    catch (IOException e) {
      throw new IllegalStateException("Append failed", e);
    }

  }
}
