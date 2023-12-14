package test;

import org.junit.Test;

import java.io.Reader;
import java.io.StringReader;

import controller.Controller;
import controller.ControllerImpl;
import dungeon.Dungeon;
import dungeon.DungeonImpl;
import randoms.RandomControlled;
import randoms.RandomGenerate;

import static org.junit.Assert.assertEquals;


/**
 * A class to test the controller.
 */
public class ControllerTest {


  @Test
  public void testLoseGame() {

    Reader input = new StringReader("p s w 2 s w 1 m w");
    StringBuilder output = new StringBuilder();
    RandomGenerate random = new RandomControlled();
    Dungeon model = new DungeonImpl(random, 6, 6, 2,
            false, 20, 4, 0, 0,"player1");


    Controller c = new ControllerImpl(input, output);
    c.play(model);

    String expectedOutput = "============Welcome to the game============\n"
            + "******************************************\n"
            + "You are in a CAVE\n"
            + "Doors lead to :[WEST]\n"
            + "Treasure found in this cell:{ruby=2}\n"
            + "There is an arrow here\n"
            + "You are getting a strong smell of a demon\n"
            + "You have following items:No treasure collected, look around for treasure.Arrows:3\n"
            + "Choose an action: Move, Pickup, Shoot, Quit (M-P-S-Q) - "
            + "picked treasure: {ruby=2}\n"
            + "picked an arrow\n"
            + "******************************************\n"
            + "You are in a CAVE\n"
            + "Doors lead to :[WEST]\n"
            + "You are getting a strong smell of a demon\n"
            + "You have following items:{ruby=2}, Arrows:4\n"
            + "Choose an action: Move, Pickup, Shoot, Quit (M-P-S-Q) - "
            + "Enter direction to shoot - "
            + "Enter distance to shoot - "
            + "You shoot into darkness\n"
            + "******************************************\n"
            + "You are in a CAVE\n"
            + "Doors lead to :[WEST]\n"
            + "You are getting a strong smell of a demon\n"
            + "You have following items:{ruby=2}, Arrows:3\n"
            + "Choose an action: Move, Pickup, Shoot, Quit (M-P-S-Q) - "
            + "Enter direction to shoot - "
            + "Enter distance to shoot - "
            + "You hit and injured a demon\n"
            + "******************************************\n"
            + "You are in a CAVE\n"
            + "Doors lead to :[WEST]\n"
            + "You are getting a strong smell of a demon\n"
            + "You have following items:{ruby=2}, Arrows:2\n"
            + "Choose an action: Move, Pickup, Shoot, Quit (M-P-S-Q) - "
            + "Direction of move - "
            + "You encountered a Demon in this cell\n"
            + "Demon killed you\n"
            + "**You LOSE**\n"
            + "==>Game Over<==";

    assertEquals(expectedOutput, output.toString());

  }

  @Test
  public void testWinGame() {

    Reader input = new StringReader("p s w 1 s w 1 m w p m w p m w p s s 1 s p s 1 s n 3 w "
            + "2 m s p m s s s 1 s s 1 p b m s");
    StringBuilder output = new StringBuilder();
    RandomGenerate random = new RandomControlled();
    Dungeon model = new DungeonImpl(random, 6, 6, 2,
            false, 20, 4, 0, 0, "player1");


    Controller c = new ControllerImpl(input, output);
    c.play(model);

    String expectedOutput = "============Welcome to the game============\n"
            + "******************************************\n"
            + "You are in a CAVE\n"
            + "Doors lead to :[WEST]\n"
            + "Treasure found in this cell:{ruby=2}\n"
            + "There is an arrow here\n"
            + "You are getting a strong smell of a demon\n"
            + "You have following items:No treasure collected, look around for treasure.Arrows:3\n"
            + "Choose an action: Move, Pickup, Shoot, Quit (M-P-S-Q) - "
            + "picked treasure: {ruby=2}\n"
            + "picked an arrow\n"
            + "******************************************\n"
            + "You are in a CAVE\n"
            + "Doors lead to :[WEST]\n"
            + "You are getting a strong smell of a demon\n"
            + "You have following items:{ruby=2}, Arrows:4\n"
            + "Choose an action: Move, Pickup, Shoot, Quit (M-P-S-Q) - "
            + "Enter direction to shoot - "
            + "Enter distance to shoot - "
            + "You hit and injured a demon\n"
            + "******************************************\n"
            + "You are in a CAVE\n"
            + "Doors lead to :[WEST]\n"
            + "You are getting a strong smell of a demon\n"
            + "You have following items:{ruby=2}, Arrows:3\n"
            + "Choose an action: Move, Pickup, Shoot, Quit (M-P-S-Q) - "
            + "Enter direction to shoot - "
            + "Enter distance to shoot - "
            + "You killed a demon\n"
            + "******************************************\n"
            + "You are in a CAVE\n"
            + "Doors lead to :[WEST]\n"
            + "You have following items:{ruby=2}, Arrows:2\n"
            + "Choose an action: Move, Pickup, Shoot, Quit (M-P-S-Q) - "
            + "Direction of move - "
            + "******************************************\n"
            + "You are in a CAVE\n"
            + "Doors lead to :[EAST, SOUTH, WEST, NORTH]\n"
            + "Treasure found in this cell:{ruby=2}\n"
            + "There is an arrow here\n"
            + "You have following items:{ruby=2}, Arrows:2\n"
            + "Choose an action: Move, Pickup, Shoot, Quit (M-P-S-Q) - "
            + "picked treasure: {ruby=2}\n"
            + "picked an arrow\n"
            + "******************************************\n"
            + "You are in a CAVE\n"
            + "Doors lead to :[EAST, SOUTH, WEST, NORTH]\n"
            + "You have following items:{ruby=4}, Arrows:3\n"
            + "Choose an action: Move, Pickup, Shoot, Quit (M-P-S-Q) - "
            + "Direction of move - "
            + "******************************************\n"
            + "You are in a CAVE\n"
            + "Doors lead to :[EAST, SOUTH, WEST]\n"
            + "There is an arrow here\n"
            + "You are getting a faint smell of a demon\n"
            + "You have following items:{ruby=4}, Arrows:3\n"
            + "Choose an action: Move, Pickup, Shoot, Quit (M-P-S-Q) - "
            + "No treasure here\n"
            + "picked an arrow\n"
            + "******************************************\n"
            + "You are in a CAVE\n"
            + "Doors lead to :[EAST, SOUTH, WEST]\n"
            + "You are getting a faint smell of a demon\n"
            + "You have following items:{ruby=4}, Arrows:4\n"
            + "Choose an action: Move, Pickup, Shoot, Quit (M-P-S-Q) - "
            + "Direction of move - "
            + "******************************************\n"
            + "You are in a CAVE\n"
            + "Doors lead to :[EAST, SOUTH, WEST]\n"
            + "There is an arrow here\n"
            + "You are getting a strong smell of a demon\n"
            + "You have following items:{ruby=4}, Arrows:4\n"
            + "Choose an action: Move, Pickup, Shoot, Quit (M-P-S-Q) - "
            + "No treasure here\n"
            + "picked an arrow\n"
            + "******************************************\n"
            + "You are in a CAVE\n"
            + "Doors lead to :[EAST, SOUTH, WEST]\n"
            + "You are getting a strong smell of a demon\n"
            + "You have following items:{ruby=4}, Arrows:5\n"
            + "Choose an action: Move, Pickup, Shoot, Quit (M-P-S-Q) - "
            + "Enter direction to shoot - "
            + "Enter distance to shoot - "
            + "You hit and injured a demon\n"
            + "******************************************\n"
            + "You are in a CAVE\n"
            + "Doors lead to :[EAST, SOUTH, WEST]\n"
            + "You are getting a strong smell of a demon\n"
            + "You have following items:{ruby=4}, Arrows:4\n"
            + "Choose an action: Move, Pickup, Shoot, Quit (M-P-S-Q) - "
            + "Enter direction to shoot - "
            + "Enter a valid direction(N S E W)\n"
            + "Enter direction to shoot - "
            + "Enter distance to shoot - "
            + "You killed a demon\n"
            + "******************************************\n"
            + "You are in a CAVE\n"
            + "Doors lead to :[EAST, SOUTH, WEST]\n"
            + "You are getting a faint smell of a demon\n"
            + "You have following items:{ruby=4}, Arrows:3\n"
            + "Choose an action: Move, Pickup, Shoot, Quit (M-P-S-Q) - "
            + "Enter direction to shoot - "
            + "Enter distance to shoot - "
            + "Can't shoot an arrow in NORTH direction\n"
            + "Enter direction to shoot - "
            + "Enter distance to shoot - "
            + "You shoot into darkness\n"
            + "******************************************\n"
            + "You are in a CAVE\n"
            + "Doors lead to :[EAST, SOUTH, WEST]\n"
            + "You are getting a faint smell of a demon\n"
            + "You have following items:{ruby=4}, Arrows:2\n"
            + "Choose an action: Move, Pickup, Shoot, Quit (M-P-S-Q) - "
            + "Direction of move - "
            + "******************************************\n"
            + "You are in a CAVE\n"
            + "Doors lead to :[WEST, SOUTH, NORTH]\n"
            + "There is an arrow here\n"
            + "You are getting a strong smell of a demon\n"
            + "You have following items:{ruby=4}, Arrows:2\n"
            + "Choose an action: Move, Pickup, Shoot, Quit (M-P-S-Q) - "
            + "No treasure here\n"
            + "picked an arrow\n"
            + "******************************************\n"
            + "You are in a CAVE\n"
            + "Doors lead to :[WEST, SOUTH, NORTH]\n"
            + "You are getting a strong smell of a demon\n"
            + "You have following items:{ruby=4}, Arrows:3\n"
            + "Choose an action: Move, Pickup, Shoot, Quit (M-P-S-Q) - "
            + "Direction of move - "
            + "******************************************\n"
            + "You are in a TUNNEL\n"
            + "Doors lead to :[NORTH, SOUTH]\n"
            + "You are getting a strong smell of a demon\n"
            + "You have following items:{ruby=4}, Arrows:3\n"
            + "Choose an action: Move, Pickup, Shoot, Quit (M-P-S-Q) - "
            + "Enter direction to shoot - "
            + "Enter distance to shoot - "
            + "You hit and injured a demon\n"
            + "******************************************\n"
            + "You are in a TUNNEL\n"
            + "Doors lead to :[NORTH, SOUTH]\n"
            + "You are getting a strong smell of a demon\n"
            + "You have following items:{ruby=4}, Arrows:2\n"
            + "Choose an action: Move, Pickup, Shoot, Quit (M-P-S-Q) - "
            + "Enter direction to shoot - "
            + "Enter distance to shoot - "
            + "You killed a demon\n"
            + "******************************************\n"
            + "You are in a TUNNEL\n"
            + "Doors lead to :[NORTH, SOUTH]\n"
            + "You are getting a faint smell of a demon\n"
            + "You have following items:{ruby=4}, Arrows:1\n"
            + "Choose an action: Move, Pickup, Shoot, Quit (M-P-S-Q) - "
            + "This cell is empty. Nothing to pick\n"
            + "******************************************\n"
            + "You are in a TUNNEL\n"
            + "Doors lead to :[NORTH, SOUTH]\n"
            + "You are getting a faint smell of a demon\n"
            + "You have following items:{ruby=4}, Arrows:1\n"
            + "Choose an action: Move, Pickup, Shoot, Quit (M-P-S-Q) - "
            + "Invalid action\n"
            + "******************************************\n"
            + "You are in a TUNNEL\n"
            + "Doors lead to :[NORTH, SOUTH]\n"
            + "You are getting a faint smell of a demon\n"
            + "You have following items:{ruby=4}, Arrows:1\n"
            + "Choose an action: Move, Pickup, Shoot, Quit (M-P-S-Q) - "
            + "Direction of move - "
            + "You reached the end cave\n"
            + "\n"
            + "**You WIN**\n"
            + "==>Game Over<==";

    assertEquals(expectedOutput, output.toString());

  }

  @Test
  public void testInputsWithMockForMove() {

    StringBuilder log = new StringBuilder();
    Dungeon mock = new MockModel(log);

    Reader input = new StringReader("m w q");
    StringBuilder output = new StringBuilder();

    Controller c = new ControllerImpl(input, output);
    c.play(mock);
    assertEquals("WEST ", log.toString());

  }

  @Test
  public void testInputsWithMockForShoot() {

    StringBuilder log = new StringBuilder();
    Dungeon mock = new MockModel(log);

    Reader input = new StringReader("s e 1 q");
    StringBuilder output = new StringBuilder();

    Controller c = new ControllerImpl(input, output);
    c.play(mock);
    assertEquals("EAST 1 ", log.toString());

  }


  @Test(expected = IllegalArgumentException.class)
  public void testInvalidInputsToConstructor() {
    new ControllerImpl(null, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidInputsToPlay() {
    Reader input = new StringReader("p s w 2 s w 1 m w");
    StringBuilder output = new StringBuilder();
    Controller c = new ControllerImpl(input, output);
    c.play(null);
  }


}