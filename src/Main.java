public class Main {
  public static void main(String[] args) throws Exception {
    System.out.println("Welcome to Minesweeper!");
    Grid newGrid = new Grid(10, 10, 10);
    newGrid.printGrid();
  }
}