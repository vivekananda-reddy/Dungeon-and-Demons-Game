package randoms;

/**
 * An interface for random generator to generate a random value in the specified range of numbers.
 */
public interface RandomGenerate {

  /**
   * Gets a random number in the provided range.
   * @param lowerBound lower bound of random number
   * @param higherBound upper bound of random number
   * @return random number in the range
   */
  int getRandom(int lowerBound, int higherBound);
}
