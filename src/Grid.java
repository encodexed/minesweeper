import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;

public class Grid {

  // Metadata
  private int height;
  private final int minHeight = 10;
  private final int maxHeight = 26;

  private int width;
  private final int minWidth = 10;
  private final int maxWidth = 26;

  private int mines;
  private final int minMines = 10;
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
  }

  private Tile[][] generateGrid(int width, int height, int mines) {

    // Generate empty grid
    Tile[][] grid = new Tile[width][height];

    // Generate locations of mines
    int[][] mineLocations = generateMineLocations(width, height, mines);
    System.out.println("Mine locations: " + Arrays.deepToString(mineLocations));

    // Add mine tiles to the empty grid
    for (int i = 0; i < mineLocations.length; i++) {
      int xLocation = mineLocations[i][0];
      int yLocation = mineLocations[i][1];
      try {
        Coordinate mineCoordinates = new Coordinate(xLocation, yLocation, this.width, this.height);
        grid[xLocation][yLocation] = new Tile(mineCoordinates, TileType.MINE);
      } catch (Exception e) {
        System.out.println(e);
      }
    }

    // Add safe tiles to the rest of the grid
    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        if (grid[i][j] == null) {
          try {
            Coordinate safeCoordinates = new Coordinate(i, j, this.width, this.height);
            grid[i][j] = new Tile(safeCoordinates, TileType.SAFE);
          } catch (Exception e) {
            System.out.println(e);
          }
        }
      }
    }

    return grid;
  }

  private int[][] generateMineLocations(int width, int height, int mines) {
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
      int xLocation = rawLocationsArray[i] / height;
      int yLocation = rawLocationsArray[i] % height;
      mineLocations[i][0] = xLocation;
      mineLocations[i][1] = yLocation;
    }

    return mineLocations;
  }

  public void printGrid() {
    printXReference();

    for (int i = 0; i < this.height; i++) {
      System.out.print(String.format("[%d] ", i));
      for (int j = 0; j < this.width; j++) {
        System.out.print(this.tiles[j][i].displayTile() + " ");
      }
      System.out.print(String.format("[%d] \n", i));
    }

    printXReference();
  }

  public void printXReference() {
    System.out.print("[+] ");
    for (int i = 0; i < this.width; i++) {
      System.out.print(String.format("[%d] ", i));
    }
    System.out.print("[+] \n");
  }

  // Getters
  public int getHeight() {
    return this.height;
  }

  public int getWidth() {
    return this.height;
  }

  public int getMines() {
    return this.height;
  }

  public int getMaxMines() {
    return maxMines;
  }

  // Setters
  public void setHeight(int height) throws OutOfBoundsError {
    if (height > this.maxHeight) {
      throw new OutOfBoundsError("Given height was more than the maximum allowed height");
    }

    if (height < this.minHeight) {
      throw new OutOfBoundsError("Given height was less than the minimum allowed height");
    }

    this.height = height;
  }

  public void setWidth(int width) throws OutOfBoundsError {
    if (width > this.maxWidth) {
      throw new OutOfBoundsError("Given width was more than the maximum allowed width");
    }

    if (width < this.minWidth) {
      throw new OutOfBoundsError("Given width was less than the minimum allowed width");
    }

    this.width = width;
  }

  public void setMines(int mines) throws Exception {
    if (mines > this.maxMines) {
      throw new Exception("Given mines was more than the maximum allowed mines");
    }

    if (mines < this.minMines) {
      throw new Exception("Given mines was more than the maximum allowed mines");
    }

    this.mines = mines;
  }
}
