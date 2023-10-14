public class Tile {

  private Coordinate coordinates;
  private TileType tileType;
  private int nearbyMines;
  private boolean isRevealed;
  private boolean isFlagged;
  private String displayedValue;

  public Tile(Coordinate coordinates, TileType tileType) {
    this.coordinates = coordinates;
    this.tileType = tileType;
    this.nearbyMines = -1;
    this.isRevealed = false;
    this.isFlagged = false;
    this.displayedValue = "_";
  }

  // Getters

  public Coordinate getCoordinates() {
    return coordinates;
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

  public void setCoordinates(Coordinate coordinates) {
    this.coordinates = coordinates;
  }

  public void setTileType(TileType tileType) {
    this.tileType = tileType;
  }

  public void setNearbyMines(int nearbyMines) {
    this.nearbyMines = nearbyMines;
  }

  public void setRevealed(boolean isRevealed, boolean isRunning) {
    this.isRevealed = isRevealed;
    if (isRevealed && isRunning) {
      setDisplayedValue(this.nearbyMines + "");
    } else if (isRevealed && !isRunning) {
      if (this.tileType == TileType.MINE) {
        setDisplayedValue("*");
      } else {
        // TODO: Display nearby mine count
        setDisplayedValue("-");
      }
    }
  }

  public void setFlagged(boolean isFlagged) {
    this.isFlagged = isFlagged;
  }

  public void setDisplayedValue(String displayedValue) {
    this.displayedValue = displayedValue;
  }
}
