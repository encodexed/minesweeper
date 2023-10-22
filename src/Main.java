import java.util.Scanner;

public class Main {
  public static void main(String[] args) throws OutOfBoundsError, Exception {
    System.out.println("\nWelcome to Java Minesweeper!");
    Game game = new Game(10, 10, 15);
    CommandUtils.printHelp();
    Scanner keyboard = new Scanner(System.in);

    boolean isGameRunning = true;
    while (isGameRunning) {
      if (game.isRunning()) {
        displayInfoEachTurn(game);
      }
      System.out.print("\nEnter a command: ");
      String command = keyboard.next();
      isGameRunning = CommandUtils.processCommand(command, game);
    }

    keyboard.close();
  }

  public static void displayInfoEachTurn(Game game) {
    System.out.println("\n");
    Grid grid = game.getGrid();
    grid.printGrid();
    int flagsUsed = grid.getMines() - grid.getFlagsRemaining();
    System.out.print("Flags: " + flagsUsed + "/" + grid.getMines());

    if (game.getTimeStarted() != null) {
      System.out.print(" --- ");
      CommandUtils.printTimeTaken(game);
    } else {
      System.out.print("\n");
    }
  }
}