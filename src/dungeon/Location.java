package dungeon;

import java.util.Objects;

/**
 * A class to keep track of x,y co-ordinates on a 2D grid.
 */
public class Location {
  int x;
  int y;

  /**
   * constructs a Location object with x,y co-ordinates.
   * @param x x co-ordinate
   * @param y y co-ordinate
   */
  public Location(int x, int y) {
    if (x < 0 || y < 0) {
      throw new IllegalArgumentException("Co ordinates can't be negative");
    }

    this.x = x;
    this.y = y;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (!(o instanceof Location)) {
      return false;
    }

    Location that = (Location) o;

    return (this.getX() == that.getX()) && (this.getY() == that.getY());

  }

  @Override
  public int hashCode() {
    return Objects.hash();
  }

  @Override
  public String toString() {
    StringBuilder tempString = new StringBuilder();
    tempString.append("(");
    tempString.append(getX());
    tempString.append(",");
    tempString.append(getY());
    tempString.append(")");

    return tempString.toString();
  }

}
