package randoms;

/**
 * A class used to generate random number which is the center value of the provided range.
 */
public final class RandomControlled implements RandomGenerate {
  @Override
  public int getRandom(int lowerBound, int higherBound) {
    if (higherBound < lowerBound) {
      throw new IllegalArgumentException("Higher bound can't be lower than lower bond");
    }
    return (lowerBound + higherBound) / 2;
  }
}
