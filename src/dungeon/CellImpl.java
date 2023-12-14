package dungeon;

import constants.CellType;
import constants.Direction;
import demons.Demon;
import demons.Otyugh;
import treasures.Treasure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;



/**
 * A class to implement the cell related operations and maintain connections/paths to other cells
 * and store treasure when assigned.
 */
public class CellImpl implements Cell {
  private CellType cellType;
  private Map<String, Integer> treasureInCell;
  private final List<Direction> connections;
  private final int cellID;
  private int arrowsCount;
  private Demon demon;
  private boolean visited;
  private boolean thief;
  private boolean pit;

  /**
   * Constructs a CellImpl object with cellID as parameter.
   * @param cellID Id of the cell
   */
  public CellImpl(int cellID) {
    if (cellID < 0) {
      throw new IllegalArgumentException("Id should be non negative");
    }
    cellType = CellType.CAVE;
    treasureInCell = new HashMap<>();
    this.cellID = cellID;
    connections = new ArrayList<>();
    arrowsCount = 0;
    demon = null;
    visited = false;
    thief = false;
    pit = false;
  }

  /**
   * Copy constructor to copy the state of the object.
   * @param that another cell object to be copied
   */

  public CellImpl(CellImpl that) {

    cellType = that.cellType;
    treasureInCell = new HashMap<>(that.treasureInCell);
    connections = new ArrayList<>(that.connections);
    cellID = that.cellID;
    arrowsCount = that.arrowsCount;

    if (that.demon != null) {
      demon = new Otyugh((Otyugh) that.demon);
    }

    visited = that.visited;
    thief = that.thief;
    pit = that.pit;

  }

  @Override
  public void setAsTunnel() {

    if (connections.size() != 2) {
      throw new IllegalStateException("This cell doesn't have 2 connections");
    }

    if (treasureInCell.isEmpty()) {
      cellType = CellType.TUNNEL;
    }

    else {
      throw new IllegalStateException("Treasure is stored in this cell, it can't be made a tunnel");
    }
  }

  @Override
  public CellType getType() {
    return cellType;
  }

  @Override
  public void addTreasure(Treasure toBeAddedTreasure) {

    if (this.getType() == CellType.TUNNEL) {
      throw new IllegalStateException("This cell is a tunnel, treasure can't be added to a tunnel");
    }

    if (toBeAddedTreasure == null) {
      throw new IllegalArgumentException("To be added treasure can't be null");
    }

    treasureInCell.put(toBeAddedTreasure.getTreasureType(),treasureInCell.getOrDefault(
            toBeAddedTreasure.getTreasureType(), 0) + 1);

  }

  @Override
  public Map<String, Integer> getTreasure() {
    return new HashMap<>(treasureInCell);
  }

  @Override
  public boolean containsTreasure() {
    return !treasureInCell.isEmpty();
  }

  @Override
  public Map<String, Integer> removeTreasure() {
    Map<String, Integer> copy = new HashMap<>(treasureInCell);
    treasureInCell = new HashMap<>();
    return copy;
  }

  @Override
  public void formConnection(Direction possibleDirections) {
    if (possibleDirections == null) {
      throw new IllegalArgumentException("Direction can't be null");
    }
    connections.add(possibleDirections);
  }

  @Override
  public List<Direction> getPossibleDirections() {
    return new ArrayList<>(connections);
  }

  @Override
  public void addArrow() {
    arrowsCount++;
  }

  @Override
  public int getArrowCount() {
    return arrowsCount;
  }

  @Override
  public int removeArrows() {
    int temp = arrowsCount;
    arrowsCount = 0;
    return temp;
  }

  @Override
  public boolean containsDemons() {
    return demon != null;
  }

  @Override
  public Demon getDemon() {
    if (!containsDemons()) {
      throw new IllegalStateException("This cell doesn't have a Demon");
    }
    return demon;
  }

  @Override
  public void addDemon() {

    if (getType() == CellType.TUNNEL) {
      throw new IllegalStateException("Can't add demon to a tunnel");
    }
    if (containsDemons()) {
      throw new IllegalStateException("One cell can have only one demon");
    }
    demon = new Otyugh();
  }


  @Override
  public Demon removeDemon() {
    if (!containsDemons()) {
      throw new IllegalStateException("Demon doesn't exist in this cell");
    }
    Demon temp = demon;
    demon = null;
    return temp;
  }

  @Override
  public void markVisited() {
    visited = true;
  }

  @Override
  public boolean isVisited() {
    return visited;
  }


  @Override
  public void addThief() {
    thief = true;
  }

  @Override
  public boolean containsThief() {
    return thief;
  }


  @Override
  public void addPit() {
    pit = true;
  }

  @Override
  public boolean containsPit() {
    return pit;
  }

  @Override
  public boolean equals(Object o) {

    if (o == this) {
      return true;
    }
    if (!(o instanceof CellImpl)) {
      return false;
    }

    CellImpl that = (CellImpl) o;

    return this.cellID == that.cellID;
  }

  @Override
  public int hashCode() {
    return Objects.hash(cellID);
  }

  @Override
  public String toString() {
    StringBuilder tempString = new StringBuilder();
    tempString.append(cellID);
    tempString.append(":");
    tempString.append(cellType.name());
    tempString.append(treasureInCell);
    tempString.append(connections);

    if (getArrowCount() > 0) {
      tempString.append("<arrow>");
    }

    if (containsDemons()) {
      tempString.append("|demon|");
    }

    if (containsThief()) {
      tempString.append("$thief$");
    }

    if (containsPit()) {
      tempString.append("&pit&");
    }


    return tempString.toString();
  }

}
