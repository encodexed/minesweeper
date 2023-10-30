import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandUtils {

  public static boolean processCommand(String command, Game game) throws OutOfBoundsError, Exception {
    switch (command) {
      case "new":
        game.reset(10, 10, 15);
        System.out.println("Starting a new game");
        return true;

      case "help":
        printHelp();
        return true;

      case "time":
        if (game.getTimeStarted() == null) {
          System.out.println("Timer not started yet.");
        } else {
          printTimeTaken(game);
        }
        return true;

      case "quit":
        System.out.println("Quitting the game. Thanks for playing!");
        return false;

      default:
        return handleCommand(command, game);
    }
  }

  private static boolean handleCommand(String command, Game game) {
    boolean validated = validateCoordinatesCommand(command);
    // if coordinates match regex
    if (validated) {
      // first turn should be safe
      if (game.isFirstTurn()) {
        boolean isFirstTurnUnsafe = true;
        // the board will regenerate if the first turn is unsafe, then retry turn
        while (isFirstTurnUnsafe) {
          // attempting the turn
          if (handleTurn(command, game)) {
            // unsafe turn, resetting the grid, looping around again
            try {
              game.reset(10, 10, 15);
            } catch (Exception e) {
              System.out.println(e.getMessage());
            }
          } else {
            // safe turn, starting timer, ending loop
            game.startTimer();
            game.setFirstTurn(false);
            isFirstTurnUnsafe = false;
          }
        }
        return true;
      } else {
        // every other turn besides the first, this could end the game
        boolean isGameOver = handleTurn(command, game);
        if (isGameOver) {
          endGame(false, game);
        }
        return true;
      }
    } else {
      // coords/command didn't match regex
      System.out.println("Invalid command");
      return true;
    }
  }

  public static void printHelp() {
    System.out.println("------------------------------------");
    System.out.println("Here are the accepted command patterns");
    System.out.println("'a9' : Reveals A9");
    System.out.println("'!c4' : Toggles flagging C4");
    System.out.println("'new' : Begins a new game.");
    System.out.println("'help' : Displays this list of commands.");
    System.out.println("'time' : Displays the current time taken.");
    System.out.println("'quit' : Quits the game.");
    System.out.print("------------------------------------");
  }

  public static void printTimeTaken(Game game) {
    if (game.getTimeStarted() == null) {
      return;
    }

    Date now = new Date();
    Date startedAt = game.getTimeStarted();
    long millisecondsTaken = now.getTime() - startedAt.getTime();
    System.out.println(timeTakenFormatted(millisecondsTaken));
  }

  private static String timeTakenFormatted(long millisecondsTaken) {
    long seconds = millisecondsTaken / 1000;
    long minutes = (seconds % 3600) / 60;
    long secondsLeft = ((seconds % 3600) % 60) % 60;
    return String.format("Time taken: %02d:%02d", minutes, secondsLeft);
  }

  private static void togglePlacedFlag(String coordinates, Grid grid) {
    int[] coords = convertCoordinatesToIntArray(coordinates);
    Tile targetTile = grid.getTileAt(coords[0], coords[1]);

    // make sure coordinates aren't out of bounds
    if (!grid.validateCoordinates(coords[0], coords[1])) {
      System.out.println("Provided coordinates are out of bounds.");
      return;
    }

    // if trying to flag a revealed tile
    if (targetTile.isRevealed() && !targetTile.isFlagged()) {
      System.out.println("You can't flag a revealed tile!");
      return;
    }

    // otherwise toggle flag on/off and revealed on/off
    boolean allowedToFlag = targetTile.setFlagged(!targetTile.isFlagged(), grid, coords);
    if (allowedToFlag) {
      boolean isRevealed = targetTile.isRevealed();
      targetTile.setRevealed(!isRevealed, true);
    }
  }

  private static int[] convertCoordinatesToIntArray(String coordinates) {
    String lowerCaseX = (coordinates.charAt(0) + "").toLowerCase();
    int x = lowerCaseX.charAt(0) - 97;
    int y = Integer.parseInt(coordinates.substring(1));
    int[] coords = { x, y };

    return coords;
  }

  private static boolean validateCoordinatesCommand(String command) {
    Pattern pattern = Pattern.compile("\\!?[A-Za-z]\\d\\d?", Pattern.CASE_INSENSITIVE);
    Matcher matcher = pattern.matcher(command);
    return matcher.matches();
  }

  // Returning true here triggers an end game condition
  private static boolean revealTile(String coordinates, Grid grid, Game game) {
    int[] coords = convertCoordinatesToIntArray(coordinates);

    // make sure coordinates aren't out of bounds
    if (!grid.validateCoordinates(coords[0], coords[1])) {
      System.out.println("Provided coordinates are out of bounds.");
      return false;
    }

    Tile targetTile = grid.getTileAt(coords[0], coords[1]);

    if (targetTile.isRevealed()) {
      System.out.println("That tile is already revealed or flagged!");
    } else {
      targetTile.setRevealed(true, game.isRunning());
      // report unsafe turn if nearby mines and first turn
      if (targetTile.getNearbyMines() > 0 && game.isFirstTurn()) {
        return true;
      }

      // report unsafe turn if first turn, or end game otherwise
      if (targetTile.getTileType() == TileType.MINE) {
        return true;
      }

      // open lots of tiles if completely safe
      if (targetTile.getDisplayedValue().equals("[~]")) {
        grid.cascadeSafeReveals(coords[0], coords[1], game);
      }

    }

    return false;
  }

  // Returning true here ends the game
  private static boolean handleTurn(String command, Game game) {
    Grid grid = game.getGrid();
    if (command.charAt(0) == '!') {
      togglePlacedFlag(command.substring(1), grid);
      // Check if game is won
      if (grid.getFlagsRemaining() == 0) {
        game.checkWinCondition();
      }
      return false;
    } else {
      // Has the potential to end the game
      boolean isGameOver = revealTile(command, grid, game);
      // Check if game is won
      if (grid.getFlagsRemaining() == 0) {
        game.checkWinCondition();
      }
      return isGameOver;
    }
  }

  public static void endGame(boolean isOutcomeGood, Game game) {
    Grid grid = game.getGrid();
    int revealedTiles = grid.getRevealedTilesCount();
    game.setRunning(false);

    if (isOutcomeGood) {
      System.out.println("\n################### YOU WON ###################");
      grid.printGrid();
      System.out.println("###############################################\n");
      System.out.println("Congratulations! You beat the game and flagged all of the mines safely.");
    } else {
      System.out.println("\n#################### OH NO ####################");
      grid.printGrid();
      System.out.println("###############################################\n");
      System.out.println("Oh no! You stepped on a mine... Better luck next time.");
    }

    printTimeTaken(game);
    System.out.println("Mines successfully flagged: " + grid.minesCorrectlyFlagged());
    System.out.println(String.format("Tiles revealed: %d/%d", revealedTiles, grid.getTotalTiles()));
  }
}
