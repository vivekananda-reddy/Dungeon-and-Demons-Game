package randoms;

import java.util.Random;

/**
 * A classes which used Java's builtin random number generator to give the random number in
 * the specified range.
 */
public final class RandomAuto implements RandomGenerate {


  @Override
  public int getRandom(int lowerBound, int higherBound) {
    if (higherBound < lowerBound) {
      throw new IllegalArgumentException("Higher bound can;t be lower than lower bond");
    }
    Random temp = new Random();
    return (temp.nextInt(higherBound - lowerBound + 1) + lowerBound);
  }
}
