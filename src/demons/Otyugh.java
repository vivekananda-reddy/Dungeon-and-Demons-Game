package demons;

import constants.DemonHealth;

/**
 * Implements a specific type of demon called Otyugh. Keeps tack of all operations related to
 * Otyugh.
 */
public class Otyugh implements Demon {

  private DemonHealth health;

  /**
   * Constructs an Otyugh object with full initial health.
   */
  public Otyugh() {
    health = DemonHealth.HEALTHY;
  }

  /**
   * Copy constructor to make the copy the Otyugh object.
   * @param that another Otyugh object to the copied
   */
  public Otyugh(Otyugh that) {
    health = that.health;
  }


  @Override
  public DemonHealth getHealthState() {
    return health;
  }

  @Override
  public void decrementHealthState() {
    if (health == DemonHealth.DEAD) {
      throw new IllegalStateException("Demon is already dead");
    }

    if (health == DemonHealth.HEALTHY) {
      health = DemonHealth.INJURED;
    }

    else {
      health = DemonHealth.DEAD;
    }

  }
}
