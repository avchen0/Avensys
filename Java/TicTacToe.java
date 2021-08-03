import java.util.Objects;
import java.util.Scanner;

public class TicTacToe {

    private int size = 3;
    private String[][] grid;
    private String gameStatus;

    public TicTacToe(int size) {
        this.size = size;
        grid = new String[size][size];
        gameStatus = "inProgress";
        createGrid(size);
        printGrid(size);
    }

    public void runGame() {
        do {
            playRound();
        } while(Objects.equals(gameStatus, "inProgress"));
        if (Objects.equals(gameStatus, "tie")) {
            System.out.println("The game has ended in a tie.");
        }
        else {
            System.out.println(gameStatus.charAt(0) + " has won.");
        }
    }

    private void add(String player) {
        System.out.println("Select row to place " + player);
        for (int i = 1; i <= size; i++) {
            System.out.println("Press " + i + " to select row " + i);
        }
        Scanner sc = new Scanner(System.in);
        int chosenRow = sc.nextInt();
        while (!validRow(chosenRow - 1)) {
            System.out.println("Please select a valid choice");
            chosenRow = sc.nextInt();
        }
        System.out.println("Select col to place " + player);
        for (int i = 1; i <= size; i++) {
            System.out.println("Press " + i + " to select col " + i);
        }
        int chosenCol = sc.nextInt();
        while (!validCol(chosenRow - 1,chosenCol - 1)) {
            System.out.println("Please select a valid choice");
            chosenCol = sc.nextInt();
        }
        updateGrid(chosenRow - 1, chosenCol - 1, player);
    }

    private void createGrid(int size) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                this.grid[i][j] = "|_";
            }
        }
    }

    private void playRound() {
        add("X");
        printGrid(size);
        if (!Objects.equals(gameStatus, "inProgress")) {
            return;
        }
        add("O");
        printGrid(size);
    }

    private void printGrid(int size) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(grid[i][j]);
            }
            System.out.print("|");
            System.out.println();
        }
    }

    private void updateGameStatus() {
        boolean isGridFull = true;
        int numMatches = 0, x = size - 1;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < x; j++) {
                if (!Objects.equals(grid[i][j], "|_") && !Objects.equals(grid[i][j + 1], "|_")) {
                    if (Objects.equals(grid[i][j], grid[i][j + 1])) {
                        numMatches++;
                    }
                }
                else {
                    isGridFull = false;
                }

            }
            if (numMatches == size - 1) {
                gameStatus = grid[i][0].charAt(1) + "win";
                return;
            }
            else {
                numMatches = 0;
            }
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < x; j++) {
                if (!Objects.equals(grid[j][i], "|_") && !Objects.equals(grid[j + 1][i], "|_")) {
                    if (Objects.equals(grid[j][i], grid[j + 1][i])) {
                        numMatches++;
                    }
                }
                else {
                    isGridFull = false;
                }
            }
            if (numMatches == size - 1) {
                gameStatus = grid[0][i].charAt(1) + "win";
                return;
            }
            else {
                numMatches = 0;
            }
        }
        if (!Objects.equals(grid[1][1], "|_")) {
            if (Objects.equals(grid[1][1], grid[2][0]) && Objects.equals(grid[1][1], grid[0][2])) {
                gameStatus = grid[1][1].charAt(1) + "win";
                return;
            }
            else if (Objects.equals(grid[1][1], grid[0][0]) && Objects.equals(grid[1][1], grid[2][2])) {
                gameStatus = grid[1][1].charAt(1) + "win";
                return;
            }
        }
        if (isGridFull) {
            gameStatus = "tie";
        }
    }

    private void updateGrid(int chosenRow, int chosenCol, String player) {
        if (Objects.equals(player, "X")) {
            grid[chosenRow][chosenCol] = "|X";
        }
        else {
            grid[chosenRow][chosenCol] = "|O";
        }
        updateGameStatus();
    }

    private boolean validRow(int chosen) {
        if (chosen < 0 || chosen > 2) {
            return false;
        }
        int empty = 0;
        for (int i = 0; i < size; i++) {
            if (Objects.equals(grid[chosen][i], "|_")) {
                empty++;
            }
        }
        return empty != 0;
    }

    private boolean validCol(int chosenRow, int chosenCol) {
        if (chosenCol < 0 || chosenCol > 2) {
            return false;
        }
        return Objects.equals(grid[chosenRow][chosenCol], "|_");
    }

    public static void main(String[] args) {
        TicTacToe ticTacToe = new TicTacToe(3);
        ticTacToe.runGame();
    }
}
