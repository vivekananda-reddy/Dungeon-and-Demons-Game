package controllerview;

import constants.DemonHit;
import constants.Direction;
import constants.GameResult;
import dungeon.Dungeon;
import dungeon.DungeonImpl;
import dungeon.DungeonReadOnly;
import randoms.RandomGenerate;

import java.util.Map;


/**
 * A class to manage the controller operations. Interacts with model and performs operations on it
 * based on the inputs form the view.
 */
public class ControllerViewImpl implements ControllerFeatures {

  private Dungeon model;
  private final View view;
  private final RandomGenerate random;
  private int dungeonRows;
  private int dungeonCols;
  private int interConnectivity;
  private boolean wrappingNeeded;
  private int treasurePercentage;
  private int demonCount;
  private int pitCount;
  private int thiefCount;

  /**
   * Constructs a Controller object with model, view and random variable.
   * @param model Dungeon model
   * @param view view of any form
   * @param random random number generator
   */
  public ControllerViewImpl(Dungeon model, View view, RandomGenerate random) {
    if (model == null || view == null || random == null) {
      throw new IllegalArgumentException("Null model or view  or random not acceptable");
    }
    this.model = model;
    this.view = view;
    this.random = random;
    this.dungeonRows = 7;
    this.dungeonCols = 7;
    this.interConnectivity = 2;
    this.wrappingNeeded = true;
    this.treasurePercentage = 30;
    this.demonCount = 4;
    this.pitCount = 1;
    this.thiefCount = 1;

  }


  @Override
  public void playGame() {
    view.makeVisible(true);
    view.setFeatures(this);

  }

  @Override
  public void movePlayer(Direction direction) {

    if (direction == null) {
      throw new IllegalArgumentException("Direction can't be null");
    }

    StringBuilder status = new StringBuilder();

    try {

      if (!model.isGameOver()) {
        model.movePlayer(direction);
        if (model.pitCheck()) {
          status.append("You fell into a pit. Game Over!");
          view.refresh(status.toString());
          return;
        }

        if (model.endGameCheck()) {
          status.append("<html>You encountered a Demon in this cell. ");
          if (model.getGameResult() == GameResult.PLAYING) {
            status.append("You are lucky it's an injured Demon, it didn't kill you.</html>");
          }

          else if (model.getGameResult() == GameResult.LOSE) {
            status.append("Demon killed you. ");
            status.append("<br>**You " + model.getGameResult().name() + "**</html>");
          }

          else {
            status.append("You are lucky it's an injured Demon, it didn't kill you.");
            status.append("You reached the end cave. ");
            status.append("<br>**You " + model.getGameResult().name() + "**</html>");
          }

        }

        else {
          if (model.getGameResult() == GameResult.WIN) {
            status.append("<html>You reached the end cave.");
            status.append("<br>**You " + model.getGameResult().name() + "**</html>");
          }
        }

        status.append("You moved to another location. ");

        Map<String, Integer> treasure = model.thiefCheck();
        if (treasure != null) {
          status.append("You encountered a thief here.");

          if (treasure.size() == 0) {
            status.append("You don't have any treasure for thief to steal.");
          }

          else {
            status.append("Thief stole all your treasure - " + treasure);
          }

        }

        view.refresh(status.toString());
      }

    }
    catch (IllegalArgumentException e) {
      view.refresh("Can't move in this direction from this cell.");
    }
  }

  @Override
  public void pickItems() {

    StringBuilder status = new StringBuilder();

    if (!model.isGameOver()) {
      if (model.getCurrentLocTreasureDetails().size() == 0 && model
              .getCurrentLocArrowDetails() == 0) {
        status.append("This cell is empty. Nothing to pick. ");
        view.refresh(status.toString());
        return;
      }

      if (model.getCurrentLocTreasureDetails().size() != 0) {
        status.append("Picked treasure: " + model.getCurrentLocTreasureDetails() + ". ");

        model.pickTreasure();
      }

      else {
        status.append("No treasure here. ");
      }

      if (model.getCurrentLocArrowDetails() != 0) {
        status.append("picked an arrow. ");
        model.pickArrows();
      }

      else {
        status.append("No arrows here. ");
      }

      view.refresh(status.toString());
    }


  }

  @Override
  public void handleDungeonClick(int x, int y) {

    if (x < 0 || y < 0) {
      throw new IllegalArgumentException("Co-ordinates can't be negative");
    }

    int currentX = model.getPlayerCurrentLocation().getX();
    int currentY = model.getPlayerCurrentLocation().getY();

    if (currentX == x) {
      if (currentY != 0) {
        if (((currentY - 1) == y)) {
          movePlayer(Direction.WEST);
          return;

        }
      }
      else {
        if (model.getDungeon()[0].length - 1 == y) {
          movePlayer(Direction.WEST);
          return;
        }
      }
      if ((currentY + 1) % model.getDungeon()[0].length  == y) {
        movePlayer(Direction.EAST);

      }
    }

    else if (currentY == y) {
      if (currentX != 0) {
        if (((currentX - 1) == x)) {
          movePlayer(Direction.NORTH);
          return;

        }
      }
      else {
        if (model.getDungeon().length - 1 == x) {
          movePlayer(Direction.NORTH);
          return;

        }
      }

      if ((currentX + 1) % model.getDungeon().length  == x) {
        movePlayer(Direction.SOUTH);

      }
    }

  }

  @Override
  public void shootArrow(Direction direction, int distance) {

    if (direction == null) {
      throw new IllegalArgumentException("Direction can't be null");
    }

    if (distance < 1) {
      throw new IllegalArgumentException("Distance should be a positive number");
    }

    StringBuilder status = new StringBuilder();

    if (!model.isGameOver()) {
      try {
        DemonHit result = model.shootArrow(direction, distance);

        if (result == DemonHit.MISS ) {
          status.append("You shoot into darkness. ");
        }
        else if (result == DemonHit.HIT ) {
          status.append("You hit and injured a demon. ");
        }
        else if (result == DemonHit.KILL ) {
          status.append("You killed a demon. ");
        }
      }
      catch (IllegalArgumentException | IllegalStateException e) {

        status.append(e.getMessage());
      }

      view.refresh(status.toString());
    }


  }

  @Override
  public void exitProgram() {
    System.exit(0);
  }

  @Override
  public void gameSettings(int dungeonRows, int dungeonCols, int interConnectivity,
                           boolean wrappingNeeded, int treasurePercentage, int demonCount,
                           int pitCount, int thiefCount) {
    if (dungeonCols < 0 || dungeonRows < 0 || treasurePercentage < 0 || demonCount < 0
            || pitCount < 0 || thiefCount < 0 || interConnectivity < 0) {
      throw new IllegalArgumentException("Negative arguments passed");
    }


    this.dungeonRows = dungeonRows;
    this.dungeonCols = dungeonCols;
    this.interConnectivity = interConnectivity;
    this.wrappingNeeded = wrappingNeeded;
    this.treasurePercentage = treasurePercentage;
    this.demonCount = demonCount;
    this.pitCount = pitCount;
    this.thiefCount = thiefCount;


    initiateNewGame();
  }

  @Override
  public void initiateNewGame() {

    try {
      model = new DungeonImpl(random,dungeonRows,dungeonCols,interConnectivity,
              wrappingNeeded, treasurePercentage, demonCount, pitCount, thiefCount,"player1");
      view.setModel((DungeonReadOnly) model, this);
      //view.setFeatures(this);
      view.refresh("<html>New Game Created!<br> Rows: " + dungeonRows + ", Cols: "
              + dungeonCols + ", Interconnectivity: " + interConnectivity + ", Treasure: "
              + treasurePercentage + ", Demon Count: " + demonCount + ", Pit Count: "
              + pitCount + ", Thief Count: " + thiefCount + ", Wrapping Needed: " + wrappingNeeded);
    }

    catch (IllegalArgumentException e) {
      view.showErrorMessage(e.getMessage());
    }

  }


  @Override
  public void restartGame() {
    model.resetModel();
    view.refresh("<html>Restarted Game! <br> Rows: " + dungeonRows + ", Cols: " + dungeonCols
            + ", Interconnectivity: " + interConnectivity + ", Treasure: " + treasurePercentage
            + ", Demon Count: " + demonCount + ", Pit Count: " + pitCount + ", Thief Count: "
            + thiefCount + ", Wrapping Needed: " + wrappingNeeded);

  }
}
