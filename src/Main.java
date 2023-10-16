import java.util.Scanner;

public class Main {
  public static void main(String[] args) throws Exception {
    System.out.println("Welcome to Java Minesweeper!");
    Grid grid = new Grid(10, 10, 15);
    CommandUtils.printHelp();
    grid.printGrid();
    Scanner keyboard = new Scanner(System.in);
    boolean isGameRunning = true;
    while (isGameRunning) {
      System.out.print("Enter a command: ");
      String command = keyboard.next();
      isGameRunning = CommandUtils.processCommand(command, grid);
    }

    keyboard.close();
  }
}