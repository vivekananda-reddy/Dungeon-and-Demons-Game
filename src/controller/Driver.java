package controller;

import controllerview.ControllerFeatures;
import controllerview.ControllerViewImpl;
import controllerview.MasterView;
import controllerview.View;
import dungeon.Dungeon;
import dungeon.DungeonImpl;
import randoms.RandomAuto;
import randoms.RandomControlled;
import randoms.RandomGenerate;

import java.io.InputStreamReader;


/**
 * A driver class to demonstrate the working of the game.
 */
public class Driver {

  private void guiGame() {

    final int dungeonRows = 7;
    final int dungeonCols = 7;
    final int interConnectivity = 2;
    final boolean wrappingNeeded = true;
    final int treasurePercentage = 30;
    final int demonCount = 4;
    final int pitCount = 1;
    final int thiefCount = 1;


    //RandomGenerate randomControl = new RandomControlled();
    RandomGenerate random = new RandomAuto();
    Dungeon dungeon = new DungeonImpl(random, dungeonRows, dungeonCols, interConnectivity,
            wrappingNeeded, treasurePercentage, demonCount, pitCount, thiefCount,"player1");

    View view = new MasterView(dungeon);
    ControllerFeatures controller = new ControllerViewImpl(dungeon, view, random);

    controller.playGame();
  }

  private void textGame(String[] args) {


    if (args == null || args.length == 0) {
      throw new IllegalArgumentException("Dungeon construction values not passed in command line");
    }

    if (args.length != 6) {
      throw new IllegalArgumentException("Insufficient number of arguments passed");
    }

    final int dungeonRows;
    final int dungeonCols;
    final int interConnectivity;
    final boolean wrappingNeeded;
    final int treasurePercentage;
    final int demonCount;


    dungeonRows = Integer.parseInt(args[0]);
    dungeonCols = Integer.parseInt(args[1]);
    interConnectivity = Integer.parseInt(args[2]);
    treasurePercentage = Integer.parseInt(args[3]);
    demonCount = Integer.parseInt(args[4]);

    if (args[5].equalsIgnoreCase("true")) {
      wrappingNeeded = true;
    } else if (args[5].equalsIgnoreCase("false")) {
      wrappingNeeded = false;
    } else {
      throw new IllegalArgumentException("Invalid 5th argument. It is expected to be a true or "
              + "false");
    }


    RandomGenerate randomControl = new RandomControlled();
    RandomGenerate random = new RandomAuto();
    Dungeon dungeon = new DungeonImpl(randomControl, dungeonRows, dungeonCols, interConnectivity,
            wrappingNeeded, treasurePercentage, demonCount, "player1");

    Readable input = new InputStreamReader(System.in);
    Appendable output = System.out;
    Controller control = new ControllerImpl(input, output);

    control.play(dungeon);


  }

  /**
   * Main method to run the driver.
   * @param args Command line arguments to provide number of rows, number of columns,
   *             interconnectivity, percentage of cells with treasure, difficulty(i.e number of
   *             demons in game) and wrapping of dungeon in
   *             the specified order.
   */
  public static void main(String[] args) {

    Driver driver = new Driver();


    if (args.length == 0) {
      driver.guiGame();
    }

    else {
      driver.textGame(args);
    }

  }
}
