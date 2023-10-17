public class Tile {

  private int coordinateX;
  private int coordinateY;
  private TileType tileType;
  private int nearbyMines;
  private boolean isRevealed;
  private boolean isFlagged;
  private String displayedValue;

  public Tile(int coordinateX, int coordinateY, TileType tileType) {
    this.coordinateX = coordinateX;
    this.coordinateY = coordinateY;
    this.tileType = tileType;
    this.nearbyMines = -1;
    this.isRevealed = false;
    this.isFlagged = false;
    this.displayedValue = "_";
  }

  // Getters

  public int getCoordinateX() {
    return coordinateX;
  }

  public int getCoordinateY() {
    return coordinateY;
  }

  public int getNearbyMines() {
    return nearbyMines;
  }

  public TileType getTileType() {
    return tileType;
  }

  public boolean isFlagged() {
    return isFlagged;
  }

  public boolean isRevealed() {
    return isRevealed;
  }

  public String getDisplayedValue() {
    return "[" + displayedValue + "]";
  }

  // Setters

  public void setCoordinateX(int coordinateX) {
    this.coordinateX = coordinateX;
  }

  public void setCoordinateY(int coordinateY) {
    this.coordinateY = coordinateY;
  }

  public void setTileType(TileType tileType) {
    this.tileType = tileType;
  }

  public void setNearbyMines(int nearbyMines) {
    this.nearbyMines = nearbyMines;
  }

  public void setRevealed(boolean isRevealed, boolean isRunning) {
    this.isRevealed = isRevealed;
    determineDisplayedValue(isRunning);
  }

  public void determineDisplayedValue(boolean isRunning) {
    // if game is running
    if (this.isRevealed && isRunning) {
      // if tile is flagged
      if (this.isFlagged) {
        setDisplayedValue("!");
        // if tile has no nearby mines
      } else if (this.nearbyMines == 0) {
        setDisplayedValue("~");
        // if tile has nearby mines
      } else {
        setDisplayedValue(this.nearbyMines + "");
      }
      // if game is over
    } else if (this.isRevealed && !isRunning) {
      // if tile is a mine
      if (this.tileType == TileType.MINE) {
        setDisplayedValue("*");
      } else {
        if (this.nearbyMines != 0) {
          setDisplayedValue(this.nearbyMines + "");
        } else {
          setDisplayedValue("~");
        }
      }
    } else if (!this.isRevealed && isRunning) {
      setDisplayedValue("_");
    }
  }

  public void setFlagged(boolean isFlagged, Grid grid) {
    // check if trying to add more flags than are remaining
    if (grid.getFlagsRemaining() == 0 && isFlagged) {
      System.out.println("No more flags remaining!");
      return;
    }

    // update flags remaining value
    if (isFlagged) {
      grid.setFlagsRemaining(grid.getFlagsRemaining() - 1);
    } else {
      grid.setFlagsRemaining(grid.getFlagsRemaining() + 1);
    }

    this.isFlagged = isFlagged;
  }

  public void setDisplayedValue(String displayedValue) {
    this.displayedValue = displayedValue;
  }
}
