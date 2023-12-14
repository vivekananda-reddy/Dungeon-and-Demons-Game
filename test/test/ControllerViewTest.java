package test;

import static org.junit.Assert.assertEquals;

import constants.Direction;
import controllerview.ControllerFeatures;
import controllerview.ControllerViewImpl;
import controllerview.View;
import dungeon.Dungeon;
import dungeon.DungeonImpl;
import org.junit.Test;
import randoms.RandomControlled;
import randoms.RandomGenerate;


/**
 * A class to test the controller with GUI.
 */
public class ControllerViewTest {

  @Test
  public void testGuiController() {

    StringBuilder log = new StringBuilder();
    RandomGenerate randomControlled = new RandomControlled();
    Dungeon model = new DungeonImpl(randomControlled,6,6,
            2,false,20,10,1,1,"player1");
    Dungeon mockModel = new MockModel(log);
    View mockView = new MockView(log);

    ControllerFeatures controller = new ControllerViewImpl(mockModel, mockView, randomControlled);

    controller.playGame();
    controller.movePlayer(Direction.WEST);
    controller.shootArrow(Direction.WEST, 2);
    controller.pickItems();
    controller.gameSettings(8,8, 2,false,
            20,10,1,1);
    controller.initiateNewGame();
    controller.restartGame();


    assertEquals("true\n"
            + "controller added\n"
            + "WEST You moved to another location. \n"
            + "WEST 2 \n"
            + "This cell is empty. Nothing to pick. \n"
            + "model controller added\n"
            + "<html>New Game Created!<br> Rows: 8, Cols: 8, Interconnectivity: 2, Treasure: 20, "
            + "Demon Count: 10, Pit Count: 1, Thief Count: 1, Wrapping Needed: false\n"
            + "model controller added\n"
            + "<html>New Game Created!<br> Rows: 8, Cols: 8, Interconnectivity: 2, Treasure: 20, "
            + "Demon Count: 10, Pit Count: 1, Thief Count: 1, Wrapping Needed: false\n"
            + "<html>Restarted Game! <br> Rows: 8, Cols: 8, Interconnectivity: 2, Treasure: 20, "
            + "Demon Count: 10, Pit Count: 1, Thief Count: 1, Wrapping Needed: false\n",
            log.toString());

  }
}
