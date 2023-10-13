import java.util.Date;

public class Utils {

  public static boolean processCommand(String command, Grid grid) throws OutOfBoundsError, Exception {
    switch (command) {
      case "new":
        System.out.println("Starting a new game");
        grid.printGrid();
        return true;
      case "grid":
        grid.printGrid();
      case "help":
        printHelp();
        return true;
      case "time":
        printTimeTaken(grid);
        return true;
      case "quit":
        System.out.println("Quitting the game. Thanks for playing!");
        return false;
      default:
        if (command.charAt(0) == '!') {
          System.out.println(String.format("Flagging %s", command.substring(1)));
        } else {
          System.out.println(String.format("Revealing %s", command));
        }
        return true;
    }
  }

  public static void printHelp() {
    System.out.println("------------------------------------");
    System.out.println("Here are the accepted command patterns");
    System.out.println("'new' : Begins a new game.");
    System.out.println("'grid' : Displays the grid.");
    System.out.println("'a9' : Reveals A9");
    System.out.println("'!c4' : Flags C4");
    System.out.println("'help' : Displays this list of commands.");
    System.out.println("'time' : Displays the current time taken.");
    System.out.println("'quit' : Quits the game.");
    System.out.println("------------------------------------");
  }

  private static void printTimeTaken(Grid grid) {
    Date now = new Date();
    Date startedAt = grid.getTimeStarted();
    long millisecondsTaken = now.getTime() - startedAt.getTime();
    System.out.println(timeTakenFormatted(millisecondsTaken));
  }

  public static String timeTakenFormatted(long millisecondsTaken) {
    long seconds = millisecondsTaken / 1000;
    long minutes = (seconds % 3600) / 60;
    long secondsLeft = ((seconds % 3600) % 60) % 60;
    return String.format("Time taken: %02d:%02d", minutes, secondsLeft);
  }
}
