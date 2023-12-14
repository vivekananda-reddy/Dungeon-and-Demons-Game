package test;

import org.junit.Before;
import org.junit.Test;

import treasures.Diamonds;
import treasures.Rubies;
import treasures.Sapphires;
import treasures.Treasure;

import static org.junit.Assert.assertEquals;


/**
 * A class to test treasure interface and related classes.
 */
public class TreasureTest {
  Treasure testDiamond;
  Treasure testRuby;
  Treasure testSapphire;

  @Before
  public void setUp() throws Exception {
    testDiamond = new Diamonds();
    testRuby = new Rubies();
    testSapphire = new Sapphires();
  }

  @Test
  public void testGetTreasureType() {

    assertEquals("diamond", testDiamond.getTreasureType());
    assertEquals("ruby", testRuby.getTreasureType());
    assertEquals("sapphire", testSapphire.getTreasureType());

  }
}