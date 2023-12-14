package test;

import org.junit.Before;
import org.junit.Test;

import dungeon.Location;

import static org.junit.Assert.assertEquals;


/**
 * A class to test Location class.
 */
public class LocationTest {
  private Location testLocation;

  @Before
  public void setUp() throws Exception {
    testLocation = new Location(2,3);
  }

  @Test
  public void testGetX() {
    assertEquals(2,testLocation.getX());
  }

  @Test
  public void testGetY() {
    assertEquals(3,testLocation.getY());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidInput() {
    new Location(-1,3);
  }

  @Test
  public void testEquals() {
    Location testEquals = new Location(2,3);
    Location testNotEquals = new Location(2,2);

    assertEquals(true,testEquals.equals(testLocation));
    assertEquals(false,testNotEquals.equals(testLocation));
  }
}