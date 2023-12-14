package test;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import dungeon.Location;
import players.GenericPlayer;
import players.Player;
import treasures.Diamonds;
import treasures.Rubies;
import treasures.Sapphires;
import treasures.Treasure;

import static org.junit.Assert.assertEquals;


/**
 * A class to test Generic player interface and related class.
 */
public class PlayerTest {

  Location testLocation;
  GenericPlayer testPlayer;
  Treasure testTreasure1;
  Treasure testTreasure2;
  Treasure testTreasure3;

  @Before
  public void setUp() throws Exception {
    testLocation = new Location(10,30);
    testPlayer = new Player("player1");
    testTreasure1 = new Sapphires();
    testTreasure2 = new Diamonds();
    testTreasure3 = new Rubies();
  }

  @Test
  public void testGetName() {
    assertEquals("player1",testPlayer.getName());
  }


  @Test
  public void testSetAndGetPlayerLocation() {
    testPlayer.setLocation(testLocation);
    assertEquals(10,testLocation.getX());
    assertEquals(30,testLocation.getY());
  }

  @Test
  public void testPickAndGetTreasure() {
    Map<String, Integer> treasures = new HashMap<>();

    treasures.put(testTreasure1.getTreasureType(), 2);
    treasures.put(testTreasure2.getTreasureType(), 2);
    treasures.put(testTreasure3.getTreasureType(), 3);

    testPlayer.pickTreasure(treasures);
    assertEquals(treasures, testPlayer.getTreasure());
  }

  @Test(expected = IllegalStateException.class)
  public void testGetPlayerLocBeforeSetting() {
    testPlayer.getPlayerLocation();
  }

  @Test
  public void testCollectArrows() {
    assertEquals(3, testPlayer.getArrowCount());
    testPlayer.addArrow();
    assertEquals(4, testPlayer.getArrowCount());
    testPlayer.removeArrow();
    assertEquals(3, testPlayer.getArrowCount());
    testPlayer.removeArrow();
    assertEquals(2, testPlayer.getArrowCount());
  }

  @Test(expected = IllegalStateException.class)
  public void testRemoveWith0Arrows() {
    testPlayer.removeArrow();
    testPlayer.removeArrow();
    testPlayer.removeArrow();
    testPlayer.removeArrow();
  }

}