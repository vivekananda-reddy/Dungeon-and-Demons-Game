package controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;

import constants.DemonSmell;
import dungeon.Dungeon;


/**
 * Implements the controller to handle user inputs and interaction with model. Communicates
 * with the user with all game related info and state.
 */
public class ControllerImpl implements Controller {

  private final Scanner scan;
  private final Appendable out;


  /**
   * Constructs a controller object with readable type as input and appendable type as output for
   * user interactions.
   * @param in used for getting the input from view/user
   * @param out used to communicate back to the user
   */
  public ControllerImpl(Readable in, Appendable out) {
    if (in == null || out == null) {
      throw new IllegalArgumentException("Inputs can't be null");
    }
    this.scan = new Scanner(in);
    this.out = out;
  }

  private void printInfo(Dungeon dungeon) {

    try {

      out.append("You are in a " + dungeon.getCurrentLocationType());
      out.append("\n");
      out.append("Doors lead to :" + dungeon.getCurrentLocPathDetails());
      out.append("\n");
      if (dungeon.getCurrentLocTreasureDetails() != null && dungeon.getCurrentLocTreasureDetails()
              .size() > 0) {
        out.append("Treasure found in this cell:" + dungeon.getCurrentLocTreasureDetails()
                .toString());
        out.append("\n");
      }
      if (dungeon.getCurrentLocArrowDetails() > 0) {
        out.append("There is an arrow here");
        out.append("\n");
      }
      if (dungeon.getDemonSmell() == DemonSmell.WEAK) {
        out.append("You are getting a faint smell of a demon");
        out.append("\n");
      }

      if (dungeon.getDemonSmell() == DemonSmell.STRONG) {
        out.append("You are getting a strong smell of a demon");
        out.append("\n");
      }

      out.append("You have following items:");

      if (dungeon.getPlayerDetails() != null && dungeon.getPlayerDetails().size() > 0) {
        out.append(dungeon.getPlayerDetails().toString());
        out.append(", ");
      }
      else {
        out.append("No treasure collected, look around for treasure.");

      }

      out.append("Arrows:" + dungeon.getPlayerArrowsCollected());
      out.append("\n");

    }
    catch (IOException e) {
      throw new IllegalStateException("Append failed", e);

    }

  }


  @Override
  public void play(Dungeon dungeon) {

    if (dungeon == null) {
      throw new IllegalArgumentException("Null parameter passed to method");
    }

    ControllerCommands cmd;
    Map<String, Function<Dungeon, ControllerCommands>> commandMap;

    commandMap = new HashMap<>();
    commandMap.put("m", d -> new Move());
    commandMap.put("s", d -> new Shoot());
    commandMap.put("p", d -> new Pick());

    try {
      out.append("============Welcome to the game============\n");

      while (!dungeon.isGameOver()) {

        out.append("******************************************\n");
        printInfo(dungeon);

        out.append("Choose an action: Move, Pickup, Shoot, Quit (M-P-S-Q) - ");
        String action = scan.next();

        if (action.equalsIgnoreCase("q") || action.equalsIgnoreCase("quit")) {
          out.append("\n**Forfeited the game**\n");
          break;
        }

        Function<Dungeon, ControllerCommands> cmdObject = commandMap.getOrDefault(
                action.toLowerCase(), null);
        if (cmdObject == null) {
          out.append("Invalid action\n");

        }
        else {
          cmd = cmdObject.apply(dungeon);
          cmd.execute(dungeon,scan,out);
        }

      }

      out.append("==>Game Over<==");

    }
    catch (IOException e) {
      throw new IllegalStateException("Append failed", e);
    }

  }

}
