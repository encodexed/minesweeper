import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Grid {

  // Metadata
  private int flagsRemaining;
  private ArrayList<Integer[]> flagLocations;
  private int[][] mineLocations;

  private int height;
  private final int MIN_HEIGHT = 10;
  private final int MAX_HEIGHT = 26;

  private int width;
  private final int MIN_WIDTH = 10;
  private final int MAX_WIDTH = 26;

  private int mines;
  private final int MIN_MINES = 10;
  private int maxMines;

  // Content
  private Tile[][] tiles;

  // Constructor
  public Grid(int height, int width, int mines) throws OutOfBoundsError, Exception {
    setHeight(height);
    setWidth(width);
    this.maxMines = (width - 1) * (height - 1);
    setMines(mines);
    this.tiles = generateGrid(width, height, mines);
    calculateAllNearbyMines();
    this.flagsRemaining = mines;
    this.flagLocations = new ArrayList<Integer[]>();
  }

  private Tile[][] generateGrid(int width, int height, int mines) {

    // Generate empty grid
    Tile[][] grid = new Tile[width][height];

    // Generate locations of mines
    generateMineLocations(width, height, mines);
    // System.out.println("Mine locations: " +
    // Arrays.deepToString(this.mineLocations));

    // Add mine tiles to the empty grid
    for (int i = 0; i < mineLocations.length; i++) {
      int xLocation = mineLocations[i][0];
      int yLocation = mineLocations[i][1];
      grid[xLocation][yLocation] = new Tile(xLocation, yLocation, TileType.MINE);
    }

    // Add safe tiles to the rest of the grid
    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        if (grid[i][j] == null) {
          grid[i][j] = new Tile(i, j, TileType.SAFE);
        }
      }
    }

    return grid;
  }

  private void generateMineLocations(int width, int height, int mines) {
    // Create unique mine locations
    int totalTiles = width * height;
    HashSet<Integer> rawLocations = new HashSet<>(mines);

    for (int i = 0; i < mines; i++) {
      boolean isAdded = false;
      while (!isAdded) {
        int newLocation = (int) (Math.floor(Math.random() * totalTiles));
        isAdded = rawLocations.add(newLocation);
      }
    }

    // Extract int values from hashset into an array
    int[] rawLocationsArray = new int[mines];
    Iterator<Integer> iterator = rawLocations.iterator();
    int a = 0;
    while (iterator.hasNext()) {
      rawLocationsArray[a] = iterator.next();
      a++;
    }

    // Convert int[] into int[][] of valid coordinates
    int[][] mineLocations = new int[mines][2];
    for (int i = 0; i < mines; i++) {
      mineLocations[i][0] = rawLocationsArray[i] / height;
      mineLocations[i][1] = rawLocationsArray[i] % height;
    }

    this.mineLocations = mineLocations;
  }

  private void calculateAllNearbyMines() {
    for (int i = 0; i < this.width; i++) {
      for (int j = 0; j < this.height; j++) {
        this.tiles[i][j].setNearbyMines(calculateNearbyMines(i, j));
      }
    }
  }

  private int calculateNearbyMines(int xLoc, int yLoc) {
    int mineCounter = 0;
    // top left
    mineCounter += this.checkForMine(xLoc - 1, yLoc - 1);
    // top
    mineCounter += this.checkForMine(xLoc, yLoc - 1);
    // top right
    mineCounter += this.checkForMine(xLoc + 1, yLoc - 1);
    // right
    mineCounter += this.checkForMine(xLoc + 1, yLoc);
    // bottom right
    mineCounter += this.checkForMine(xLoc + 1, yLoc + 1);
    // bottom
    mineCounter += this.checkForMine(xLoc, yLoc + 1);
    // bottom left
    mineCounter += this.checkForMine(xLoc - 1, yLoc + 1);
    // left
    mineCounter += this.checkForMine(xLoc - 1, yLoc);

    return mineCounter;
  }

  private int checkForMine(int xLoc, int yLoc) {
    if (validateCoordinates(xLoc, yLoc)) {
      if (this.getTileAt(xLoc, yLoc).getTileType() == TileType.MINE) {
        return 1;
      }
    }

    return 0;
  }

  public boolean validateCoordinates(int x, int y) {
    if (x < 0 || x > this.width - 1) {
      return false;
    }

    if (y < 0 || y > this.height - 1) {
      return false;
    }

    return true;
  }

  public void printGrid() {

    printXReference();

    for (int i = 0; i < this.height; i++) {
      System.out.print(String.format("[%d] ", i));
      for (int j = 0; j < this.width; j++) {
        System.out.print(this.tiles[j][i].getDisplayedValue() + " ");
      }
      System.out.print(String.format("[%d] \n", i));
    }

    printXReference();
  }

  public void printXReference() {
    System.out.print("[+] ");
    for (int i = 0; i < this.width; i++) {
      System.out.print(String.format("[%c] ", i + 65));
    }
    System.out.print("[+] \n");
  }

  // Getters
  public int getTotalTiles() {
    return this.width * this.height;
  }

  public int getFlagsRemaining() {
    return flagsRemaining;
  }

  public ArrayList<Integer[]> getFlagLocations() {
    return flagLocations;
  }

  public int getHeight() {
    return this.height;
  }

  public int getWidth() {
    return this.width;
  }

  public int getMines() {
    return this.mines;
  }

  public int getMaxMines() {
    return this.maxMines;
  }

  // OutOfBoundsError cannot be thrown logically here
  public Tile getTileAt(int x, int y) {
    return this.tiles[x][y];
  }

  public int getFlagLocationsSize() {
    return this.flagLocations.size();
  }

  // Setters

  public void addToFlagLocations(Integer[] coords) {
    this.flagLocations.add(coords);
    System.out.println("Flag location added.");
  }

  public void removeFromFlagLocations(Integer[] coords) {
    Stream<Integer[]> filteredLocations = this.flagLocations.stream().filter((flagLocs) -> {
      return (flagLocs[0] != coords[0] || flagLocs[1] != coords[1]);
    });

    List<Integer[]> locationsList = filteredLocations.collect(Collectors.toList());
    this.flagLocations = new ArrayList<Integer[]>(locationsList);
    System.out.println("Flag location removed.");
  }

  public void setHeight(int height) throws OutOfBoundsError {
    if (height > this.MAX_HEIGHT) {
      throw new OutOfBoundsError("Given height was more than the maximum allowed height");
    }

    if (height < this.MIN_HEIGHT) {
      throw new OutOfBoundsError("Given height was less than the minimum allowed height");
    }

    this.height = height;
  }

  public void setWidth(int width) throws OutOfBoundsError {
    if (width > this.MAX_WIDTH) {
      throw new OutOfBoundsError("Given width was more than the maximum allowed width");
    }

    if (width < this.MIN_WIDTH) {
      throw new OutOfBoundsError("Given width was less than the minimum allowed width");
    }

    this.width = width;
  }

  public void setMines(int mines) throws Exception {
    if (mines > this.maxMines) {
      throw new Exception("Given mines was more than the maximum allowed mines");
    }

    if (mines < this.MIN_MINES) {
      throw new Exception("Given mines was more than the maximum allowed mines");
    }

    this.mines = mines;
  }

  public void setFlagsRemaining(int flagsRemaining) {
    this.flagsRemaining = flagsRemaining;
  }

  public void revealAllTiles() {
    for (int i = 0; i < this.width; i++) {
      for (int j = 0; j < this.height; j++) {
        this.tiles[i][j].setRevealed(true, false);
      }
    }
  }

  public void cascadeSafeReveals(int xLoc, int yLoc, Game game) {
    // top left
    handleCascade(xLoc - 1, yLoc - 1, game);
    // top
    handleCascade(xLoc, yLoc - 1, game);
    // // top right
    handleCascade(xLoc + 1, yLoc - 1, game);
    // // right
    handleCascade(xLoc + 1, yLoc, game);
    // // bottom right
    handleCascade(xLoc + 1, yLoc + 1, game);
    // // bottom
    handleCascade(xLoc, yLoc + 1, game);
    // // bottom left
    handleCascade(xLoc - 1, yLoc + 1, game);
    // // left
    handleCascade(xLoc - 1, yLoc, game);
  }

  private void handleCascade(int xLoc, int yLoc, Game game) {
    if (validateCoordinates(xLoc, yLoc)) {
      Tile tile = getTileAt(xLoc, yLoc);
      if (!tile.isRevealed()) {
        tile.setRevealed(true, game.isRunning());
        if (tile.getNearbyMines() == 0) {
          this.cascadeSafeReveals(xLoc, yLoc, game);
        }
      }
    }
  }

  public int minesCorrectlyFlagged() {
    int minesCorrectlyFlagged = 0;

    for (Integer[] flagLoc : this.flagLocations) {
      int fX = flagLoc[0];
      int fY = flagLoc[1];

      for (int[] mineLoc : this.mineLocations) {
        int mX = mineLoc[0];
        int mY = mineLoc[1];
        if (fX == mX && fY == mY) {
          minesCorrectlyFlagged++;
        }
      }
    }

    return minesCorrectlyFlagged;
  }

  public int getRevealedTilesCount() {
    int revealedTiles = 0;

    for (int i = 0; i < this.width; i++) {
      for (int j = 0; j < this.height; j++) {
        if (this.tiles[i][j].isRevealed()) {
          revealedTiles++;
        }
      }
    }

    return revealedTiles;
  }

}
