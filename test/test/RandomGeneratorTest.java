package test;

import org.junit.Before;
import org.junit.Test;

import randoms.RandomAuto;
import randoms.RandomControlled;
import randoms.RandomControlledChoices;
import randoms.RandomGenerate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * A class to test Random Generator classes.
 */
public class RandomGeneratorTest {

  private RandomGenerate testRandomAuto;
  private RandomGenerate testRandomControlled;
  private RandomControlledChoices testRandomChoices;


  @Before
  public void setUp() throws Exception {
    testRandomAuto = new RandomAuto();
    testRandomControlled = new RandomControlled();
    testRandomChoices = new RandomControlledChoices(1,2,3,4,5);


  }

  @Test
  public void testRandomAuto() {
    int testValue1 = testRandomAuto.getRandom(1, 5);

    if ( testValue1 > 5 || testValue1 < 1) {
      fail("Generated random value is not in range");
    }

    int testValue2 = testRandomAuto.getRandom(1, 5);
    if ( testValue2 > 5 || testValue2 < 1) {
      fail("Generated random value is not in range");
    }

    int testValue3 = testRandomAuto.getRandom(1, 5);
    if ( testValue3 > 5 || testValue3 < 1) {
      fail("Generated random value is not in range");
    }

    int testValue4 = testRandomAuto.getRandom(1, 5);
    if ( testValue4 > 5 || testValue4 < 1) {
      fail("Generated random value is not in range");
    }

    int testValue5 = testRandomAuto.getRandom(1, 5);
    if ( testValue5 > 5 || testValue5 < 1) {
      fail("Generated random value is not in range");
    }

    int testValue6 = testRandomAuto.getRandom(5, 15);
    if ( testValue6 > 15 || testValue6 < 5) {
      fail("Generated random value is not in range");
    }

    int testValue7 = testRandomAuto.getRandom(5, 15);
    if ( testValue7 > 15 || testValue7 < 5) {
      fail("Generated random value is not in range");
    }

    int testValue8 = testRandomAuto.getRandom(5, 15);
    if ( testValue8 > 15 || testValue8 < 5) {
      fail("Generated random value is not in range");
    }


    int testValue9 = testRandomAuto.getRandom(5, 15);
    if ( testValue9 > 15 || testValue9 < 5) {
      fail("Generated random value is not in range");
    }

    if (testValue1 == testValue2 && testValue2 == testValue3 && testValue3 == testValue4
            && testValue4 == testValue5 && testValue6 == testValue7 && testValue8 == testValue9) {
      fail("Values are repeating randomness is not achieved");
    }


  }

  @Test(expected = IllegalArgumentException.class)
  public void testRandomAutoBoundValidation() {
    testRandomAuto.getRandom(3, 1);

  }

  @Test
  public void testRandomControlled() {
    assertEquals(3,testRandomControlled.getRandom(1, 5));
    assertEquals(12,testRandomControlled.getRandom(10, 15));
    assertEquals(1,testRandomControlled.getRandom(1, 2));

  }

  @Test(expected = IllegalArgumentException.class)
  public void testRandomControlledBoundValidation() {
    testRandomControlled.getRandom(3, 1);

  }

  @Test
  public void testRandomControlledChoices() {
    assertEquals(1,testRandomChoices.getRandom(1,4));
    assertEquals(2,testRandomChoices.getRandom(1,4));
    assertEquals(3,testRandomChoices.getRandom(1,4));
    assertEquals(3,testRandomChoices.getRandom(1,3));
    assertEquals(5,testRandomChoices.getRandom(1,5));
    assertEquals(3,testRandomChoices.getRandom(3,4));
  }


}
