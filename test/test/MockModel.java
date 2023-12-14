package test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import constants.CellType;
import constants.DemonHealth;
import constants.DemonHit;
import constants.DemonSmell;
import constants.Direction;
import constants.GameResult;
import dungeon.CellReadOnly;
import dungeon.Dungeon;
import dungeon.Location;


/**
 * This mock model is used only for testing the main model hence made it package private.
 */
class MockModel implements Dungeon {

  StringBuilder log;

  public MockModel(StringBuilder log) {
    this.log = log;
  }


  @Override
  public Location getStartPlayerLocation() {
    return null;
  }

  @Override
  public Location getEndPlayerLocation() {
    return null;
  }

  @Override
  public Location getPlayerCurrentLocation() {
    return null;
  }

  @Override
  public CellType getCurrentLocationType() {
    return null;
  }

  @Override
  public Map<String, Integer> getCurrentLocTreasureDetails() {
    return new HashMap<>();
  }

  @Override
  public int getCurrentLocArrowDetails() {
    return 0;
  }

  @Override
  public DemonHealth getCurrentLocDemonDetails() {
    return null;
  }

  @Override
  public List<Direction> getCurrentLocPathDetails() {
    return null;
  }

  @Override
  public Map<String, Integer> getPlayerDetails() {
    return null;
  }

  @Override
  public int getPlayerArrowsCollected() {
    return 1;
  }

  @Override
  public String getPlayerName() {
    return null;
  }

  @Override
  public void movePlayer(Direction moveTo) {

    log.append(moveTo);
    log.append(" ");

  }

  @Override
  public boolean endGameCheck() {
    return false;
  }

  @Override
  public void pickTreasure() {
    log.append("pickTreasure ");

  }

  @Override
  public void pickArrows() {
    log.append("pickTreasure ");
  }

  @Override
  public DemonHit shootArrow(Direction shootDirection, int distance) {
    log.append(shootDirection);
    log.append(" ");
    log.append(distance);
    log.append(" ");
    return null;
  }

  @Override
  public Map<String, Integer> thiefCheck() {
    return null;
  }

  @Override
  public boolean pitCheck() {
    return false;
  }

  @Override
  public void resetModel() {
    //Just a mock so leaving it empty

  }

  @Override
  public DemonSmell getDemonSmell() {
    return null;
  }

  @Override
  public GameResult getGameResult() {
    return null;
  }

  @Override
  public boolean isGameOver() {
    return false;
  }

  @Override
  public CellReadOnly[][] getDungeon() {
    return new CellReadOnly[0][];
  }

  @Override
  public boolean isVisited(int i, int j) {
    return false;
  }

  @Override
  public boolean getPitSound() {
    return false;
  }
}
