package test;


import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import constants.CellType;
import constants.DemonHealth;
import constants.Direction;
import dungeon.CellImpl;
import treasures.Diamonds;
import treasures.Rubies;
import treasures.Sapphires;
import treasures.Treasure;

import static org.junit.Assert.assertEquals;


/**
 * A class to test Cell interface and related classes.
 */
public class CellTest {

  CellImpl testCell;
  Treasure testTreasure1;
  Treasure testTreasure2;
  Treasure testTreasure3;

  @Before
  public void setUp() throws Exception {
    testCell = new CellImpl(1);
    testTreasure1 = new Sapphires();
    testTreasure2 = new Diamonds();
    testTreasure3 = new Rubies();
  }


  @Test
  public void testGetAddRemoveContainsTreasure() {
    testCell.addTreasure(testTreasure1);
    testCell.addTreasure(testTreasure2);
    testCell.addTreasure(testTreasure3);
    testCell.addTreasure(testTreasure1);
    testCell.addTreasure(testTreasure3);

    assertEquals(true,testCell.containsTreasure());
    Integer value = 2;
    assertEquals(value, testCell.getTreasure().get(testTreasure1.getTreasureType()));
    assertEquals(value, testCell.getTreasure().get(testTreasure3.getTreasureType()));
    value = 1;
    assertEquals(value, testCell.getTreasure().get(testTreasure2.getTreasureType()));

    Map<String, Integer> removedTreasure = testCell.removeTreasure();
    assertEquals(false,testCell.containsTreasure());
    value = 2;
    assertEquals(value, removedTreasure.get(testTreasure1.getTreasureType()));
    assertEquals(value, removedTreasure.get(testTreasure3.getTreasureType()));
    value = 1;
    assertEquals(value, removedTreasure.get(testTreasure2.getTreasureType()));

  }

  @Test
  public void testConnectionsAndType() {
    testCell.formConnection(Direction.EAST);
    testCell.formConnection(Direction.SOUTH);

    assertEquals(Direction.EAST,testCell.getPossibleDirections().get(0));
    assertEquals(Direction.SOUTH,testCell.getPossibleDirections().get(1));

    assertEquals(CellType.CAVE, testCell.getType());
    testCell.setAsTunnel();
    assertEquals(CellType.TUNNEL, testCell.getType());

  }

  @Test
  public void testArrows() {
    assertEquals(0, testCell.getArrowCount());
    testCell.addArrow();
    assertEquals(1, testCell.getArrowCount());
    testCell.addArrow();
    assertEquals(2, testCell.getArrowCount());
    assertEquals(2, testCell.removeArrows());
    assertEquals(0, testCell.getArrowCount());
  }

  @Test
  public void testDemons() {

    assertEquals(false, testCell.containsDemons());
    testCell.addDemon();
    assertEquals(DemonHealth.HEALTHY, testCell.getDemon().getHealthState());
    testCell.getDemon().decrementHealthState();
    assertEquals(DemonHealth.INJURED, testCell.getDemon().getHealthState());

    assertEquals(DemonHealth.INJURED, testCell.removeDemon().getHealthState());
    assertEquals(false, testCell.containsDemons());
  }

  @Test
  public void testVisited() {
    assertEquals(false, testCell.isVisited());
    testCell.markVisited();
    assertEquals(true, testCell.isVisited());
  }

  @Test
  public void testThief() {
    assertEquals(false, testCell.containsThief());
    testCell.addThief();
    assertEquals(true, testCell.containsThief());
  }

  @Test
  public void testPit() {
    assertEquals(false, testCell.containsPit());
    testCell.addPit();
    assertEquals(true, testCell.containsPit());
  }

  @Test
  public void testDeepCopy() {

    testCell.addTreasure(testTreasure1);
    testCell.addTreasure(testTreasure2);
    testCell.addTreasure(testTreasure3);
    testCell.addPit();
    testCell.addThief();
    testCell.addDemon();
    testCell.addArrow();
    testCell.formConnection(Direction.WEST);

    CellImpl copyCell = new CellImpl(testCell);

    assertEquals(testCell.containsDemons(), copyCell.containsDemons());
    assertEquals(testCell.containsPit(), copyCell.containsPit());
    assertEquals(testCell.containsThief(), copyCell.containsThief());
    assertEquals(testCell.containsTreasure(), copyCell.containsTreasure());
    assertEquals(testCell.getArrowCount(), copyCell.getArrowCount());
    assertEquals(testCell.getType(), copyCell.getType());
    assertEquals(testCell.getTreasure(), copyCell.getTreasure());
    assertEquals(testCell.getPossibleDirections(), copyCell.getPossibleDirections());
    assertEquals(testCell.getDemon().getHealthState(), copyCell.getDemon().getHealthState());

  }

  @Test(expected = IllegalStateException.class)
  public void testDemonInTunnel() {
    testCell.formConnection(Direction.EAST);
    testCell.formConnection(Direction.SOUTH);
    testCell.setAsTunnel();
    testCell.addDemon();
  }

  @Test(expected = IllegalStateException.class)
  public void testAdd2ndDemon() {
    testCell.addDemon();
    testCell.addDemon();
  }


  @Test(expected = IllegalStateException.class)
  public void testEmptyCaveGetDemon() {
    testCell.getDemon();
  }

  @Test(expected = IllegalStateException.class)
  public void testRemoveFromEmptyCell() {
    testCell.removeDemon();
  }


}