import java.util.Scanner;

public class Main {
  public static void main(String[] args) throws OutOfBoundsError, Exception {
    System.out.println("\nWelcome to Java Minesweeper!");
    Grid grid = new Grid(10, 10, 15);
    CommandUtils.printHelp();
    Scanner keyboard = new Scanner(System.in);

    boolean isGameRunning = true;
    while (isGameRunning) {
      if (grid.isRunning()) {
        displayInfoEachTurn(grid);
      }
      System.out.print("\nEnter a command: ");
      String command = keyboard.next();
      isGameRunning = CommandUtils.processCommand(command, grid);
    }

    keyboard.close();
  }

  public static void displayInfoEachTurn(Grid grid) {
    System.out.println("\n");
    grid.printGrid();
    int flagsUsed = grid.getMines() - grid.getFlagsRemaining();
    System.out.print("Flags: " + flagsUsed + "/" + grid.getMines());

    if (grid.getTimeStarted() != null) {
      System.out.print(" --- ");
      CommandUtils.printTimeTaken(grid);
    } else {
      System.out.print("\n");
    }
  }
}