package dungeon;

import constants.CellType;
import constants.DemonHealth;
import constants.DemonHit;
import constants.DemonSmell;
import constants.Direction;
import constants.GameResult;
import players.GenericPlayer;
import players.Player;
import randoms.RandomGenerate;
import treasures.Diamonds;
import treasures.Rubies;
import treasures.Sapphires;
import treasures.Treasure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;




/**
 * A class to build and perform all dungeon related operations. Builds a dungeon as per the
 * specifications provided by the user and performs the operations on the player moves in dungeon.
 */
public class DungeonImpl implements Dungeon {

  private final int dungeonRows;
  private final int dungeonCols;
  private final RandomGenerate random;
  private final Cell[][] copyGrid;
  private GenericPlayer player;
  private final Cell[][] grid;
  private Location startPoint;
  private Location endPoint;
  private int endPointLevel;
  private GameResult gameResult;


  /**
   * Builds a dungeon object with specified parameters.
   *
   * @param random             Random number generator
   * @param dungeonRows        number of rows in the dungeon
   * @param dungeonCols        number of columns in the dungeon
   * @param interConnectivity  interconnectivity of the dungeon
   * @param wrappingNeeded     to determine wrapping or unwrapped dungeon
   * @param treasurePercentage percentage of caves to have treasure
   * @param demonCount         Count of demons to be added to the cell
   * @param playerName         name of the player
   * @throws IllegalArgumentException if dungeonRows, columns is less than 4
   *                                  if interconnectivity is less than 0, if treasure percentage
   *                                  is not in 0 -100 range
   *                                  if demonCount is less than 1 or more than half the number
   *                                  of cells in the cave
   */
  public DungeonImpl(RandomGenerate random, int dungeonRows, int dungeonCols, int interConnectivity,
                     boolean wrappingNeeded, int treasurePercentage, int demonCount,
                     String playerName) {
    this(random, dungeonRows, dungeonCols, interConnectivity, wrappingNeeded, treasurePercentage,
            demonCount, 0, 0, playerName);
  }

  /**
   * Builds a dungeon object with specified parameters includes a pit and a thief.
   *
   * @param random             Random number generator
   * @param dungeonRows        number of rows in the dungeon
   * @param dungeonCols        number of columns in the dungeon
   * @param interConnectivity  interconnectivity of the dungeon
   * @param wrappingNeeded     to determine wrapping or unwrapped dungeon
   * @param treasurePercentage percentage of caves to have treasure
   * @param demonCount         Count of demons to be added to the cell
   * @param pitCount           Count of pits to be added to the cell
   * @param thiefCount         Count of thieves to be added to the cell
   * @param playerName         name of the player
   * @throws IllegalArgumentException if dungeonRows, columns is less than 4
   *                                  if interconnectivity is less than 0, if treasure percentage
   *                                  is not in 0 -100 range
   *                                  if demonCount is less than 1 or more than half the number
   *                                  of cells in the cave
   */
  public DungeonImpl(RandomGenerate random, int dungeonRows, int dungeonCols, int interConnectivity,
                     boolean wrappingNeeded, int treasurePercentage, int demonCount, int pitCount,
                     int thiefCount, String playerName) {

    if (dungeonRows < 4 || dungeonCols < 4) {
      throw new IllegalArgumentException("Dungeon height and width can't be less than 5");
    }

    if (interConnectivity < 0 || interConnectivity > dungeonRows * dungeonCols) {
      throw new IllegalArgumentException("Inter connectivity can't be negative or more than total"
              + "number of cells in dungeon");
    }

    if (treasurePercentage < 0 || treasurePercentage > 100) {
      throw new IllegalArgumentException("Percentage of treasure to be added can't be negative or "
              + "more than 100");
    }

    if (demonCount < 1 || demonCount > (dungeonCols * dungeonRows) / 3) {
      throw new IllegalArgumentException("Demon count should be minimum 1 and less than one third "
              + "of the total cells in dungeon");
    }

    if (pitCount < 0 || pitCount > (dungeonCols * dungeonRows) / 20) {
      throw new IllegalArgumentException("Pit count should be minimum 0 and less than one 20th"
              + "of the total cells in dungeon");
    }

    if (thiefCount < 0 || thiefCount > (dungeonCols * dungeonRows) / 20) {
      throw new IllegalArgumentException("Thief count should be minimum 0 and less than one 20th"
              + "of the total cells in dungeon");
    }

    if (random == null) {
      throw new IllegalArgumentException("Random can't be null");
    }

    if (playerName == null || playerName.equals("")) {
      throw new IllegalArgumentException("Player name can't be null or empty");
    }


    this.dungeonRows = dungeonRows;
    this.dungeonCols = dungeonCols;
    player = new Player(playerName);
    this.random = random;
    this.startPoint = null;
    this.endPoint = null;
    this.gameResult = GameResult.PLAYING;

    grid = new CellImpl[dungeonRows][dungeonCols];
    copyGrid = new CellImpl[dungeonRows][dungeonCols];

    for (int i = 0; i < dungeonRows; i++) {
      for (int j = 0; j < dungeonCols; j++) {
        grid[i][j] = new CellImpl(i * dungeonCols + j);
      }
    }

    //Creating list of all possible edges
    List<DungeonEdge<Location>> allEdges = new ArrayList<>();
    for (int i = 0; i < dungeonRows - 1; i++) {
      for (int j = 0; j < dungeonCols - 1; j++) {
        allEdges.add(new DungeonEdge<Location>(new Location(i, j), new Location(i + 1, j)));
        allEdges.add(new DungeonEdge<Location>(new Location(i, j), new Location(i, j + 1)));
      }
    }

    for (int i = 0; i < dungeonCols - 1; i++) {
      allEdges.add(new DungeonEdge<Location>(new Location(dungeonRows - 1, i), new
              Location(dungeonRows - 1, i + 1)));
    }

    for (int i = 0; i < dungeonRows - 1; i++) {
      allEdges.add(new DungeonEdge<Location>(new Location(i, dungeonCols - 1), new
              Location(i + 1, dungeonCols - 1)));
    }

    if (wrappingNeeded) {
      for (int i = 0; i < dungeonRows; i++) {
        allEdges.add(new DungeonEdge<Location>(new Location(i, 0), new
                Location(i, dungeonCols - 1)));
      }

      for (int i = 0; i < dungeonCols; i++) {
        allEdges.add(new DungeonEdge<Location>(new Location(0, i), new
                Location(dungeonRows - 1, i)));
      }
    }

    List<DungeonEdge<Location>> edgesOfDungeon = krushkal(allEdges, grid, interConnectivity);

    //Connecting cells based on Krushkal's output
    for (DungeonEdge<Location> edge : edgesOfDungeon) {
      int x1 = edge.either().getX();
      int x2 = edge.other().getX();
      int y1 = edge.either().getY();
      int y2 = edge.other().getY();


      if (x1 == x2) {
        if (y1 < y2 && (y1 != 0 || y2 != dungeonCols - 1)) {
          grid[x1][y1].formConnection(Direction.EAST);
          grid[x2][y2].formConnection(Direction.WEST);
        } else {
          grid[x1][y1].formConnection(Direction.WEST);
          grid[x2][y2].formConnection(Direction.EAST);
        }

      }

      if (y1 == y2) {
        if (x1 < x2 && (x1 != 0 || x2 != dungeonRows - 1)) {
          grid[x1][y1].formConnection(Direction.SOUTH);
          grid[x2][y2].formConnection(Direction.NORTH);
        } else {
          grid[x1][y1].formConnection(Direction.NORTH);
          grid[x2][y2].formConnection(Direction.SOUTH);
        }
      }
    }

    //Marking caves as tunnels
    int numberOfCaves = 0;
    List<Cell> listOfCaves = new ArrayList<>();
    for (int i = 0; i < dungeonRows; i++) {
      for (int j = 0; j < dungeonCols; j++) {
        if (grid[i][j].getPossibleDirections().size() == 2) {
          grid[i][j].setAsTunnel();
        } else {
          listOfCaves.add(grid[i][j]);
          numberOfCaves++;
        }
      }
    }

    List<Cell> copyListOfCaves = new ArrayList<>(listOfCaves);
    //Adding treasure to Cells
    int numberOfCavesWithTreasure = (int) (1.0 * numberOfCaves * treasurePercentage) / 100;

    int treasureCount;
    int treasureToAdd;

    for (int i = 0; i < numberOfCavesWithTreasure; i++) {
      Cell tempCell = listOfCaves.remove(random.getRandom(0, listOfCaves.size() - 1));
      treasureCount = random.getRandom(1, 3);

      while (treasureCount-- > 0) {
        treasureToAdd = random.getRandom(1, 3);
        tempCell.addTreasure(factoryTreasure(treasureToAdd));
      }
    }

    //Adding arrows to cells
    int numberOfCellsWithArrows = (int) (1.0 * dungeonRows * dungeonCols * treasurePercentage)
            / 100;
    List<Cell> listOfCells = new ArrayList<>();
    for (int i = 0; i < dungeonRows; i++) {
      for (int j = 0; j < dungeonCols; j++) {
        listOfCells.add(grid[i][j]);
      }
    }

    for (int i = 0; i < numberOfCellsWithArrows; i++) {
      Cell tempCell = listOfCells.remove(random.getRandom(0, listOfCells.size() - 1));
      tempCell.addArrow();
    }

    startPoint = setStartPlayerLocation();
    player.setLocation(startPoint);
    grid[player.getPlayerLocation().getX()][player.getPlayerLocation().getY()].markVisited();
    endPoint = setEndPlayerLocation();

    //Adding demons to dungeon
    if (numberOfCaves <= demonCount) {
      throw new IllegalStateException("Number of caves in the dungeon are less than specified "
              + "demons count");
    }

    for (int i = 0; i < demonCount - 1; i++) {
      Cell tempCell = copyListOfCaves.remove(random.getRandom(0, copyListOfCaves.size() - 1));
      if (tempCell.equals(grid[startPoint.getX()][startPoint.getY()])
              || tempCell.equals(grid[endPoint.getX()][endPoint.getY()])) {
        i--;
        continue;
      }
      tempCell.addDemon();
    }

    grid[endPoint.getX()][endPoint.getY()].addDemon();


    //Adding pit to dungeon
    if (numberOfCaves <= pitCount) {
      throw new IllegalStateException("Number of caves in the dungeon are less than specified "
              + "pits count");
    }

    setThiefOrPit(pitCount, false);


    //Adding thief to dungeon
    if (numberOfCaves <= thiefCount) {
      throw new IllegalStateException("Number of caves in the dungeon are less than specified "
              + "thieves count");
    }

    setThiefOrPit(thiefCount, true);
    safeCopy();

  }

  private void setThiefOrPit(int count, boolean flag) {

    List<Cell> listOfCaves = new ArrayList<>();
    for (int i = 0; i < dungeonRows; i++) {
      for (int j = 0; j < dungeonCols; j++) {
        if (grid[i][j].getType() == CellType.CAVE) {
          listOfCaves.add(grid[i][j]);
        }
      }
    }

    for (int i = 0; i < count; i++) {
      Cell tempCell = listOfCaves.remove(random.getRandom(0, listOfCaves.size() - 1));

      if (tempCell.equals(grid[startPoint.getX()][startPoint.getY()]) || tempCell.equals(
              grid[endPoint.getX()][endPoint.getY()])) {
        i--;
        continue;
      }

      if (flag) {
        tempCell.addThief();
      } else {
        tempCell.addPit();
      }

    }
  }

  private void safeCopy() {

    for (int i = 0; i < dungeonRows; i++) {
      for (int j = 0; j < dungeonCols; j++) {
        copyGrid[i][j] = new CellImpl((CellImpl) grid[i][j]);
      }
    }

  }


  private Location setStartPlayerLocation() {

    List<Location> cavesList = new ArrayList<>();
    for (int i = 0; i < dungeonRows; i++) {
      for (int j = 0; j < dungeonCols; j++) {
        if (grid[i][j].getType() == CellType.CAVE) {
          cavesList.add(new Location(i, j));
        }
      }
    }

    return cavesList.get(random.getRandom(0, cavesList.size() - 1));

  }

  private Map<Integer, List<Location>> bfsOnGrid() {
    List<Location> list = new ArrayList<>();
    List<Location> cellsMoreThan5Distance = new ArrayList<>();
    LinkedList<List<Location>> queue = new LinkedList<>();
    Map<Integer, List<Location>> mapOfNodes = new HashMap<>();

    int level = 0;
    boolean[] visited = new boolean[dungeonRows * dungeonCols];
    list.add(startPoint);

    queue.add(list);
    visited[startPoint.getX() * dungeonCols + startPoint.getY()] = true;

    while (queue.size() != 0) {
      level++;
      List<Location> tempList = queue.poll();

      List<Location> newListForChildren = new ArrayList<>();

      for (Location l : tempList) {

        List<Direction> directionsList = grid[l.getX()][l.getY()].getPossibleDirections();
        for (Direction d : directionsList) {
          if (d == Direction.NORTH) {
            if (l.getX() == 0) {
              if (!visited[(dungeonRows - 1) * dungeonCols + l.getY()]) {
                newListForChildren.add(new Location(dungeonRows - 1, l.getY()));
                visited[(dungeonRows - 1) * dungeonCols + l.getY()] = true;
              }
            } else {
              if (!visited[(l.getX() - 1) * dungeonCols + l.getY()]) {
                newListForChildren.add(new Location(l.getX() - 1, l.getY()));
                visited[(l.getX() - 1) * dungeonCols + l.getY()] = true;
              }
            }

          }

          if (d == Direction.SOUTH) {
            if (l.getX() == dungeonRows - 1) {
              if (!visited[l.getY()]) {
                newListForChildren.add(new Location(0, l.getY()));
                visited[l.getY()] = true;
              }

            } else {
              if (!visited[(l.getX() + 1) * dungeonCols + l.getY()]) {
                newListForChildren.add(new Location(l.getX() + 1, l.getY()));
                visited[(l.getX() + 1) * dungeonCols + l.getY()] = true;
              }
            }
          }

          if (d == Direction.EAST) {
            if (l.getY() == dungeonCols - 1) {
              if (!visited[l.getX()]) {
                newListForChildren.add(new Location(l.getX(), 0));
                visited[l.getX()] = true;
              }

            } else {
              if (!visited[l.getX() * dungeonCols + l.getY() + 1]) {
                newListForChildren.add(new Location(l.getX(), l.getY() + 1));
                visited[l.getX() * dungeonCols + l.getY() + 1] = true;
              }

            }

          }

          if (d == Direction.WEST) {
            if (l.getY() == 0) {
              if (!visited[(l.getX()) * dungeonCols + dungeonCols - 1]) {
                newListForChildren.add(new Location(l.getX(), dungeonCols - 1));
                visited[(l.getX()) * dungeonCols + dungeonCols - 1] = true;
              }

            } else {
              if (!visited[l.getX() * dungeonCols + l.getY() - 1]) {
                newListForChildren.add(new Location(l.getX(), l.getY() - 1));
                visited[l.getX() * dungeonCols + l.getY() - 1] = true;
              }
            }
          }
        }

        if (level > 5 && (grid[l.getX()][l.getY()].getType() == CellType.CAVE)) {
          cellsMoreThan5Distance.add(l);
        }
      }

      if (cellsMoreThan5Distance.size() != 0) {
        mapOfNodes.put(level, cellsMoreThan5Distance);
        cellsMoreThan5Distance = new ArrayList<>();
      }
      if (newListForChildren.size() != 0) {
        queue.add(newListForChildren);
      }
    }

    return mapOfNodes;
  }


  private Location setEndPlayerLocation() {
    if (startPoint == null) {
      throw new IllegalStateException("Player start location should be set before end location");
    }

    Map<Integer, List<Location>> potentialEndPoints = bfsOnGrid();

    if (potentialEndPoints.size() == 0) {
      throw new IllegalArgumentException("Dungeon creation failed! End point at minimum 5 distance "
              + "can't be found for this start point and grid size");
    }

    List<Integer> levels = new ArrayList<>(potentialEndPoints.keySet());
    int level = levels.get(random.getRandom(0, levels.size() - 1));

    Location endPointTemp;
    endPointTemp = potentialEndPoints.get(level).get(random.getRandom(0,
            potentialEndPoints.get(level).size() - 1));
    endPointLevel = level;

    return endPointTemp;
  }

  @Override
  public void resetModel() {
    for (int i = 0; i < dungeonRows; i++) {
      for (int j = 0; j < dungeonCols; j++) {
        grid[i][j] = new CellImpl((CellImpl) copyGrid[i][j]);
      }
    }

    player = new Player(this.getPlayerName());
    player.setLocation(startPoint);
    gameResult = GameResult.PLAYING;
  }

  @Override
  public Location getStartPlayerLocation() {
    return startPoint;
  }

  @Override
  public Location getEndPlayerLocation() {
    return endPoint;
  }

  @Override
  public Location getPlayerCurrentLocation() {
    return player.getPlayerLocation();
  }

  @Override
  public CellType getCurrentLocationType() {
    return grid[player.getPlayerLocation().getX()][player.getPlayerLocation().getY()].getType();
  }

  @Override
  public Map<String, Integer> getCurrentLocTreasureDetails() {
    Location currentLocation = player.getPlayerLocation();

    return grid[currentLocation.getX()][currentLocation.getY()].getTreasure();
  }

  @Override
  public int getCurrentLocArrowDetails() {
    Location currentLocation = player.getPlayerLocation();

    return grid[currentLocation.getX()][currentLocation.getY()].getArrowCount();

  }

  @Override
  public DemonHealth getCurrentLocDemonDetails() {

    Location currentLocation = player.getPlayerLocation();
    if (grid[currentLocation.getX()][currentLocation.getY()].containsDemons()) {
      return grid[currentLocation.getX()][currentLocation.getY()].getDemon().getHealthState();
    }
    return null;
  }


  @Override
  public List<Direction> getCurrentLocPathDetails() {
    Location currentLocation = player.getPlayerLocation();
    return grid[currentLocation.getX()][currentLocation.getY()].getPossibleDirections();
  }

  @Override
  public Map<String, Integer> getPlayerDetails() {
    return new HashMap<>(player.getTreasure());
  }

  @Override
  public int getPlayerArrowsCollected() {
    return player.getArrowCount();
  }


  @Override
  public String getPlayerName() {
    return player.getName();
  }

  @Override
  public void movePlayer(Direction moveTo) {
    if (!grid[player.getPlayerLocation().getX()][player.getPlayerLocation().getY()]
            .getPossibleDirections().contains(moveTo)) {
      throw new IllegalArgumentException("Illegal move for this cell, player can't move in this "
              + "direction");
    }

    if (moveTo == null) {
      throw new IllegalArgumentException("Null direction provided");
    }

    Location currentLocation = player.getPlayerLocation();
    if (moveTo == Direction.NORTH) {
      if (currentLocation.getX() == 0) {
        player.setLocation(new Location(dungeonRows - 1, currentLocation.getY()));
      } else {
        player.setLocation(new Location(currentLocation.getX() - 1, currentLocation.getY()));
      }

    }

    if (moveTo == Direction.SOUTH) {
      if (currentLocation.getX() == dungeonRows - 1) {
        player.setLocation(new Location(0, currentLocation.getY()));
      } else {
        player.setLocation(new Location(currentLocation.getX() + 1, currentLocation.getY()));
      }
    }

    if (moveTo == Direction.EAST) {
      if (currentLocation.getY() == dungeonCols - 1) {
        player.setLocation(new Location(currentLocation.getX(), 0));
      } else {
        player.setLocation(new Location(currentLocation.getX(), currentLocation.getY() + 1));
      }
    }

    if (moveTo == Direction.WEST) {

      if (currentLocation.getY() == 0) {
        player.setLocation(new Location(currentLocation.getX(), dungeonCols - 1));
      } else {
        player.setLocation(new Location(currentLocation.getX(), currentLocation.getY() - 1));
      }
    }

    grid[player.getPlayerLocation().getX()][player.getPlayerLocation().getY()].markVisited();

  }

  @Override
  public Map<String, Integer> thiefCheck() {
    Location currentLocation = player.getPlayerLocation();
    Cell currentCell = grid[currentLocation.getX()][currentLocation.getY()];

    if (currentCell.containsThief()) {
      return player.removeTreasure();
    }

    return null;
  }

  @Override
  public boolean pitCheck() {
    Location currentLocation = player.getPlayerLocation();
    Cell currentCell = grid[currentLocation.getX()][currentLocation.getY()];
    if (currentCell.containsPit()) {
      gameResult = GameResult.LOSE;
      return true;
    }

    return false;
  }

  @Override
  public boolean endGameCheck() {
    Location currentLocation = player.getPlayerLocation();
    Cell currentCell = grid[currentLocation.getX()][currentLocation.getY()];

    if (currentCell.containsDemons()) {
      if (currentCell.getDemon().getHealthState() == DemonHealth.HEALTHY) {
        gameResult = GameResult.LOSE;
        return true;
      }

      if (currentCell.getDemon().getHealthState() == DemonHealth.INJURED) {
        if (random.getRandom(0, 1) == 0) {
          gameResult = GameResult.LOSE;
        } else {

          if (currentLocation.equals(endPoint)) {
            gameResult = GameResult.WIN;
          } else {
            gameResult = GameResult.PLAYING;
          }

        }
        return true;
      }
    }

    if (currentLocation.equals(endPoint)) {
      gameResult = GameResult.WIN;
    }

    return false;
  }


  @Override
  public void pickTreasure() {
    Location currentLocation = player.getPlayerLocation();

    if (grid[currentLocation.getX()][currentLocation.getY()].containsTreasure()) {
      player.pickTreasure(grid[currentLocation.getX()][currentLocation.getY()].removeTreasure());
    }

  }

  @Override
  public void pickArrows() {
    Location currentLocation = player.getPlayerLocation();

    if (grid[currentLocation.getX()][currentLocation.getY()].getArrowCount() > 0) {
      grid[currentLocation.getX()][currentLocation.getY()].removeArrows();
      player.addArrow();
    }
  }

  @Override
  public DemonHit shootArrow(Direction shootDirection, int distance) {

    if (shootDirection == null) {
      throw new IllegalArgumentException("Shoot direction can't be null");
    }

    if (distance > dungeonCols * dungeonRows) {
      throw new IllegalArgumentException("Maximum distance arrow can travel is "
              + dungeonCols * dungeonRows);
    }

    if (distance < 1) {
      throw new IllegalArgumentException("Distance must be more than 1");
    }
    Location currentLocation = player.getPlayerLocation();

    if (!grid[currentLocation.getX()][currentLocation.getY()].getPossibleDirections()
            .contains(shootDirection)) {
      throw new IllegalArgumentException("Can't shoot an arrow in " + shootDirection
              + " direction");
    }

    if (player.getArrowCount() < 1) {
      throw new IllegalStateException("You are out of arrows");
    }

    int count = 0;
    player.removeArrow();
    Location tempLocation = currentLocation;
    Direction tempDirection = shootDirection;
    tempLocation = calculateLocBasedOnDirection(tempLocation, tempDirection);
    Cell tempCell = grid[tempLocation.getX()][tempLocation.getY()];
    if (tempCell.getType() == CellType.CAVE) {
      count++;
    }
    while (count < distance) {

      Direction entryDirection;
      if (tempDirection == Direction.EAST) {
        entryDirection = Direction.WEST;
      } else if (tempDirection == Direction.WEST) {
        entryDirection = Direction.EAST;
      } else if (tempDirection == Direction.NORTH) {
        entryDirection = Direction.SOUTH;
      } else {
        entryDirection = Direction.NORTH;
      }

      if (tempCell.getType() == CellType.TUNNEL) {
        if (tempCell.getPossibleDirections().get(0) == entryDirection) {
          tempDirection = tempCell.getPossibleDirections().get(1);
        } else {
          tempDirection = tempCell.getPossibleDirections().get(0);
        }

      } else {
        boolean validDirection = false;
        for (Direction d : tempCell.getPossibleDirections()) {
          if ((entryDirection == Direction.EAST && d == Direction.WEST)
                  || (entryDirection == Direction.NORTH && d == Direction.SOUTH)
                  || (entryDirection == Direction.WEST && d == Direction.EAST)
                  || (entryDirection == Direction.SOUTH && d == Direction.NORTH)) {
            tempDirection = d;
            validDirection = true;
            break;
          }

        }

        if (!validDirection) {
          return DemonHit.MISS;

        }

      }

      tempLocation = calculateLocBasedOnDirection(tempLocation, tempDirection);
      tempCell = grid[tempLocation.getX()][tempLocation.getY()];

      if (tempCell.getType() == CellType.CAVE) {
        count++;
      }

    }

    if (tempCell.containsDemons()) {
      tempCell.getDemon().decrementHealthState();
      if (tempCell.getDemon().getHealthState() == DemonHealth.DEAD) {
        tempCell.removeDemon();
        return DemonHit.KILL;
      }

      return DemonHit.HIT;
    }

    return DemonHit.MISS;
  }

  private Location calculateLocBasedOnDirection(Location currentLocation, Direction moveTo) {

    if (moveTo == Direction.NORTH) {
      if (currentLocation.getX() == 0) {
        return new Location(dungeonRows - 1, currentLocation.getY());
      } else {
        return new Location(currentLocation.getX() - 1, currentLocation.getY());
      }

    }

    if (moveTo == Direction.SOUTH) {
      if (currentLocation.getX() == dungeonRows - 1) {
        return new Location(0, currentLocation.getY());
      } else {
        return new Location(currentLocation.getX() + 1, currentLocation.getY());
      }
    }

    if (moveTo == Direction.EAST) {
      if (currentLocation.getY() == dungeonCols - 1) {
        return new Location(currentLocation.getX(), 0);
      } else {
        return new Location(currentLocation.getX(), currentLocation.getY() + 1);
      }
    }

    if (moveTo == Direction.WEST) {

      if (currentLocation.getY() == 0) {
        return new Location(currentLocation.getX(), dungeonCols - 1);
      } else {
        return new Location(currentLocation.getX(), currentLocation.getY() - 1);
      }
    }
    return currentLocation;
  }

  @Override
  public DemonSmell getDemonSmell() {
    Location currentLocation = player.getPlayerLocation();
    Cell currentCell = grid[currentLocation.getX()][currentLocation.getY()];

    if (currentCell.containsDemons()) {
      return DemonSmell.STRONG;
    }
    int smellStrength = 0;
    Location nextLocation;
    for (Direction nextMoveDirection : currentCell.getPossibleDirections()) {
      nextLocation = calculateLocBasedOnDirection(currentLocation, nextMoveDirection);
      Cell nextCell = grid[nextLocation.getX()][nextLocation.getY()];
      if (nextCell.containsDemons()) {
        smellStrength = smellStrength + 2;
        break;
      }

      Location nextNextLocation;
      for (Direction nextNextMoveDirection : nextCell.getPossibleDirections()) {
        nextNextLocation = calculateLocBasedOnDirection(nextLocation, nextNextMoveDirection);
        Cell nextNextCell = grid[nextNextLocation.getX()][nextNextLocation.getY()];
        if (nextNextCell.containsDemons()) {
          smellStrength = smellStrength + 1;
        }

        if (smellStrength > 1) {
          break;
        }
      }
      if (smellStrength > 1) {
        break;
      }

    }

    if (smellStrength == 0) {
      return DemonSmell.NONE;
    }

    if (smellStrength == 1) {
      return DemonSmell.WEAK;
    } else {
      return DemonSmell.STRONG;
    }
  }

  @Override
  public boolean getPitSound() {

    Location currentLocation = player.getPlayerLocation();
    Cell currentCell = grid[currentLocation.getX()][currentLocation.getY()];

    if (currentCell.containsPit()) {
      return true;
    }

    for (Direction nextMoveDirection : currentCell.getPossibleDirections()) {
      Location nextLocation = calculateLocBasedOnDirection(currentLocation, nextMoveDirection);
      Cell nextCell = grid[nextLocation.getX()][nextLocation.getY()];
      if (nextCell.containsPit()) {
        return true;
      }
    }
    return false;
  }

  @Override
  public GameResult getGameResult() {
    return gameResult;
  }


  @Override
  public boolean isGameOver() {

    return gameResult != GameResult.PLAYING;
  }

  @Override
  public CellReadOnly[][] getDungeon() {
    CellReadOnly[][] copyGrid = new CellReadOnly[dungeonRows][dungeonCols];
    for (int r = 0; r < grid.length; r++) {
      copyGrid[r] = Arrays.copyOf(grid[r], grid[r].length);
    }
    return copyGrid;
  }

  @Override
  public boolean isVisited(int i, int j) {

    if (i < 0 || j < 0 || i >= dungeonRows || j >= dungeonCols) {
      throw new IllegalArgumentException("Invalid location co-ordinates");
    }
    return grid[i][j].isVisited();
  }


  private Treasure factoryTreasure(int id) {
    if (id == 1) {
      return new Diamonds();
    }
    if (id == 2) {
      return new Rubies();
    } else {
      return new Sapphires();
    }
  }

  private List<DungeonEdge<Location>> krushkal(List<DungeonEdge<Location>> allEdges, Cell[][] grid,
                                               int interConnectivity) {

    int m = grid.length;
    int n = grid.length;
    List<DungeonEdge<Location>> leftOver = new ArrayList<>();
    List<DungeonEdge<Location>> path = new ArrayList<>();
    UnionFind uf = new UnionFind(m * n);

    while (allEdges.size() > 0) {
      DungeonEdge<Location> tempEdge = allEdges.remove(random.getRandom(0, allEdges.size() - 1));

      int ufIdA = tempEdge.either().getX() * n + tempEdge.either().getY();
      int ufIdB = tempEdge.other().getX() * n + tempEdge.other().getY();

      if (uf.connected(ufIdA, ufIdB)) {
        leftOver.add(tempEdge);
      } else {
        uf.union(ufIdA, ufIdB);
        path.add(tempEdge);
      }
    }

    if (interConnectivity > leftOver.size()) {
      throw new IllegalStateException("Interconnectivity value is too high");
    }

    for (int i = 0; i < interConnectivity; i++) {
      path.add(leftOver.remove(random.getRandom(0, leftOver.size() - 1)));
    }

    return path;
  }

  @Override
  public String toString() {

    StringBuilder tempString = new StringBuilder();

    int numberOfCaves = 0;
    int numberOfCavesWithTreasure = 0;
    int numberOfEdges = 0;

    for (int i = 0; i < dungeonRows; i++) {
      for (int j = 0; j < dungeonCols; j++) {
        tempString.append("(");
        tempString.append(i);
        tempString.append(",");
        tempString.append(j);
        tempString.append(")");
        tempString.append(grid[i][j].toString());
        tempString.append("\n");
        numberOfEdges = numberOfEdges + grid[i][j].getPossibleDirections().size();
        if (grid[i][j].getType() == CellType.CAVE) {
          numberOfCaves++;
        }
        if (grid[i][j].getTreasure().size() > 0) {
          numberOfCavesWithTreasure++;
        }
      }
    }

    tempString.append(";");
    tempString.append("NumberOfEdges:");
    tempString.append(numberOfEdges / 2);
    tempString.append(";");
    tempString.append("NumberOfCaves:");
    tempString.append(numberOfCaves);
    tempString.append(";");
    tempString.append("NumberOfCavesWithTreasure:");
    tempString.append(numberOfCavesWithTreasure);
    tempString.append(";");
    tempString.append(endPointLevel);


    return tempString.toString();
  }


}