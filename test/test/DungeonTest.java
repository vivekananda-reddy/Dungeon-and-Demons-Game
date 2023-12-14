package test;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import constants.CellType;
import constants.DemonHit;
import constants.DemonSmell;
import constants.Direction;
import constants.GameResult;
import dungeon.CellReadOnly;
import dungeon.Dungeon;
import dungeon.DungeonImpl;
import dungeon.Location;
import randoms.RandomAuto;
import randoms.RandomControlled;
import randoms.RandomGenerate;

import static org.junit.Assert.assertEquals;


/**
 * A class to test Dungeon interface and related classes.
 */
public class DungeonTest {

  Dungeon testDungeon;
  Dungeon testDungeonThiefPit;
  RandomGenerate random;
  RandomGenerate randomControlled;


  @Before
  public void setUp() throws Exception {
    random = new RandomAuto();
    randomControlled = new RandomControlled();
    testDungeon = new DungeonImpl(randomControlled,6,6,2,
            false,20,1,"player1");

    testDungeonThiefPit = new DungeonImpl(randomControlled,6,6,2,
            false,20,1,1,1,"player1");
  }

  @Test
  public void testInterconnectivityTreasurePercent() {
    RandomGenerate randomControlledLocal = new RandomControlled();
    Dungeon testDungeonLocal = new DungeonImpl(randomControlledLocal,6,6,
            2,true,20,1,0,0,"player1");


    String s = testDungeonLocal.toString();
    String[] tokens = s.split(";");

    String[] edgesToken = tokens[1].split(":");
    int numberOfEdges = Integer.parseInt(edgesToken[1]);
    String[] cavesToken = tokens[2].split(":");
    int numberOfCaves = Integer.parseInt(cavesToken[1]);
    String[] cavesTreasureToken = tokens[3].split(":");
    int numberOfCavesWithTreasure = Integer.parseInt(cavesTreasureToken[1]);

    //testing percentage of caves with treasure
    assertEquals(20,(numberOfCavesWithTreasure * 100) / numberOfCaves);

    //testing interconnectivity
    assertEquals(2,numberOfEdges - 36 + 1);
  }

  @Test
  public void testStartToEndDistance() {
    Location startPoint = testDungeon.getStartPlayerLocation();
    Location endPoint = testDungeon.getEndPlayerLocation();
    String s = testDungeon.toString();
    String[] tokens = s.split(";");
    int level = Integer.parseInt(tokens[tokens.length - 1]);
    assertEquals(true, level > 5);
  }


  @Test
  public void testSingleRoundKillDemon() {

    Dungeon testDungeonLocal = new DungeonImpl(randomControlled,6,6,
            2,false,20,5,0,0,"player1");

    Location startPoint = testDungeonLocal.getStartPlayerLocation();
    Location endPoint = testDungeonLocal.getEndPlayerLocation();

    assertEquals(new Location(2,5),testDungeonLocal.getPlayerCurrentLocation());
    Map<String,Integer> treasureMap = testDungeonLocal.getCurrentLocTreasureDetails();
    assertEquals(1,testDungeonLocal.getCurrentLocArrowDetails());
    assertEquals(3,testDungeonLocal.getPlayerArrowsCollected());
    assertEquals(null, testDungeonLocal.getCurrentLocDemonDetails());
    testDungeonLocal.pickTreasure();
    testDungeonLocal.pickArrows();

    assertEquals(treasureMap, testDungeonLocal.getPlayerDetails());
    assertEquals(4,testDungeonLocal.getPlayerArrowsCollected());
    assertEquals(0, testDungeonLocal.getCurrentLocTreasureDetails().size());
    assertEquals(0, testDungeonLocal.getCurrentLocArrowDetails());

    assertEquals(false, testDungeonLocal.endGameCheck());
    assertEquals(DemonSmell.STRONG, testDungeonLocal.getDemonSmell());
    assertEquals(DemonHit.HIT, testDungeonLocal.shootArrow(Direction.WEST,1));
    assertEquals(DemonHit.KILL, testDungeonLocal.shootArrow(Direction.WEST,1));
    assertEquals(2,testDungeonLocal.getPlayerArrowsCollected());



    assertEquals(GameResult.PLAYING, testDungeonLocal.getGameResult());
    assertEquals(false, testDungeonLocal.isGameOver());

    assertEquals(DemonSmell.WEAK, testDungeonLocal.getDemonSmell());
    assertEquals(DemonHit.HIT, testDungeonLocal.shootArrow(Direction.WEST,2));
    assertEquals(DemonHit.KILL, testDungeonLocal.shootArrow(Direction.WEST,2));
    assertEquals(DemonSmell.NONE, testDungeonLocal.getDemonSmell());
    assertEquals(0,testDungeonLocal.getPlayerArrowsCollected());

    List<Direction> path = testDungeonLocal.getCurrentLocPathDetails();
    testDungeonLocal.movePlayer(path.get(0));
    assertEquals(false, testDungeonLocal.endGameCheck());

    assertEquals(new Location(2,4), testDungeonLocal.getPlayerCurrentLocation());
    assertEquals(GameResult.PLAYING, testDungeonLocal.getGameResult());
    assertEquals(false, testDungeonLocal.isGameOver());

  }

  @Test
  public void testSingleRoundPlayerDead() {

    Dungeon testDungeonLocal = new DungeonImpl(randomControlled,6,6,
            2,false,20,10,0,0,"player1");

    Location startPoint = testDungeonLocal.getStartPlayerLocation();
    Location endPoint = testDungeonLocal.getEndPlayerLocation();

    assertEquals(new Location(2,5),testDungeonLocal.getPlayerCurrentLocation());
    Map<String,Integer> treasureMap = testDungeonLocal.getCurrentLocTreasureDetails();
    assertEquals(1,testDungeonLocal.getCurrentLocArrowDetails());
    assertEquals(3,testDungeonLocal.getPlayerArrowsCollected());
    assertEquals(null, testDungeonLocal.getCurrentLocDemonDetails());
    assertEquals(false, testDungeonLocal.endGameCheck());
    testDungeonLocal.pickTreasure();
    testDungeonLocal.pickArrows();

    assertEquals(treasureMap, testDungeonLocal.getPlayerDetails());
    assertEquals(4,testDungeonLocal.getPlayerArrowsCollected());

    assertEquals(0, testDungeonLocal.getCurrentLocTreasureDetails().size());
    assertEquals(0, testDungeonLocal.getCurrentLocArrowDetails());

    assertEquals(DemonSmell.STRONG, testDungeonLocal.getDemonSmell());
    assertEquals(DemonHit.MISS, testDungeonLocal.shootArrow(Direction.WEST,4));
    assertEquals(3,testDungeonLocal.getPlayerArrowsCollected());
    assertEquals(DemonHit.HIT, testDungeonLocal.shootArrow(Direction.WEST,5));

    assertEquals(GameResult.PLAYING, testDungeonLocal.getGameResult());
    assertEquals(false, testDungeonLocal.isGameOver());


    List<Direction> path = testDungeonLocal.getCurrentLocPathDetails();
    testDungeonLocal.movePlayer(path.get(0));
    assertEquals(true, testDungeonLocal.endGameCheck());

    assertEquals(new Location(2,4), testDungeonLocal.getPlayerCurrentLocation());
    assertEquals(DemonSmell.STRONG, testDungeonLocal.getDemonSmell());
    assertEquals(GameResult.LOSE, testDungeonLocal.getGameResult());
    assertEquals(true, testDungeonLocal.isGameOver());

  }

  @Test
  public void testSingleRoundDemonInjured() {

    Dungeon testDungeonLocal = new DungeonImpl(randomControlled,6,6,
            2,false,20,10,0,0,"player1");

    Location startPoint = testDungeonLocal.getStartPlayerLocation();
    Location endPoint = testDungeonLocal.getEndPlayerLocation();

    assertEquals(new Location(2,5),testDungeonLocal.getPlayerCurrentLocation());
    Map<String,Integer> treasureMap = testDungeonLocal.getCurrentLocTreasureDetails();
    assertEquals(1,testDungeonLocal.getCurrentLocArrowDetails());
    assertEquals(3,testDungeonLocal.getPlayerArrowsCollected());
    assertEquals(null, testDungeonLocal.getCurrentLocDemonDetails());
    assertEquals(false, testDungeonLocal.endGameCheck());
    testDungeonLocal.pickTreasure();
    testDungeonLocal.pickArrows();

    assertEquals(treasureMap, testDungeonLocal.getPlayerDetails());
    assertEquals(4,testDungeonLocal.getPlayerArrowsCollected());
    assertEquals(0, testDungeonLocal.getCurrentLocTreasureDetails().size());
    assertEquals(0, testDungeonLocal.getCurrentLocArrowDetails());

    assertEquals(DemonSmell.STRONG, testDungeonLocal.getDemonSmell());
    assertEquals(DemonHit.HIT, testDungeonLocal.shootArrow(Direction.WEST,1));
    assertEquals(3,testDungeonLocal.getPlayerArrowsCollected());
    assertEquals(GameResult.PLAYING, testDungeonLocal.getGameResult());
    assertEquals(false, testDungeonLocal.isGameOver());


    List<Direction> path = testDungeonLocal.getCurrentLocPathDetails();
    testDungeonLocal.movePlayer(path.get(0));
    assertEquals(true, testDungeonLocal.endGameCheck());

    assertEquals(new Location(2,4), testDungeonLocal.getPlayerCurrentLocation());
    assertEquals(DemonSmell.STRONG, testDungeonLocal.getDemonSmell());
    assertEquals(GameResult.LOSE, testDungeonLocal.getGameResult());
    assertEquals(true, testDungeonLocal.isGameOver());

  }

  @Test
  public void testTraverseAllNodesUnWrapped() {
    Location startPoint = testDungeon.getStartPlayerLocation();
    Location endPoint = testDungeon.getEndPlayerLocation();

    boolean[] visited = new boolean[36];
    int nodesCount = 0;
    int loopCounter = 0;

    while (nodesCount != 36 && loopCounter < 50000) {

      Location current = testDungeon.getPlayerCurrentLocation();
      if (!visited[current.getX() * 6 + current.getY()]) {
        visited[current.getX() * 6 + current.getY()] = true;
        nodesCount++;
      }
      List<Direction> directionsList = testDungeon.getCurrentLocPathDetails();
      Direction chosenDirection = directionsList.get(random.getRandom(0,
              directionsList.size() - 1));
      testDungeon.movePlayer(chosenDirection);
      loopCounter++;

    }
    //verifying if all nodes are visited with in 50000 random iterations
    assertEquals(36,nodesCount);
  }

  @Test
  public void testTraverseAllNodesWrapped() {
    Dungeon testDungeonLocal = new DungeonImpl(randomControlled,6, 6,
            2,true,20, 1,0,0,"player1");
    Location startPoint = testDungeonLocal.getStartPlayerLocation();
    Location endPoint = testDungeonLocal.getEndPlayerLocation();

    boolean[] visited = new boolean[36];
    int nodesCount = 0;
    int loopCounter = 0;

    while (nodesCount != 36 && loopCounter < 50000) {

      Location current = testDungeonLocal.getPlayerCurrentLocation();
      if (!visited[current.getX() * 6 + current.getY()]) {
        visited[current.getX() * 6 + current.getY()] = true;
        nodesCount++;
      }
      List<Direction> directionsList = testDungeonLocal.getCurrentLocPathDetails();
      Direction chosenDirection = directionsList.get(random.getRandom(0,
              directionsList.size() - 1));
      testDungeonLocal.movePlayer(chosenDirection);
      loopCounter++;

    }
    //verifying if all nodes are visited with in 50000 random iterations
    assertEquals(36,nodesCount);
  }

  @Test
  public void testFullGameForUnWrappingDungeon() {
    Location startPoint = testDungeon.getStartPlayerLocation();
    Location endPoint = testDungeon.getEndPlayerLocation();

    while (!testDungeon.isGameOver()) {
      testDungeon.pickTreasure();
      List<Direction> directionsList = testDungeon.getCurrentLocPathDetails();
      Direction chosenDirection = directionsList.get(random.getRandom(0,
              directionsList.size() - 1));
      testDungeon.movePlayer(chosenDirection);
      testDungeon.endGameCheck();
    }

    assertEquals(endPoint,testDungeon.getPlayerCurrentLocation());
    assertEquals(GameResult.LOSE,testDungeon.getGameResult());

  }

  @Test
  public void testFullGameForWrappingDungeon() {
    Dungeon testWrappingDungeon = new DungeonImpl(randomControlled,6,6,
            2,true,20, 1,"player1");
    Location startPoint = testWrappingDungeon.getStartPlayerLocation();
    Location endPoint = testWrappingDungeon.getEndPlayerLocation();

    while (!testWrappingDungeon.isGameOver()) {
      testWrappingDungeon.pickTreasure();
      List<Direction> directionsList = testWrappingDungeon.getCurrentLocPathDetails();
      Direction chosenDirection = directionsList.get(random.getRandom(0,
              directionsList.size() - 1));
      testWrappingDungeon.movePlayer(chosenDirection);
      testWrappingDungeon.endGameCheck();
    }
    assertEquals(endPoint,testWrappingDungeon.getPlayerCurrentLocation());
    assertEquals(GameResult.LOSE,testWrappingDungeon.getGameResult());

  }

  @Test
  public void testFullGameWin() {

    String dungeonMap = testDungeon.toString();
    String[] tokens = dungeonMap.split("demon");
    int actualDemons = tokens.length - 1;
    assertEquals(1,actualDemons);
    assertEquals(false, testDungeon.endGameCheck());
    testDungeon.movePlayer(Direction.WEST);
    testDungeon.endGameCheck();
    testDungeon.movePlayer(Direction.WEST);
    testDungeon.endGameCheck();
    testDungeon.movePlayer(Direction.WEST);
    testDungeon.endGameCheck();
    testDungeon.movePlayer(Direction.SOUTH);
    testDungeon.endGameCheck();
    testDungeon.movePlayer(Direction.SOUTH);
    testDungeon.endGameCheck();
    assertEquals(false, testDungeon.isGameOver());
    assertEquals(GameResult.PLAYING, testDungeon.getGameResult());
    assertEquals(DemonHit.HIT,testDungeon.shootArrow(Direction.SOUTH, 1));
    assertEquals(DemonHit.KILL,testDungeon.shootArrow(Direction.SOUTH, 1));
    testDungeon.movePlayer(Direction.SOUTH);
    testDungeon.endGameCheck();
    assertEquals(true, testDungeon.isGameOver());
    assertEquals(GameResult.WIN, testDungeon.getGameResult());

  }

  @Test
  public void testArrowAndDemonCount() {
    Dungeon testDungeonLocal = new DungeonImpl(randomControlled, 6,6,
            2,false,20, 4, "player1");

    String dungeonMap = testDungeonLocal.toString();
    String[] tokens = dungeonMap.split("demon");
    int actualDemons = tokens.length - 1;
    assertEquals(4,actualDemons);

    tokens = dungeonMap.split("arrow");
    int actualArrows = tokens.length - 1;
    int expectedArrows = (int)(1.0 * 6 * 6 * 20) / 100;
    assertEquals(expectedArrows,actualArrows);

  }

  @Test
  public void testIsVisited() {
    CellReadOnly[][] grid = testDungeon.getDungeon();
    Location start = testDungeon.getStartPlayerLocation();
    System.out.println(start);
    testDungeon.movePlayer(Direction.WEST);
    assertEquals(true, testDungeon.isVisited(start.getX(),start.getY()));
    testDungeon.movePlayer(Direction.WEST);
    assertEquals(true, testDungeon.isVisited(start.getX(), start.getY() - 1));

    assertEquals(false, testDungeon.isVisited(start.getX() + 1, start.getY()));


  }


  @Test
  public void testThief() {
    testDungeonThiefPit.pickTreasure();
    assertEquals(null,testDungeonThiefPit.thiefCheck());
    testDungeonThiefPit.movePlayer(Direction.WEST);

    assertEquals(null,testDungeonThiefPit.thiefCheck());
    testDungeonThiefPit.movePlayer(Direction.WEST);

    assertEquals(null,testDungeonThiefPit.thiefCheck());
    testDungeonThiefPit.movePlayer(Direction.WEST);

    assertEquals(null,testDungeonThiefPit.thiefCheck());
    testDungeonThiefPit.movePlayer(Direction.SOUTH);

    assertEquals(null,testDungeonThiefPit.thiefCheck());
    testDungeonThiefPit.movePlayer(Direction.WEST);


    Map<String,Integer> treasure = testDungeonThiefPit.getPlayerDetails();
    assertEquals(treasure,testDungeonThiefPit.thiefCheck());

    Map<String,Integer> noTreasure = new HashMap<>();
    assertEquals(noTreasure, testDungeonThiefPit.getPlayerDetails());

    testDungeonThiefPit.movePlayer(Direction.EAST);
    assertEquals(null,testDungeonThiefPit.thiefCheck());

    testDungeonThiefPit.movePlayer(Direction.WEST);
    assertEquals(noTreasure,testDungeonThiefPit.thiefCheck());

  }

  @Test
  public void testPitWithGameOver() {


    assertEquals(false,testDungeonThiefPit.getPitSound());
    assertEquals(false, testDungeonThiefPit.pitCheck());
    assertEquals(false, testDungeonThiefPit.isGameOver());
    assertEquals(GameResult.PLAYING, testDungeonThiefPit.getGameResult());

    testDungeonThiefPit.movePlayer(Direction.WEST);

    assertEquals(false,testDungeonThiefPit.getPitSound());
    assertEquals(false, testDungeonThiefPit.pitCheck());
    assertEquals(false, testDungeonThiefPit.isGameOver());
    assertEquals(GameResult.PLAYING, testDungeonThiefPit.getGameResult());

    testDungeonThiefPit.movePlayer(Direction.WEST);

    assertEquals(false,testDungeonThiefPit.getPitSound());
    assertEquals(false, testDungeonThiefPit.pitCheck());
    assertEquals(false, testDungeonThiefPit.isGameOver());
    assertEquals(GameResult.PLAYING, testDungeonThiefPit.getGameResult());

    testDungeonThiefPit.movePlayer(Direction.WEST);

    assertEquals(false,testDungeonThiefPit.getPitSound());
    assertEquals(false, testDungeonThiefPit.pitCheck());
    assertEquals(false, testDungeonThiefPit.isGameOver());
    assertEquals(GameResult.PLAYING, testDungeonThiefPit.getGameResult());

    testDungeonThiefPit.movePlayer(Direction.SOUTH);


    assertEquals(true,testDungeonThiefPit.getPitSound());
    assertEquals(false, testDungeonThiefPit.pitCheck());
    assertEquals(false, testDungeonThiefPit.isGameOver());
    assertEquals(GameResult.PLAYING, testDungeonThiefPit.getGameResult());

    testDungeonThiefPit.movePlayer(Direction.WEST);

    assertEquals(true,testDungeonThiefPit.getPitSound());
    assertEquals(true, testDungeonThiefPit.pitCheck());
    assertEquals(true, testDungeonThiefPit.isGameOver());
    assertEquals(GameResult.LOSE, testDungeonThiefPit.getGameResult());
  }

  @Test
  public void testPitWithOutGameOver() {

    assertEquals(false,testDungeonThiefPit.getPitSound());
    assertEquals(false, testDungeonThiefPit.isGameOver());
    assertEquals(GameResult.PLAYING, testDungeonThiefPit.getGameResult());

    testDungeonThiefPit.movePlayer(Direction.WEST);

    assertEquals(false,testDungeonThiefPit.getPitSound());
    assertEquals(false, testDungeonThiefPit.isGameOver());
    assertEquals(GameResult.PLAYING, testDungeonThiefPit.getGameResult());

    testDungeonThiefPit.movePlayer(Direction.WEST);

    assertEquals(false,testDungeonThiefPit.getPitSound());
    assertEquals(false, testDungeonThiefPit.isGameOver());
    assertEquals(GameResult.PLAYING, testDungeonThiefPit.getGameResult());

    testDungeonThiefPit.movePlayer(Direction.WEST);

    assertEquals(false,testDungeonThiefPit.getPitSound());
    assertEquals(false, testDungeonThiefPit.isGameOver());
    assertEquals(GameResult.PLAYING, testDungeonThiefPit.getGameResult());

    testDungeonThiefPit.movePlayer(Direction.SOUTH);

    assertEquals(true,testDungeonThiefPit.getPitSound());
    assertEquals(false, testDungeonThiefPit.isGameOver());
    assertEquals(GameResult.PLAYING, testDungeonThiefPit.getGameResult());

    testDungeonThiefPit.movePlayer(Direction.WEST);

    assertEquals(true,testDungeonThiefPit.getPitSound());
    assertEquals(false, testDungeonThiefPit.isGameOver());
    assertEquals(GameResult.PLAYING, testDungeonThiefPit.getGameResult());

    testDungeonThiefPit.movePlayer(Direction.WEST);

    assertEquals(true,testDungeonThiefPit.getPitSound());
    assertEquals(false, testDungeonThiefPit.isGameOver());
    assertEquals(GameResult.PLAYING, testDungeonThiefPit.getGameResult());

  }

  @Test
  public void testPitCount() {
    Dungeon testDungeonLocal = new DungeonImpl(randomControlled,10,10,2,
            false,20,1,3,2,"player1");
    CellReadOnly[][] grid = testDungeonLocal.getDungeon();
    int pitCount = 0;
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[0].length; j++) {
        if (grid[i][j].containsPit()) {
          assertEquals(true, grid[i][j].getType() == CellType.CAVE);
          pitCount++;
        }
      }
    }

    assertEquals(3, pitCount);

  }

  @Test
  public void testThiefCountCellType() {
    Dungeon testDungeonLocal = new DungeonImpl(randomControlled,10,10,2,
            false,20,1,3,2,"player1");
    CellReadOnly[][] grid = testDungeonLocal.getDungeon();
    int thiefCount = 0;
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[0].length; j++) {
        if (grid[i][j].containsThief()) {
          assertEquals(true, grid[i][j].getType() == CellType.CAVE);
          thiefCount++;
        }
      }
    }

    assertEquals(2, thiefCount);

  }

  @Test
  public void testResetModel() {
    Dungeon testDungeonLocal = new DungeonImpl(randomControlled,6,6,
            2,false,20,10,0,0,"player1");

    Location startPoint = testDungeonLocal.getStartPlayerLocation();
    Location endPoint = testDungeonLocal.getEndPlayerLocation();


    assertEquals(new Location(2,5),testDungeonLocal.getPlayerCurrentLocation());
    Map<String,Integer> treasureMap = testDungeonLocal.getCurrentLocTreasureDetails();
    assertEquals(1,testDungeonLocal.getCurrentLocArrowDetails());
    assertEquals(3,testDungeonLocal.getPlayerArrowsCollected());
    assertEquals(null, testDungeonLocal.getCurrentLocDemonDetails());
    assertEquals(false, testDungeonLocal.endGameCheck());
    testDungeonLocal.pickTreasure();
    testDungeonLocal.pickArrows();

    assertEquals(treasureMap, testDungeonLocal.getPlayerDetails());
    assertEquals(4,testDungeonLocal.getPlayerArrowsCollected());

    assertEquals(0, testDungeonLocal.getCurrentLocTreasureDetails().size());
    assertEquals(0, testDungeonLocal.getCurrentLocArrowDetails());

    assertEquals(DemonSmell.STRONG, testDungeonLocal.getDemonSmell());
    assertEquals(DemonHit.MISS, testDungeonLocal.shootArrow(Direction.WEST,4));
    assertEquals(3,testDungeonLocal.getPlayerArrowsCollected());
    assertEquals(DemonHit.HIT, testDungeonLocal.shootArrow(Direction.WEST,5));

    assertEquals(GameResult.PLAYING, testDungeonLocal.getGameResult());
    assertEquals(false, testDungeonLocal.isGameOver());


    List<Direction> path = testDungeonLocal.getCurrentLocPathDetails();
    testDungeonLocal.movePlayer(path.get(0));
    assertEquals(true, testDungeonLocal.endGameCheck());

    assertEquals(new Location(2,4), testDungeonLocal.getPlayerCurrentLocation());
    assertEquals(DemonSmell.STRONG, testDungeonLocal.getDemonSmell());
    assertEquals(GameResult.LOSE, testDungeonLocal.getGameResult());
    assertEquals(true, testDungeonLocal.isGameOver());

    testDungeonLocal.resetModel();

    assertEquals(GameResult.PLAYING, testDungeonLocal.getGameResult());
    assertEquals(false, testDungeonLocal.isGameOver());

    assertEquals(startPoint, testDungeonLocal.getPlayerCurrentLocation());

    assertEquals(new Location(2,5),testDungeonLocal.getPlayerCurrentLocation());
    treasureMap = testDungeonLocal.getCurrentLocTreasureDetails();

    assertEquals(1,testDungeonLocal.getCurrentLocArrowDetails());
    assertEquals(3,testDungeonLocal.getPlayerArrowsCollected());
    assertEquals(null, testDungeonLocal.getCurrentLocDemonDetails());
    assertEquals(false, testDungeonLocal.endGameCheck());
    testDungeonLocal.pickTreasure();
    testDungeonLocal.pickArrows();

    assertEquals(treasureMap, testDungeonLocal.getPlayerDetails());
    assertEquals(4,testDungeonLocal.getPlayerArrowsCollected());

    assertEquals(0, testDungeonLocal.getCurrentLocTreasureDetails().size());
    assertEquals(0, testDungeonLocal.getCurrentLocArrowDetails());

    assertEquals(DemonSmell.STRONG, testDungeonLocal.getDemonSmell());
    assertEquals(DemonHit.MISS, testDungeonLocal.shootArrow(Direction.WEST,4));
    assertEquals(3,testDungeonLocal.getPlayerArrowsCollected());
    assertEquals(DemonHit.HIT, testDungeonLocal.shootArrow(Direction.WEST,5));

  }

  @Test(expected = IllegalArgumentException.class)
  public void testNegativeDimensions() {
    new DungeonImpl(randomControlled,-6,6,2,false,
            20, 10,"player1");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNegativeInterConnectivity() {
    new DungeonImpl(randomControlled,6,6,-2,false,
            20, 10,"player1");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNegativeTreasurePercent() {
    new DungeonImpl(randomControlled,6,6,2,false,
            -20, 10,"player1");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidTreasurePercent() {
    new DungeonImpl(randomControlled,6,6,2,false,
            120,10,"player1");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullPlayerName() {
    new DungeonImpl(randomControlled,6,6,
            2,false,20,10,null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testEmptyPlayerName() {
    new DungeonImpl(randomControlled,6,6,
            2,false,20,10,"");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNegativePitCount() {
    new DungeonImpl(randomControlled,6,6,
            2,false,20,10,-1,1,"player1");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidPitCount() {
    new DungeonImpl(randomControlled,6,6,
            2,false,20,10,2,1,"player1");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNegativeThiefCount() {
    new DungeonImpl(randomControlled,6,6,
            2,false,20,10,1,-2,"player1");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidThiefCount() {
    new DungeonImpl(randomControlled,6,6,
            2,false,20,10,1,3,"player1");
  }


  @Test(expected = IllegalStateException.class)
  public void testShootArrowWithoutArrows() {
    testDungeon.shootArrow(Direction.WEST, 1);
    testDungeon.shootArrow(Direction.WEST, 1);
    testDungeon.shootArrow(Direction.WEST, 1);
    testDungeon.shootArrow(Direction.WEST, 1);

  }

  @Test(expected = IllegalArgumentException.class)
  public void testShootArrowHighDistance() {
    testDungeon.shootArrow(Direction.WEST, 100);

  }

  @Test(expected = IllegalArgumentException.class)
  public void testShootArrowInvalidDirection() {
    testDungeon.shootArrow(Direction.EAST, 1);

  }

  @Test(expected = IllegalArgumentException.class)
  public void testShootArrowInvalidDistance() {
    testDungeon.shootArrow(Direction.WEST, -1);

  }



}