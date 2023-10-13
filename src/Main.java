import java.util.Scanner;

public class Main {
  public static void main(String[] args) throws Exception {
    System.out.println("Welcome to Java Minesweeper!");
    Grid grid = new Grid(10, 10, 10);
    Utils.printHelp();
    Scanner keyboard = new Scanner(System.in);
    boolean isGameRunning = true;
    while (isGameRunning) {
      System.out.print("Enter a command: ");
      String command = keyboard.next();
      isGameRunning = Utils.processCommand(command, grid);
    }

    keyboard.close();
  }
}