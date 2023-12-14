package demons;

import constants.DemonHealth;

/**
 * Represents a demon in the cell. Can handle demon health related operations.
 */
public interface Demon {

  /**
   * Gets the health of the demon.
   * @return Enum representing the helth of the demon
   */
  DemonHealth getHealthState();

  /**
   * Decrements the health of the demon.
   */
  void decrementHealthState();

}
