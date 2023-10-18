import java.util.Scanner;

public class Main {
  public static void main(String[] args) throws OutOfBoundsError, Exception {
    System.out.println("Welcome to Java Minesweeper!");
    Grid grid = new Grid(10, 10, 15);
    CommandUtils.printHelp();
    Scanner keyboard = new Scanner(System.in);
    boolean isGameRunning = true;
    while (isGameRunning) {
      displayInfoEachTurn(grid);
      String command = keyboard.next();
      isGameRunning = CommandUtils.processCommand(command, grid);
    }

    keyboard.close();
  }

  public static void displayInfoEachTurn(Grid grid) {
    System.out.println("------------------------------------");
    grid.printGrid();
    int flagsUsed = grid.getMines() - grid.getFlagsRemaining();
    System.out.println("Flags: " + flagsUsed + "/" + grid.getMines());
    CommandUtils.printTimeTaken(grid);
    System.out.print("Enter a command: ");
  }
}