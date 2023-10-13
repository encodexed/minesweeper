public class Tile {

  private Coordinate coordinates;
  private TileType tileType;
  private int nearbyMines;
  private boolean isRevealed;
  private boolean isFlagged;

  public Tile(Coordinate coordinates, TileType tileType) {
    this.coordinates = coordinates;
    this.tileType = tileType;
    this.nearbyMines = -1;
    this.isRevealed = false;
    this.isFlagged = false;
  }

  public String displayTile() {
    String value = "-";

    if (this.isRevealed) {
      if (this.tileType == TileType.SAFE) {
        value = " ";
      } else {
        value = "*";
      }
    }

    return String.format("[%s]", value);
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

  public void setRevealed(boolean isRevealed) {
    this.isRevealed = isRevealed;
  }

  public void setFlagged(boolean isFlagged) {
    this.isFlagged = isFlagged;
  }
}
