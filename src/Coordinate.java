public class Coordinate {

  private int x;
  private int y;

  public Coordinate(int x, int y, int gridWidth, int gridHeight) throws OutOfBoundsError {
    setX(x, gridWidth);
    setY(y, gridHeight);
  }

  // Getters

  public int getX() {
    return this.x;
  }

  public int getY() {
    return this.y;
  }

  // Setters

  public void setX(int x, int gridWidth) throws OutOfBoundsError {
    if (x < 0 || x > gridWidth) {
      throw new OutOfBoundsError("Coordinate X is out of bounds");
    }

    this.x = x;
  }

  public void setY(int y, int gridHeight) throws OutOfBoundsError {
    if (y < 0 || y > gridHeight) {
      throw new OutOfBoundsError("Coordinate Y is out of bounds");
    }

    this.y = y;
  }
}
