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

  public boolean setFlagged(boolean isFlagged, Grid grid, int[] coords) {
    // check if trying to add more flags than are remaining
    if (grid.getFlagsRemaining() == 0 && isFlagged) {
      System.out.println("No more flags remaining!");
      return false;
    }

    // update flags remaining value
    if (isFlagged) {
      grid.setFlagsRemaining(grid.getFlagsRemaining() - 1);
    } else {
      grid.setFlagsRemaining(grid.getFlagsRemaining() + 1);
    }

    this.isFlagged = isFlagged;
    adjustFlagLocationsArray(coords, grid);
    return true;
  }

  private void adjustFlagLocationsArray(int[] coords, Grid grid) {
    // add/remove flag from flag locations list
    Integer coordsInt0 = Integer.valueOf(coords[0]);
    Integer coordsInt1 = Integer.valueOf(coords[1]);
    Integer[] coordsIntArr = { coordsInt0, coordsInt1 };
    if (this.isFlagged()) {
      grid.addToFlagLocations(coordsIntArr);
    } else {
      grid.removeFromFlagLocations(coordsIntArr);
    }
  }

  public void setDisplayedValue(String displayedValue) {
    this.displayedValue = displayedValue;
  }
}
