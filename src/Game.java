import java.util.Date;

public class Game {

  private Date timeStarted;
  private boolean isFirstTurn;
  private boolean isRunning;
  private Grid grid;

  public Game(int height, int width, int mines) throws OutOfBoundsError, Exception {
    this.timeStarted = null;
    this.isFirstTurn = true;
    this.isRunning = true;
    this.grid = new Grid(height, width, mines);
  }

  public void reset(int height, int width, int mines) throws OutOfBoundsError, Exception {
    this.timeStarted = null;
    this.isFirstTurn = true;
    this.isRunning = true;
    this.grid = new Grid(height, width, mines);
  }

  public Date getTimeStarted() {
    return timeStarted;
  }

  public boolean isFirstTurn() {
    return isFirstTurn;
  }

  public boolean isRunning() {
    return isRunning;
  }

  public Grid getGrid() {
    return grid;
  }

  public void setTimeStarted(Date timeStarted) {
    this.timeStarted = timeStarted;
  }

  public void setFirstTurn(boolean isFirstTurn) {
    this.isFirstTurn = isFirstTurn;
  }

  public void setRunning(boolean isRunning) {
    this.isRunning = isRunning;
    if (!isRunning) {
      this.grid.revealAllTiles();
    }
  }

  public void setGrid(Grid grid) {
    this.grid = grid;
  }

  public void startTimer() {
    this.timeStarted = new Date();
  }

  public void checkWinCondition() {
    if (this.grid.getRevealedTilesCount() == this.grid.getTotalTiles() && isRunning) {
      CommandUtils.endGame(true, this);
    }
  }
}
