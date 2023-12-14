package test;


import org.junit.Before;
import org.junit.Test;

import constants.DemonHealth;
import demons.Demon;
import demons.Otyugh;

import static org.junit.Assert.assertEquals;


/**
 * A class to test Demon related items.
 */
public class DemonTest {
  private Demon testDemon;

  @Before
  public void setUp() throws Exception {
    testDemon = new Otyugh();
  }

  @Test
  public void testGetAndSet() {
    assertEquals(DemonHealth.HEALTHY, testDemon.getHealthState());
    testDemon.decrementHealthState();
    assertEquals(DemonHealth.INJURED, testDemon.getHealthState());
    testDemon.decrementHealthState();
    assertEquals(DemonHealth.DEAD, testDemon.getHealthState());
  }

  @Test(expected = IllegalStateException.class)
  public void testDecrementHealthAfterDead() {

    testDemon.decrementHealthState();
    testDemon.decrementHealthState();
    testDemon.decrementHealthState();

  }
}