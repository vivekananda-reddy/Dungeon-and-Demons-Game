package randoms;

/**
 * A class used to generate random number from the list of random values provided in the
 * controlled order. If the range is out of the list of number then it returns the lower bound
 * or upper bound of the range.
 */
public final class RandomControlledChoices implements RandomGenerate {

  int index = 0;
  int[] randomValues;

  /**
   * Constructs a RandomControlledChoices object with the provided list of numbers.
   * @param randomValues list of integer values from which the random number is expected
   * @throws IllegalArgumentException when randomValues is null
   */
  public RandomControlledChoices(int... randomValues) {
    if (randomValues == null) {
      throw new IllegalArgumentException("Argument to constructor can't be null");
    }
    this.randomValues = randomValues;

  }


  /**
   * Returns the random number in the list of integers provided.
   * If the lowerbound is greater than the maximum value of the list then it returns lower bound.
   * If the upperbound is less than the minimum value in the list then it returns upper bound.
   * @param lowerBound lower bound of random number
   * @param higherBound upper bound of random number
   * @return random number
   * @throws IllegalArgumentException if lower bound is greater than higher bound.
   */
  @Override
  public int getRandom(int lowerBound, int higherBound) {

    if (lowerBound > higherBound) {
      throw new IllegalArgumentException("Lower bound can't be greater than higher bound");
    }

    if (higherBound < randomValues[index % randomValues.length]) {
      index++;
      return higherBound;
    }

    if (lowerBound > randomValues[index % randomValues.length]) {
      index++;
      return lowerBound;
    }

    return randomValues[index++ % randomValues.length];
  }
}
